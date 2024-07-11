package single_cycle
import chisel3._
import chisel3.util._

class Control extends Module {
  val io = IO(new Bundle {
    val instruction = Input(UInt(32.W))

    val regWrEn = Output(Bool())  // Enables writing to regfile
    val branch = Output(Bool())   // current instruction is a branch
    val memRead = Output(Bool())  // Enables reading data from memory
    val memWrite = Output(Bool()) // Enables writing data to memory
    val memToReg = Output(Bool()) // write data from memory or ALU to the register file
    val aluImm = Output(Bool())   // rs2 or immediate for alu
    val branchfun3 = Output(UInt(3.W)) // branch fun 3 for branch alu
  })

  io.regWrEn := 0.B
  io.branch := 0.B
  io.memRead := 0.B
  io.memWrite := 0.B
  io.memToReg := 0.B
  io.aluImm := 0.B
  io.branchfun3 := 0.U

  val opcode = io.instruction(6, 0)

  switch(opcode) {
    is("b0110011".U) { // R-type instructions
      io.regWrEn := 1.B    // write ALU result to regfile
      io.aluImm := 0.B 
      io.branch := 0.B
    }
    is("b0010011".U) { // I-type instructions
      io.regWrEn := 1.B    // write ALU result to regfile
      io.aluImm := 1.B     // ALU second operand is an immediate value
      io.branch := 0.B
    }
    is("b0000011".U) { // Load instructions
      io.regWrEn := 1.B    // write ALU result to regfile
      io.memRead := 1.B    // Enable memory read
      io.memToReg := 1.B   // Select memory data as the source for register write
      io.aluImm := 1.B     // immediate value (address offset)
      io.branch := 0.B
    }
    is("b0100011".U) { // Store instructions
      io.memWrite := 1.B   // Enable memory write
      io.aluImm := 1.B     // immediate value (address offset)
      io.branch := 0.B
    }
    is("b1100011".U) { // Branch instructions
      io.branchfun3 := io.instruction(14, 12)
      io.branch := 1.B     // Indicate that this is a branch instruction
    }
    is("b0110111".U) { // LUI (Load Upper Immediate) instructions
      io.regWrEn := 1.B    // write ALU result to regfile
      io.aluImm := 1.B     // immediate value
      io.branch := 0.B
    }
    is("b0010111".U) { // AUIPC (Add Upper Immediate to PC) instructions
      io.regWrEn := 1.B    // write ALU result to regfile
      io.aluImm := 1.B     // immediate value
      io.branch := 0.B
    }
    is("b1101111".U) { // JAL (Jump and Link) instructions
      io.regWrEn := 1.B    /// write ALU result to regfile
      io.branch := 1.B     // branch (jump) instruction
      io.aluImm := 1.B     // immediate value
    }
    is("b1100111".U) { // JALR (Jump and Link Register) instructions
      io.regWrEn := 1.B    // write ALU result to regfile
      io.branch := 1.B     // Indicate that this is a branch (jump) instruction
      io.aluImm := 1.B     // immediate value
    }
  }
}
