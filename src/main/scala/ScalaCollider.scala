
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
        val loFiMiningInSpace = play {
          synthSelect("loFiMining")
        }
        loFiMiningInSpace.onGo(synthSelect("spaceMining"))

      case "syntheticPiano" =>
        play {
          val n = 6        // number of keys playing
          Mix.fill(n) {    // mix an array of notes
            // calculate delay based on a random note
            val pitch  = IRand(36, 89)
            val strike = Impulse.ar(Rand(0.1, 0.5), Rand(0, 2*math.Pi)) * 0.05    // random period for each key
            val hammerEnv = Decay2.ar(strike, 0.008, 0.04)    // excitation envelope
            Pan2.ar(
              // array of 3 strings per note
              Mix.tabulate(3) { i =>
                // detune strings, calculate delay time :
                val detune = Array(-0.05, 0, 0.04)(i)
                val delayTime = 1 / (pitch + detune).midiCps
                // each string gets own exciter :
                val hammer = LFNoise2.ar(3000) * hammerEnv   // 3000 Hz was chosen by ear..
                CombL.ar(hammer,   // used as a string resonator
                  delayTime,     // max delay time
                  delayTime,     // actual delay time
                  6              // decay time of string
                )
              },
              (pitch - 36) / 27 - 1    // pan position: lo notes left, hi notes right
            )
          }
        }

      case "reverberatedSinePercussion" =>
        play {
          val d = 6    // number of percolators
          val c = 5    // number of comb delays
          val a = 4    // number of allpass delays

          // sine percolation sound :
          val s = Mix.fill(d) { Resonz.ar(Dust.ar(2.0 / d) * 50, Rand(200, 3200), 0.003) }

          // reverb pre-delay time :
          val z = DelayN.ar(s, 0.048)

          // 'c' length modulated comb delays in parallel :
          val y = Mix(CombL.ar(z, 0.1, LFNoise1.kr(Seq.fill(c)(Rand(0, 0.1))).mulAdd(0.04, 0.05), 15))

          // chain of 'a' allpass delays on each of two channels (2 times 'a' total) :
          val x = Mix.fold(y, a) { in =>
            AllpassN.ar(in, 0.050, Seq(Rand(0, 0.050), Rand(0, 0.050)), 1)
          }

          // add original sound to reverb and play it :
          s + 0.2 * x
        }

      case "sampleHold" =>
        play {
          val clockRate  = MouseX.kr(1, 200, 1)
          val clockTime  = clockRate.reciprocal
          val clock      = Impulse.kr(clockRate, 0.4)

          val centerFreq = MouseY.kr(100, 8000, 1)
          val freq       = Latch.kr(WhiteNoise.kr(centerFreq * 0.5) + centerFreq, clock)
          val panPos     = Latch.kr(WhiteNoise.kr, clock)
          CombN.ar(
            Pan2.ar(
              SinOsc.ar(freq) *
                Decay2.kr(clock, 0.1 * clockTime, 0.9 * clockTime),
              panPos
            ),
            0.3, 0.3, 2
          )
        }

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

