package single_cycle
import chisel3._
import chisel3.util._

class decoder extends Module {
  val io = IO(new Bundle {
    val instruction = Intput(UInt(32.W))
    val opcode = Output(UInt(7.W))
  })
  val opcode = io.instruction(6, 0)

  switch(opcode){
    is("b0010011".U){ // R 0110011
      io.opcode := opcode
    }
    is("b0010011".U){ // I 0010011
      io.opcode := opcode
    }
    is("b0100011".U){ // S 0100011
      io.opcode := opcode
    }
    is("b1100011".U){ // B 1100011
      io.opcode := opcode
    }
    is("b0110111".U){ // 0110111 (for LUI)
      io.opcode := opcode
    }
    is("b0010111".U){ //0010111 (for AUIPC)  
      io.opcode := opcode
    }
    is("b1101111".U){ // Jal 1101111
      io.opcode := opcode
    }
    is("b1100111".U){ // jalr 1100111
      io.opcode := opcode
    }
    }
}