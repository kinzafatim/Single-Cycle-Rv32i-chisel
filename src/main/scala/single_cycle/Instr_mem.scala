package single_cycle
import chisel3._
import chisel3.util._

class Instr_mem extends Module {
    val io = IO(new Bundle {
        val addr = Input(UInt(32.W))
        val instruction = Output(UInt(32.W))
  })
  val mem = Mem(1024, UInt(32.W)) 
  io.instruction := mem(io.addr)
}