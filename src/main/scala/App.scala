import de.sciss.synth.Server

object App {

  val java = new JavaApp()

  @main def run(): Unit = {

    println(s"${Console.CYAN} ${java.getSomethingFromJavaApp}! ${Console.RESET}")

    val sc = new ScalaCollider

    val cfg: Server.ConfigBuilder = Server.Config()
    cfg.program = "C:\\Program Files\\SuperCollider-3.12.2\\scsynth.exe"

    Server.run(cfg) { s =>

      println(s"${Console.BLUE} output from Scala app!")

      s.dumpOSC()

      sc.loFiMiningInSpace()

    }

    println(Console.RESET)

  }

}


// println(s"${Console.CYAN} calling scala code from project... ${sc.getSomethingFromScalaCollider}! ${Console.RESET}")
