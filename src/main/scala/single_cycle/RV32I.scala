package single_cycle
import chisel3._
import chisel3.util._

class RV32I extends Module {
    val io = IO(new Bundle{
        val instruction = Output(UInt(32.W))
        val out = Output(SInt(32.W))
    })

    io.out := 0.S
    // objects of modules
    val alu_Module    = Module(new ALU) 
    val branch_Module = Module(new Branch)
    val control_Module = Module(new Control)
    val datamem_Module = Module(new Datamem)
    val decoder_Module = Module(new Decoder)
    val immdGen_Module = Module(new ImmGen)
    val instr_mem_Module = Module(new Instr_mem)
    val pc_Module = Module(new Pc)
    val regFile_Module = Module(new regfile)
    

    pc_Module.io.addr :=



   
}