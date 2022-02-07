
import de.sciss.synth.*
import de.sciss.osc
import Ops.*
import Import.*
import ugen.*

class ScalaCollider {
  def selectScalaColliderPreset(preset: String): Synth = {
    preset match {
      case "lfoModPulseWaves" => lfoModPulseWaves()
      case "analogBubbles" => analogBubbles()
      case "syntheticPiano" => syntheticPiano()
      case "reverberatedSinePercussion" => reverberatedSinePercussion()
      case "mouseInputSampleHold" => mouseInputSampleHold()
      case _ => throw new Exception("Unknown synth")
    }
  }
  
  // scala collider presets

  def lfoModPulseWaves(): Synth = {
    play {
      CombL.ar(
        RLPF.ar(LFPulse.ar(FSinOsc.kr(0.05).mulAdd(80, 160), 0, 0.4) * 0.05,
          FSinOsc.kr(Seq(0.6, 0.7)).mulAdd(3600, 4000), 0.2),
        0.3, Seq(0.2, 0.25), 2)
    }
  }

  def analogBubbles(): Synth = {
    play {
      val o = LFSaw.kr(Seq(8, 7.23)).mulAdd(3, 80)
      val f = LFSaw.kr(0.4).mulAdd(24, o)
      val s = SinOsc.ar(f.midiCps) * 0.04
      CombN.ar(s, 0, 0, 0)
    }
  }

  def syntheticPiano(): Synth = {
    play {
      val n = 6
      Mix.fill(n) {

        val pitch  = IRand(36, 89)
        val strike = Impulse.ar(Rand(0.1, 0.5), Rand(0, 2*math.Pi)) * 0.05
        val hammerEnv = Decay2.ar(strike, 0.008, 0.04)
        Pan2.ar(

          Mix.tabulate(3) { i =>

            val detune = Array(-0.05, 0, 0.04)(i)
            val delayTime = 1 / (pitch + detune).midiCps

            val hammer = LFNoise2.ar(3000) * hammerEnv
            CombL.ar(hammer,
              delayTime,
              delayTime,
              6
            )
          },
          (pitch - 36) / 27 - 1    // pan
        )
      }
    }
  }

  def reverberatedSinePercussion(): Synth = {
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
  }

  def mouseInputSampleHold(): Synth = {
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
  }
  
}

