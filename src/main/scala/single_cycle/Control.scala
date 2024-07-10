package single_cycle

import chisel3._
import chisel3.util._

class Control extends Module {
  val io = IO(new Bundle {
    val instruction = Input(UInt(32.W))
    
    val regWrEn = Output(Bool())
    val branch = Output(Bool())
  })

  // Default values
  io.regWrEn := 0.B
  io.branch := 0.B

  val opcode = io.instruction(6, 0)
 

  switch(opcode) {
    is("b0110011".U) { // R-type
      io.regWrEn := 1.B
      io.branch := 0.B
    }
    is("b0010011".U) { // I-type
      io.regWrEn := 1.B
      io.branch := 0.B
    }
    is("b0100011".U) { // S-type
      io.regWrEn := 0.B
      io.branch := 0.B
    }
    is("b1100011".U) { // B-type

      io.regWrEn := 0.B
      io.branch := 1.B
    }
    is("b0110111".U) { // LUI

      io.regWrEn := 1.B
      io.branch := 0.B
    }
    is("b0010111".U) { // AUIPC

      io.regWrEn := 1.B
      io.branch := 0.B
    }
    is("b1101111".U) { // JAL

      io.regWrEn := 1.B
      io.branch := 0.B
    }
    is("b1100111".U) { // JALR

      io.regWrEn := 1.B
      io.branch := 0.B
    }
  }
}
