package lab4

import chisel3._ 
import chisel3.util._ 


object ALUOP {
    val ALU_ADD = 0.U(4.W)
    val ALU_SUB = 1.U(4.W)
    val ALU_AND = 2.U(4.W)
    val ALU_OR  = 3.U(4.W)
    val ALU_XOR = 4.U(4.W)
    val ALU_SLT = 5.U(4.W)
    val ALU_SLL = 6.U(4.W)
    val ALU_SLTU= 7.U(4.W)
    val ALU_SRL = 8.U(4.W)
    val ALU_SRA = 9.U(4.W)
    val ALU_COPY_A = 10.U(4.W)
    val ALU_COPY_B = 11.U(4.W)
    val ALU_XXX = 12.U(4.W) 
}

trait Config{
    val WLEN = 32
    val ALUOP_SIG_LEN = 4
}

import ALUOP._

class ALUIO extends Bundle with Config {
    val A = Input(UInt(WLEN.W))
    val B = Input(UInt(WLEN.W))
    val alu_Op = Input(UInt(ALUOP_SIG_LEN.W))
    val out = Output(UInt(WLEN.W))
    val sum = Output(UInt(WLEN.W))
}

class ALU extends Module with Config {
    val io = IO(new ALUIO)

    val sum = io.A + Mux(io.alu_Op(0), io.B, -io.B)
    val cmp = Mux(io.A(WLEN-1) === io.B(WLEN-1), sum(WLEN-1),
                Mux(io.alu_Op(1), io.A(WLEN-1), io.B(WLEN-1)))
    val shamt = io.A(4,0).asUInt
    val shin = Mux(io.alu_Op(3), Reverse(io.A), io.A)
    val shiftr = (Cat(io.alu_Op(0) && shin, shin(WLEN-1)).asSInt >> shamt)(WLEN-1,0)
    val shitfl = Reverse(shiftr)
    val out = 
    Mux(io.alu_Op === ALU_ADD || io.alu_Op === ALU_SUB, sum, 
    Mux(io.alu_Op === ALU_SLT || io.alu_Op === ALU_SLTU, cmp, 
    Mux(io.alu_Op === ALU_SRA || io.alu_Op === ALU_SRL, shiftl,
    Mux(io.alu_Op === ALU_SLL, shitfr, 
    Mux(io.alu_Op === ALU_AND, (io.A && io.B),
    Mux(io.alu_Op === ALU_OR, (io.A | io.B),
    Mux(io.alu_Op === ALU_XOR, (io.A ^ io.B),
    Mux(io.alu_Op === ALU_COPY_A, io.A, 
    Mux(io.alu_Op === ALU_COPY_B, io.A, 0.U)))))))))


    io.out := out
    io.sum := sum
}
