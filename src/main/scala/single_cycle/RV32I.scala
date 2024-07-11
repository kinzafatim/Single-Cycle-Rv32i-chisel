package single_cycle
import chisel3._
import chisel3.util._

class RV32I extends Module {
    val io = IO(new Bundle{
        val instruction = Output(UInt(32.W))
        val out = Output(SInt(32.W))
    })
    
    io.out := 0.S
}