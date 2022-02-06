
import de.sciss.synth.*
import de.sciss.osc
import Ops.*
import Import.*
import ugen.*

class ScalaCollider {

  // this would make more sense as a constructor for this class
  def synthSelect(scalaCollider: String): Unit = {
    scalaCollider match {
      case "analogBubbles" => analogBubbles()
      case "analogBubblesModulation" => analogBubblesModulation()
      case "spaceMining" => spaceMining()
      case "loFiMining" => loFiMining()
      case "loFiMiningInSpace" => loFiMiningInSpace()
      case _ => throw new Exception("Unknown synth")
    }
  }

  // default example in ScalaCollider
  def analogBubbles(): Unit = {
    play {
      val o = LFSaw.kr(Seq(8, 7.23)).mulAdd(3, 80)
      val f = LFSaw.kr(0.4).mulAdd(24, o)
      val s = SinOsc.ar(f.midiCps) * 0.04
      CombN.ar(s, 0, 0, 0)
    }
  }

  def analogBubblesModulation(): Unit = {

    val df1 = SynthDef("AnalogBubbles") {
      val f1 = "freq1".kr(0.4)
      val f2 = "freq2".kr(8.0)
      val d  = "detune".kr(0.90375)
      val f  = LFSaw.ar(f1).mulAdd(24, LFSaw.ar(Seq(f2, f2 * d)).mulAdd(3, 80)).midiCps // glissando function
      val x  = CombN.ar(SinOsc.ar(f) * 0.04, 0.2, 0.2, 4) // echoing sine wave
      Out.ar(0, x)
    }

    val x1 = df1.play()

    play {
      x1.set("freq1" -> 0.1)
      x1.set("freq2" -> 222.2)
      x1.set("detune" -> 0.44)
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






