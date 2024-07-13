package single_cycle

import chisel3._
import chisel3.util._

class Control extends Module {
  val io = IO(new Bundle {
    val instruction = Input(UInt(32.W))
    val btaken      = Input(Bool())

    val aluOP      = Output(UInt(4.W)) // ALU operation fun3+fun7(5)
    val rs1        = Output(UInt(5.W)) // rs1 from instruction
    val rs2        = Output(UInt(5.W)) // rs2 from instruction
    val rd         = Output(UInt(5.W))  // rd address from instruction
    val imm        = Output(SInt(32.W)) // Immediate value from ImmGen
    val regWrEn    = Output(Bool())  // Enables writing to regfile
    val branch     = Output(Bool())   // Indicates branch instruction
    val memRead    = Output(Bool())  // Enables reading data from memory
    val memWrite   = Output(Bool()) // Enables writing data to memory
    val memToReg   = Output(Bool()) // Write data from memory or ALU to regfile
    val aluImm     = Output(Bool())   // Selects rs2 or immediate for ALU
    val branchfun3 = Output(UInt(3.W)) // Branch fun3 for branch ALU
    val pcsel      = Output(Bool()) 
  
  })

  // Default values
  io.aluOP := 0.U
  io.rs1 := 0.U
  io.rs2 := 0.U
  io.rd := 0.U
  io.imm := 0.S
  io.regWrEn := 0.B
  io.branch := 0.B
  io.memRead := 0.B
  io.memWrite := 0.B
  io.memToReg := 0.B
  io.aluImm := 0.B
  io.branchfun3 := 0.U
  io.pcsel := 0.B

  val opcode = io.instruction(6, 0) // opcode for instructions
  val func3 = io.instruction(14, 12)
  val func7 = io.instruction(31, 25)

//   // Instantiate ImmGen module
//   val immGen = Module(new ImmGen)
//   immGen.instr := io.instruction // Connect instruction input to ImmGen
  
//   // Connect outputs based on opcode
//   io.imm := immGen.imm_val // Connect imm output from ImmGen

  switch(opcode) {
    is("b0110011".U) { // R-type instructions
      io.aluOP := Cat(func3, func7(5))
      io.rs1 := io.instruction(19, 15)
      io.rs2 := io.instruction(24, 20)
      io.rd := io.instruction(11, 7)
      io.regWrEn := 1.B
      io.aluImm := 0.B
      io.pcsel := 0.B
    }
    is("b0010011".U) { // I-type instructions
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rd := io.instruction(11, 7)
      io.rs2 := 0.U
      io.regWrEn := 1.B
      io.aluImm := 1.B
      io.pcsel := 0.B
    }
    is("b0000011".U) { // Load instructions
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rd := io.instruction(11, 7)
      io.rs2 := 0.U
      io.regWrEn := 1.B
      io.memRead := 1.B
      io.memToReg := 1.B
      io.aluImm := 1.B
      io.pcsel := 0.B
    }
    is("b0100011".U) { // Store instructions
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rs2 := io.instruction(24, 20)
      io.rd := 0.U
      io.memWrite := 1.B
      io.aluImm := 1.B
      io.pcsel := 0.B
    }
    is("b1100011".U) { // Branch instructions
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rs2 := io.instruction(24, 20)
      io.rd := 0.U
      io.branchfun3 := func3
      io.branch := 1.B
      io.pcsel := Mux(io.branch && io.btaken,1.B,0.B)
    }
    is("b0110111".U) { // LUI (Load Upper Immediate)
      io.aluOP := 0.U
      io.rs1 := 0.U
      io.rs2 := 0.U
      io.rd := io.instruction(11, 7)
      io.regWrEn := 1.B
      io.aluImm := 1.B
      io.pcsel := 0.B
    }
    is("b0010111".U) { // AUIPC (Add Upper Immediate to PC)
      io.aluOP := 0.U
      io.rs1 := 0.U
      io.rs2 := 0.U
      io.rd := io.instruction(11, 7)
      io.regWrEn := 1.B
      io.aluImm := 1.B
      io.pcsel := 1.B
    }
    is("b1101111".U) { // JAL (Jump and Link)
      io.aluOP := 0.U
      io.rs1 := 0.U
      io.rs2 := 0.U
      io.rd := io.instruction(11, 7)
      io.regWrEn := 1.B
      io.branch := 1.B
      io.aluImm := 1.B
      io.pcsel := 1.B
    }
    is("b1100111".U) { // JALR (Jump and Link Register)
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rs2 := 0.U
      io.rd := io.instruction(11, 7)
      io.regWrEn := 1.B
      io.branch := 1.B
      io.aluImm := 1.B
      io.pcsel := 1.B
    }
  }
}
