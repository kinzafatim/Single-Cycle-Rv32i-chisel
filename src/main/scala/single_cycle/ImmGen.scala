package single_cycle
import chisel3._
import chisel3.util._

class IO_ImmdValGen extends Bundle {
    val instr = Input(UInt(32.W))
    val opcode = Input(UInt(7.W))
    val out_ins = Output(UInt(32.W))
}

class ImmGen extends Module {
    val io = IO(new IO_ImmdValGen)
    val wiree = WireInit(0.U(32.W))
    io.out_ins := 0.U
    switch(io.opcode) {
        is("b0010011".U) { // I 0010011
            wiree := Cat(Fill(20, io.instr(31)), io.instr(31, 20))
            io.out_ins := wiree
        }
        is("b0100011".U) { // S 0100011
            wiree := Cat(Fill(20, io.instr(31)), io.instr(31), io.instr(30, 25), io.instr(11, 7))
            io.out_ins := wiree
        }
        is("b1100011".U) { // B 1100011
            wiree := Cat(Fill(18, io.instr(31)), io.instr(31), io.instr(7), io.instr(30, 25), io.instr(11, 8))
            io.out_ins := wiree
        }
        is("b0110111".U) { // 0110111 (for LUI)
            wiree := Cat(Fill(12, 0.U), io.instr(31, 12))
            io.out_ins := wiree
        }
        is("b0010111".U) { //0010111 (for AUIPC)  
            wiree := Cat(Fill(12, 0.U), io.instr(31, 12))
            io.out_ins := wiree
        }
        is("b1101111".U) { // Jal 1101111
            wiree := Cat(Fill(11, io.instr(31)), io.instr(31), io.instr(19, 12), io.instr(20), io.instr(30, 21))
            io.out_ins := wiree
        }
        is("b1100111".U) { // jalr 1100111
            wiree := Cat(Fill(11, io.instr(31)), io.instr(31, 20))
            io.out_ins := wiree
        }
    }
}
