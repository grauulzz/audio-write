
import de.sciss.synth.*
import de.sciss.osc
import Ops.*
import Import.*
import ugen.*

class ScalaCollider {

  // switch statement from hell
  def synthSelect(synthName: String): Unit = {
    synthName match {
      
      case "lfoModPulseWaves" =>
        val lfoModPulseWaves: Synth = play {
            CombL.ar(
              RLPF.ar(LFPulse.ar(FSinOsc.kr(0.05).mulAdd(80, 160), 0, 0.4) * 0.05,
                FSinOsc.kr(Seq(0.6, 0.7)).mulAdd(3600, 4000), 0.2),
              0.3, Seq(0.2, 0.25), 2)
        }
        
      case "analogBubbles" =>
        val analogBubbles: Synth = play {
          val o = LFSaw.kr(Seq(8, 7.23)).mulAdd(3, 80)
          val f = LFSaw.kr(0.4).mulAdd(24, o)
          val s = SinOsc.ar(f.midiCps) * 0.04
          CombN.ar(s, 0, 0, 0)
        }
        
      case "analogBubblesModulation" =>
        play {
          val analogBubblesModulation: SynthDef = SynthDef("AnalogBubbles") {
            val f1 = "freq1".kr(0.4)
            val f2 = "freq2".kr(8.0)
            val d  = "detune".kr(0.90375)
            val f  = LFSaw.ar(f1).mulAdd(24, LFSaw.ar(Seq(f2, f2 * d)).mulAdd(3, 80)).midiCps
            val x  = CombN.ar(SinOsc.ar(f) * 0.04, 0.2, 0.2, 4)
            Out.ar(0, x)
          }
          val modulate = analogBubblesModulation.play()
          modulate.set("freq1" -> 0.1)
          modulate.set("freq2" -> 222.2)
          modulate.set("detune" -> 0.44)
        }
        
      case "loFiMining" =>
        val synth4 = play {
          val f = LFSaw.kr(-100).mulAdd(24, LFSaw.kr(Seq(0, 5)).mulAdd(-50, 50)).midiCps
          CombN.ar(SinOsc.ar(f) * 0.04, 10, 0.1, 0.01)
        }
        
      case "spaceMining" =>
        val synth5 =  play {
          val o = LFSaw.kr(Seq(0, 2.5)).mulAdd(-50, 50)
          val f = LFSaw.kr(- 0.4).mulAdd(12, o)
          val s = SinOsc.ar(f.midiCps) * 0.04
          CombN.ar(s, 0, 0, 0)
        }
        
      case "loFiMiningInSpace" =>
        val synth6 = play {
          synthSelect("loFiMining")
        }
        
        synth6.onGo(synthSelect("spaceMining"))
      case _ => throw new Exception("Unknown synth")
    }
  }
}




//val setFreq = (freq: Double) => SynthDef("AnalogBubbles") {
//  val f1 = "freq1".kr(freq)
//}

//  def loFiMiningInSpace(): Unit = {
//    val x0 = play {
//      loFiMining()
//    }
//    x0.onGo(spaceMining())
//  }

