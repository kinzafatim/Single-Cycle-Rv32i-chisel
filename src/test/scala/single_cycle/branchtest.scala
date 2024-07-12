package single_cycle
import chisel3._
import chisel3.util
import org.scalatest._
import chiseltest._
import chiseltest.experimental.TestOptionBuilder . _
class branchtest extends FreeSpec with ChiselScalatestTester {
"branch Test" in {
    test(new Branch){ x =>
    x.io.func3.poke(1.S)
    x.io.branch.poke(2.S)
    x.io.x1.poke(0.U)
    x.io.x2.poke(0.U)
    x.clock.step(1)
    x.io.br_taken.expect(3.S)
    }
}
}