import de.sciss.synth.Server


object Main extends App {

  val sc = new ScalaCollider

  val cfg: Server.ConfigBuilder = Server.Config()
  cfg.program = "C:\\Program Files\\SuperCollider-3.12.2\\scsynth.exe"

  Server.run(cfg) { s =>
    s.dumpOSC() //dumps readable info to console
    sc.loFiMiningInSpace()
  }
}
