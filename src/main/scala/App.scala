import de.sciss.synth.Server
import de.sciss.synth.Ops.*

object App {

  val sc = new ScalaCollider

  val cfg: Server.ConfigBuilder = Server.Config()
  cfg.program = "C:\\Program Files\\SuperCollider-3.12.2\\scsynth.exe"

  def startServer(synth: String): Unit = {
    Server.run(cfg) { s =>
      println(s"${Console.BLUE} server running...")
      s.dumpOSC()
      sc.synthSelect(synth)
      s.freeAll()
      println(Console.RESET)
    }
  }

  @main def main(): Unit = {
    startServer("analogBubblesModulation")
  }

}

