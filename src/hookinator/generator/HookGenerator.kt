package hookinator.generator

import hookinator.parser.ParserFunction
import java.io.File

class HookGenerator(val initialFunctions: List<ParserFunction>) {
  private val functions = generateHookedFunctionsSignature(initialFunctions)
  private val hookscpp = HooksCppGenerator(functions, "cpp/")

  init {
    File("cpp/").mkdirs()
  }

  fun write() {
    hookscpp.generate()
    hookscpp.finish()
  }
}

fun generateFunctionHookedName(function: ParserFunction): String {
  return "hooked_${function.name}"
}

fun generateHookedFunctionsSignature(functions: List<ParserFunction>): List<HookedFunction> {
  val list = ArrayList<HookedFunction>()
  for (i in functions) {
    list.add(HookedFunction(i))
  }
  return list
}