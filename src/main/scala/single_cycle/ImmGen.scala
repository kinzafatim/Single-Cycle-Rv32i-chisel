package single_cycle

import chisel3._
import chisel3.util._

class IO_ImmdValGen extends Bundle {
  val instr = Input(UInt(32.W))
  val imm_val = Output(SInt(32.W)) // Output as signed integer
}

class ImmGen extends Module {
  val io = IO(new IO_ImmdValGen)
  
  val wiree = WireInit(0.S(32.W)) // Initialize as signed integer
  io.imm_val := 0.S // Default value as signed integer

  val opcode = io.instr(6, 0)

  switch(opcode) {
    is("b0010011".U) { // I/ load-type
      wiree := Cat(Fill(20, io.instr(31)), io.instr(31, 20)).asSInt()
      io.imm_val := wiree
    }
    is("b0100011".U) { // Store-type
      wiree := Cat(Fill(20, io.instr(31)), io.instr(31), io.instr(30, 25), io.instr(11, 7)).asSInt()
      io.imm_val := wiree
    }
    is("b1100011".U) { // B-type
      wiree := Cat(Fill(19, io.instr(31)), io.instr(31), io.instr(7), io.instr(30, 25), io.instr(11, 8), 0.U(1.W)).asSInt()
      io.imm_val := wiree
    }
    is("b0110111".U) { // LUI
      wiree := Cat(io.instr(31, 12), Fill(12, 0.U)).asSInt()
      io.imm_val := wiree
    }
    is("b0010111".U) { // AUIPC
      wiree := Cat(io.instr(31, 12), Fill(12, 0.U)).asSInt()
      io.imm_val := wiree
    }
    is("b1101111".U) { // JAL
      wiree := Cat(Fill(11, io.instr(31)), io.instr(31), io.instr(19, 12), io.instr(20), io.instr(30, 21), 0.U(1.W)).asSInt()
      io.imm_val := wiree
    }
    is("b1100111".U) { // JALR
      wiree := Cat(Fill(20, io.instr(31)), io.instr(31, 20)).asSInt()
      io.imm_val := wiree
    }
  }
}
