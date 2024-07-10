package single_cycle
import chisel3._
import chisel3.util._

object alu_op{
    val alu_add = 0.U(4.W)
    val alu_Sub = 1.U(4.W)
    val alu_Sll = 2.U(4.W)
    val alu_Slt = 4.U(4.W)
    val alu_Sltu = 6.U(4.W)
    val alu_Xor = 8.U(4.W)
    val alu_Srl = 10.U(4.W)
    val alu_Sra = 11.U(4.W)
    val alu_Or = 12.U(4.W)
    val alu_And = 14.U(4.W)
}
import alu_op._
class ALUI_O extends Bundle with Configg {
    val A = Input(UInt(32.W))
    val B = Input(UInt( 32.W))
    val alu_Op = Input(UInt(4.W) )
    val out = Output(UInt(32.W))
    val sum = Output(UInt(32.W))

}
class ALU extends Module with Configg{
    val io= IO(new ALUI_O)
    val sum = io.A + Mux(io.alu_Op(0),-io.B,io.B)

    val cmp= Mux(io.A(XLEN-1) === io.B(XLEN-1), sum(XLEN-1),Mux(io.alu_Op(1), io.B(XLEN-1) , io.A(XLEN-1)))
    
    val shamt = io.B(4 ,0).asUInt

    val shin = Mux(io.alu_Op(3), io.A, Reverse(io.A))

    val shiftr = (Cat(io.alu_Op(0) && shin(XLEN-1), shin).asSInt>>shamt)(XLEN-1 , 0)

    val shiftl = Reverse(shiftr)
    io.out := 0.U
    io.sum :=0.U
    switch(io.alu_Op){
        is(ALU_ADD){
            io.out:= sum
        }
        is(ALU_SUB){
            io.out:= sum
        }
        is(ALU_SLT){
            io.out:= cmp
        }
        is(ALU_SLTU){
            io.out:= cmp
        }
        is(ALU_SRA){
            io.out:=shiftr
        }
        is(ALU_SRL){
            io.out:=shiftr
        }
        is(ALU_SLL){
            io.out:=shiftl
        }
        is(ALU_AND){
            io.out:=(io.A & io.B)
        }
        is(ALU_OR){
            io.out:=(io.A | io.B)
        }
        is(ALU_XOR){
            io.out:=(io.A ^ io.B)
        }
        }
        io.sum := sum
    }