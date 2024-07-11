package single_cycle
import chisel3._
import chisel3.util
import org.scalatest._
import chiseltest._
import chiseltest.experimental.TestOptionBuilder . _
class Alutest extends FreeSpec with ChiselScalatestTester {
"ALU Test" in {
    test(new Alutest){ x =>
    x.io.in_A.poke(1.S)
    x.io.in_B.poke(2.S)
    x.io.alu_op.poke(0.U)
    x.clock.step(1)
    x.io.out.expect(3.S)
    }
}
}