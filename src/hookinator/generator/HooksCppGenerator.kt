package hookinator.generator

import hookinator.parser.FunctionSignature

class HooksCppGenerator(val functions: List<HookedFunction>, folder: String) {
  private val writer = CppWriter("$folder/hooks.cpp")

  fun generate() {
    writeIncludes()
    writeRealFunctions()
    writeFunctions()
  }

  fun finish() = writer.finish()

  private fun writeRealFunctions() {
    for (i in functions) {
      val arguments = i.arguments.joinToString { x -> x.type.typeName }
      writer.write("${i.returnType} (${i.callingConvetion} *real_${i.name})($arguments) = ${i.name};\n")
    }
    writer.writeNewLine()
  }

  private fun writeIncludes() {
    writer.writeInclude("Winsock2.h")
    writer.writeInclude("Windows.h")
    writer.writeInclude("string")
//    writer.writeDefine("FMT_HEADER_ONLY")
    writer.writeInclude("fmt/format.h")
    writer.writeInclude("util.hpp", false)
    writer.writeInclude("hooks.hpp", false)
    writer.writeNewLine()
  }

  private fun writeFunctions() {
    for (i in functions) {
      writeFunction(i)
      writer.writeNewLine()
    }
  }

  private fun cppFormat(format: String, vararg args: String): String {
    val builder = StringBuilder()
    builder.append("fmt::format(\"$format\"")

    for (i in  args) {
      builder.append(", $i")
    }

    builder.append(")")
    return builder.toString()
  }

  private fun writeFunction(function: HookedFunction) {
    writer.writeFunctionSignature(function, function.hookedName)
    writer.writeBeginStatament()

    writer.writeLog(cppFormat("Entering function ${function.hookedName} in thread {}", "GetCurrentThreadId()"))

    writeRealFunctionCall(function)
    writeArgumentsWithValues(function)

    writer.writeLog(cppFormat("Leaving function ${function.hookedName} in thread {}", "GetCurrentThreadId()"))

    if (!function.returnType.isVoid()) {
      writer.write("return returned;\n")
    }

    writer.writeEndStatament()
  }

  private fun writeArgumentsWithValues(function: HookedFunction) {
    writer.write("std::string str;\n")

    for (i in function.arguments) {
      val towrite = when {
        i.type.isNumber() -> cppFormat("{}", i.name)
        else -> "\"...\""
      }

      writer.write("str += std::string(\"${i.name} = \") + $towrite + \"\\n\";\n")
    }

    writer.write("str += std::string(\"Return = \") + ${cppFormat("{}", "returned")};\n")
    writer.write("log(str);\n")
  }

  private fun writeRealFunctionCall(function: FunctionSignature) {
    if (!function.returnType.isVoid()) {
      writer.write("${function.returnType} returned = ")
    }

    val arguments = function.arguments.joinToString { x -> x.name }
    writer.write("real_${function.name}($arguments);\n")
  }
}