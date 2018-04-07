package hookinator.generator

import hookinator.parser.ParserFunction
import java.io.File

class HookGenerator(initialFunctions: List<ParserFunction>) {
  private val functions = generateHookedFunctionsSignature(initialFunctions)

  private val folder = "D:\\programs\\4story_spy\\msimg32\\"

  private val hookscpp = HooksCppGenerator(functions, folder)
  private val hookshpp = HookHppGeneratorval(functions, folder)
  private val dllmaincpp = DllMainCppGenerator(functions, folder)

  init {
    File("cpp/").mkdirs()
  }

  fun write() {
    hookscpp.generate()
    hookscpp.finish()

    hookshpp.generate()
    hookshpp.finish()

    dllmaincpp.generate()
    dllmaincpp.finish()
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