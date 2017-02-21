package bench

import firrtl._
import firrtl.ir._
import scala.io.Source
import java.io.File

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@BenchmarkMode(Array(Mode.AverageTime))
@Fork(1)
@Threads(1)
class BenchmarkVerilogCompiler {

  // Resource from root project directory
  private def parse(resource: String): Circuit = {
    val source = Source.fromFile(new File(System.getProperty("user.dir") + "/" + resource))
    firrtl.Parser.parse(source.getLines)
  }

	private def compile(c: Circuit): Unit = {
    val compiler = new VerilogCompiler
    compiler.compile(CircuitState(c, ChirrtlForm), new java.io.StringWriter)
	}

  var rob: Circuit = _

  @Setup
  def parseRob: Unit = rob = parse("firrtl/regress/Rob.fir")

  @Benchmark
  def measureRob: Unit = compile(rob)

  var rocket: Circuit = _

  @Setup
  def parseRocket: Unit = rocket = parse("regress/rocketchip.DefaultL2Config.fir")

  @Benchmark
  def measureRocket: Unit = compile(rocket)
}
