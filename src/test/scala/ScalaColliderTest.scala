
import de.sciss.synth.Server
import de.sciss.synth.ugen.{CombN, LFSaw, SinOsc}

final class ScalaColliderTest extends TestSuite {

  private val config = Server.Config()

  override def beforeEach(): Unit = {
    config.program = "C:\\Program Files\\SuperCollider-3.12.2\\scsynth.exe"
  }

  test("configPath_startsSever") {
    Server.run(config) { s =>
      assert(s.isRunning)
    }
  }
}

