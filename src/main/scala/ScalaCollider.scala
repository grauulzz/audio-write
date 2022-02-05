
import de.sciss.synth.*
import de.sciss.osc
import Ops.*
import Import.*
import ugen.*

class ScalaCollider {

  // default example in ScalaCollider
  def analogBubbles(): Unit = {
    play {
      val o = LFSaw.kr(Seq(8, 7.23)).mulAdd(3, 80)
      val f = LFSaw.kr(0.4).mulAdd(24, o)
      val s = SinOsc.ar(f.midiCps) * 0.04
      CombN.ar(s, 0, 0, 0)
    }
  }

  def spaceMining(): Unit = {
   play {
      val o = LFSaw.kr(Seq(0, 2.5)).mulAdd(-50, 50)
      val f = LFSaw.kr(- 0.4).mulAdd(12, o)
      val s = SinOsc.ar(f.midiCps) * 0.04
      CombN.ar(s, 0, 0, 0)
    }
  }

  def loFiMining(): Unit = {
    play {
      val f = LFSaw.kr(-100).mulAdd(24, LFSaw.kr(Seq(0, 5)).mulAdd(-50, 50)).midiCps
      CombN.ar(SinOsc.ar(f) * 0.04, 10, 0.1, 0.01)
    }
  }

  def loFiMiningInSpace(): Unit = {
    val x0 = play {
      loFiMining()
    }
    x0.onGo(spaceMining())
  }
}






