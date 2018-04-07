package hookinator.generator

import hookinator.parser.Variable

class HookHppGeneratorval(val functions: List<HookedFunction>, folder: String) {
  private val writer = CppWriter("$folder/hooks.hpp")

  fun generate() {
    writer.write("#pragma once\n\n")
    writeIncludes()
    writeRealFunctions()
    writeHookedFunctions()
  }

  fun finish() = writer.finish()

  private fun writeIncludes() {
    writer.writeInclude("Winsock2.h")
    writer.writeInclude("Windows.h")
    writer.writeInclude("string")
//    writer.writeDefine("FMT_HEADER_ONLY")
    writer.writeInclude("fmt/format.h")
    writer.writeInclude("util.hpp", false)
    writer.writeNewLine()
  }

  fun generateArgsString(args: List<Variable>): String {
    val builder = StringBuilder()

    if (args.isNotEmpty()) {
      builder.append(args[0].toTypeNameString())
    }

    for (i in 1 until args.size) {
      builder.append(", ${args[i].toTypeNameString()}")
    }

    return builder.toString()
  }

  private fun writeRealFunctions() {
    for (i in functions) {
      val arguments = i.arguments.joinToString { x -> x.type.typeName }
      writer.write("extern ${i.returnType} (${i.callingConvetion} *real_${i.name})($arguments);\n")
    }
    writer.writeNewLine()
  }

  private fun writeHookedFunctions() {
    for (i in functions) {
      writer.writeFunctionSignature(i, i.hookedName)
      writer.write(";\n")
    }
  }
}