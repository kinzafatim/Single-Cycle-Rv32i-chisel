package single_cycle
import chisel3._
import chisel3.util._

class regfile extends Module {
  val io = IO(new Bundle {
    val readAddr1 = Input(UInt(5.W)) //rs1
    val readAddr2 = Input(UInt(5.W)) //rs2
    val writeAddr = Input(UInt(5.W)) //rd

    val writeData = Input(UInt(32.W))
    val writeEnable = Input(Bool())
    val readData1 = Output(UInt(32.W))
    val readData2 = Output(UInt(32.W))
  })
  val regFile = Reg(Vec(32, UInt(32.W)))

  // Write operation
  when(io.writeEnable && io.writeAddr) {
  
  }
  
  
}