package single_cycle
import chisel3._
import chisel3.util
import org.scalatest._
import chiseltest._
import chiseltest.experimental.TestOptionBuilder . _
class Controltest extends FreeSpec with ChiselScalatestTester {
"Control Test" in {
    test(new Control){ x =>
    x.io.instruction.poke(.S)
    
    x.clock.step(10)

    x.io.regWrEn.expect(.S)
    x.io.branch.expect(.S)
    x.io.memRead.expect(.S)
    x.io.memWrite.expect(.S)
    x.io.memToReg.expect(.S)
    x.io.aluImm.expect(.S)
    x.io.branchfun3.expect(.S)
    x.io.btaken.expect(.S)


    }
}
}