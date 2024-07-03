package single_cycle
import chisel3._
import chisel3.util._
object branch{
  // ALU Operations, may expand / modify in future
  val beq   = 0.U(3.W)
  val bne   = 1.U(3.W)
  val blt   = 4.U(3.W)
  val bge    = 5.U(3.W)
  val bltu   = 6.U(3.W)
  val bgeu   = 7.U(3.W) 
}
import branch._
class LM_IO_Interface_BranchControl extends Bundle {
    val fnct3 = Input(UInt(3.W))
    //val branch= Input(Bool())
    val arg_x = Input(UInt(32.W))
    val arg_y = Input(UInt(32.W))
    val br_taken = Output(Bool())
}
class Branch extends Module {
    val io = IO(new LM_IO_Interface_BranchControl)
    io.br_taken:=0.B
    switch(io.fnct3){
        is(beq){
            io.br_taken:= io.arg_x === io.arg_y
        }
        is(bne){
                io.br_taken:= io.arg_x =/= io.arg_y
        }
        is(blt){
                io.br_taken:= io.arg_x < io.arg_y
        }
        is(bge){
                io.br_taken:= io.arg_x >= io.arg_y
        }
        is(bltu){
                io.br_taken:= io.arg_x.asUInt < io.arg_y.asUInt
        }
        is(bgeu){
                io.br_taken:= io.arg_x.asUInt >= io.arg_y.asUInt
        }
    }
}
