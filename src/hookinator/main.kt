package hookinator

import hookinator.generator.HookGenerator
import hookinator.parser.Parser
import java.nio.file.Files
import java.nio.file.Paths

fun main(array: Array<String>) {
  val str = String(Files.readAllBytes(Paths.get("in.txt")))
  val parser = Parser(str)
  parser.parse()
  val generator = HookGenerator(parser.parsedFunctions)
  generator.write()
}