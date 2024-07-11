package single_cycle
import chisel3._
import chisel3.util._

class pc extends Module {
  val io = IO(new Bundle {
    val instruction = Input(SInt(32.W))
    val pc_out = Output(UInt(32.W))
    //val pc4_out = Output(UInt(32.W))
  })
  val pc = RegInit(0.U(32.W))

  pc := pc + 4.U
  io.pc_out := pc
}