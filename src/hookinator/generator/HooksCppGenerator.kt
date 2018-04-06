package hookinator.generator

class HooksCppGenerator(val functions: List<HookedFunction>, folder: String) {
  private val writer = CppWriter("$folder/hooks.cpp")

  fun generate() {
    writeIncludes()
    writeFunctions()
  }

  fun finish() = writer.finish()

  private fun writeIncludes() {
    writer.writeInclude("Winsock2.h")
    writer.writeInclude("Windows.h")
    writer.writeInclude("string")
    writer.writeDefine("FMT_HEADER_ONLY")
    writer.writeInclude("fmt/format.h")
    writer.writeNewLine()
  }

  private fun writeFunctions() {
    for (i in functions) {
      writeFunction(i)
      writer.writeNewLine()
    }
  }

  private fun writeFunction(function: HookedFunction) {
    writer.writeFunctionSignature(function, function.hookedName)
    writer.writeBeginStatament()

    writer.writeLogString("Entering function ${function.hookedName}")

    for (i in function.arguments) {

    }

    writer.writeLogString("Leaving function ${function.hookedName}")

    writer.writeEndStatament()
  }
}