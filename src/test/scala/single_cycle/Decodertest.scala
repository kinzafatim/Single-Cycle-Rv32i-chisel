package single_cycle
import chisel3._
import chisel3.util
import org.scalatest._
import chiseltest._
import chiseltest.experimental.TestOptionBuilder . _
class Decodertest extends FreeSpec with ChiselScalatestTester {
"Decoder Test" in {
    test(new Decoder){ x =>
    x.io.instruction.poke(.S)
    
    x.clock.step(10)

    x.io.aluOP.expect(.S)
    x.io.rs1.expect(.S)
    x.io.rs2.expect(.S)
    x.io.rd.expect(.S)
    x.io.imm.expect(.S)

    }
}
}