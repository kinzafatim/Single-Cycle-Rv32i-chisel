package single_cycle
import chisel3._
import chisel3.util._

class RV32I extends Module {
    val io = IO(new Bundle{
        val out = Output(SInt(32.W))
    })
    io.out := 0.S

    // objects of modules
    val alu_Module       = Module(new ALU) 
    val branch_Module    = Module(new Branch)
    val control_Module   = Module(new Control)
    val datamem_Module   = Module(new Datamem)
    val decoder_Module   = Module(new Decoder)
    val immdGen_Module   = Module(new ImmGen)
    val instr_mem_Module = Module(new Instr_mem)
    val pc_Module        = Module(new Pc)
    val regFile_Module   = Module(new regfile)
    
    //         inputs       :=         outputs

    // Instruction memory fetch
    instr_mem_Module.io.addr := pc_Module.io.pc_out
    
    // pc connections
    pc_Module.io.instruction := instr_mem_Module.io.inst
    pc_Module.pcsel := control_Module.io.pcsel
    pc_Module.aluout := alu_Module.io.out

    control_Module.io.instruction:= instr_mem_Module.io.inst
    control_Module.io.btaken := branch_Module.io.br_taken

    // branch
    branch_Module.io.fnct3 := control_Module.io.branchfun3
    branch_Module.io.branch := control_Module.io.branch
    branch_Module.io.x1 := regFile_Module.io.rs1
    branch_Module.io.x2 := regFile_Module.io.rs2
    // branch_Module := 

    // Register file connections
    regFile_Module.io.readAddr1 := control_Module.io.rs1
    regFile_Module.io.readAddr2 := control_Module.io.rs2
    regFile_Module.io.writeAddr := control_Module.io.rd
    regFile_Module.io.writeData := // not sure Mux(control_Module.io.memToReg, datamem_Module.io.addr, alu_Module.io.out)
    regFile_Module.io.writeEnable := control_Module.io.regWrEn
    
    // Alu connections
    alu_Module.io.in_A := regFile_Module.io.rs1
    alu_Module.io.in_B := Mux(control_Module.io.aluImm, control_Module.io.imm ,regFile_Module.io.rs2)
    alu_Module.io.alu_Op := control_Module.io.aluOP

    // Data memory connections
   // datamem_Module.io.addr := alu_Module.io.out 
    //datamem_Module.io.writeData := regFile_Module.io.rs2
    datamem_Module.io.memRead := control_Module.io.memRead
    datamem_Module.io.memWrite := control_Module.io.memWrite

}