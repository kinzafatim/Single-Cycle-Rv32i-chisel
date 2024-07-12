package single_cycle
import chisel3._
import chisel3.util._

class Decoder extends Module {
  val io = IO(new Bundle {
    val instruction = Input(UInt(32.W))
    val aluOP = Output(UInt(4.W))
    val rs1 = Output(UInt(5.W))
    val rs2 = Output(UInt(5.W))
    val rd = Output(UInt(5.W))
    val imm = Output(SInt(32.W))
  })

  // Default values
  io.aluOP := 0.U
  io.rs1 := 0.U
  io.rs2 := 0.U
  io.rd := 0.U
  io.imm := 0.S

  val opcode = io.instruction(6, 0) // op code for instructions
  val func3 = io.instruction(14, 12)
  val func7 = io.instruction(31, 25)

  // object of ImmGen file
  val immGen = Module(new ImmGen)
  immGen.instr := io.instruction // input of immgen := input of decoder 

   // Connect outputs based on opcode
  io.imm := immGen.imm_val // output of decoder := output of immgen

   switch(opcode) {
    is("b0110011".U) { // R-type
      io.aluOP := Cat(func3, func7(5))
      io.rs1 := io.instruction(19, 15)
      io.rs2 := io.instruction(24, 20)
      io.rd := io.instruction(11, 7)
    }
    is("b0010011".U) { // I-type
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rd := io.instruction(11, 7)
      io.rs2 := 0.U 
    }
    is("b0000011".U) { // Load instructions
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rd := io.instruction(11, 7)
      io.rs2 := 0.U
    }
    is("b0100011".U) { // S-type
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rs2 := io.instruction(24, 20)
      io.rd := 0.U
    }
    is("b1100011".U) { // B-type
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rs2 := io.instruction(24, 20)
      io.rd := 0.U 
    }
    is("b0110111".U) { // LUI
      io.aluOP := 0.U
      io.rs1 := 0.U 
      io.rs2 := 0.U
      io.rd := io.instruction(11, 7)
    }
    is("b0010111".U) { // AUIPC
      io.aluOP := 0.U
      io.rs1 := 0.U 
      io.rs2 := 0.U
      io.rd := io.instruction(11, 7)
    }
    is("b1101111".U) { // JAL
      io.aluOP := 0.U
      io.rs1 := 0.U 
      io.rs2 := 0.U 
      io.rd := io.instruction(11, 7)
    }
    is("b1100111".U) { // JALR
      io.aluOP := Cat(func3, 0.U)
      io.rs1 := io.instruction(19, 15)
      io.rs2 := 0.U 
      io.rd := io.instruction(11, 7)
    }
  }
}
