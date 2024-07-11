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
    val in_A = Input(UInt(32.W))
    val in_B = Input(UInt( 32.W))
    val alu_Op = Input(UInt(4.W) )
    val out = Output(UInt(32.W))
    val sum = Output(UInt(32.W))

}
class ALU extends Module with Configg{
    val io= IO(new ALUI_O)
    val sum = io.in_A + Mux(io.alu_Op(0),-io.in_B, io.in_B)

    val cmp= Mux(io.A(XLEN-1) === io.in_B(XLEN-1), sum(XLEN-1),Mux(io.alu_Op(1), io.in_B(XLEN-1) , io.in_A(XLEN-1)))
    
    val shamt = io.in_B(4 ,0).asUInt  // Shift amount from input B

    val shin = Mux(io.alu_Op(3), io.in_A, Reverse(io.in_A))

    val shiftr = (Cat(io.alu_Op(0) && shin(XLEN-1), shin).asSInt>>shamt)(XLEN-1 , 0)

    val shiftl = Reverse(shiftr)  // Shift left logical based on alu_op

    io.out := 0.U // default value 0
    io.sum :=0.U  // default value 0

    switch(io.alu_Op){
        is(ALU_ADD){ // add. addi 
            io.out:= sum
        }
        is(ALU_SUB){ // sub
            io.out:= sum
        }
        is(ALU_SLT){ // slt
            io.out:= cmp
        }
        is(ALU_SLTU){ //sltu
            io.out:= cmp
        }
        is(ALU_SRA){ // sra
            io.out:=shiftr
        }
        is(ALU_SRL){ // srl
            io.out:=shiftr
        }
        is(ALU_SLL){ // sll
            io.out:=shiftl
        }
        is(ALU_AND){ // and
            io.out:=(io.in_A & io.in_B)
        }
        is(ALU_OR){ // or
            io.out:=(io.in_A | io.in_B)
        }
        is(ALU_XOR){ // xor
            io.out:=(io.in_A ^ io.in_B)
        }
        }
        io.sum := sum
    }