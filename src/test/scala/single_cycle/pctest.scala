package single_cycle
import chisel3._
import chisel3.tester._
import org.scalatest.FreeSpec
import chisel3.experimental.BundleLiterals._

class pctest extends FreeSpec with ChiselScalatestTester {
  "Program counter" in {
    test(new pc){ x =>
      //  a.io.addr.poke(4.U)
       x.clock.step()
       x.io.pc_out.expect(.U)
       
    } 
  }
}