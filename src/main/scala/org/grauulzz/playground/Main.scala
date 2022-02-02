package org.grauulzz

import de.sciss.synth.*

import ugen.*
import Import.*
import Ops.*

@main def Main(args: String*): Unit = {
    println(Console.GREEN)

    val cfg = Server.Config()
    cfg.program = "/mnt/c/Program Files/SuperCollider-3.12.2/scsynth.exe"

    Server.run(cfg) { s =>
        s.dumpOSC()
        play {
            val f = LFSaw.kr(0.4).mulAdd(24, LFSaw.kr(Seq(8, 7.23)).mulAdd(3, 80)).midiCps
            CombN.ar(SinOsc.ar(f) * 0.04, 0.2, 0.2, 4)
        }
    }
}
