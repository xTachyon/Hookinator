package hookinator.generator

import hookinator.parser.FunctionSignature
import hookinator.parser.Variable
import java.io.BufferedWriter
import java.io.FileWriter

class CppWriter(private val filename: String) {
  private val builder = StringBuilder()

  fun finish() {
    val file = BufferedWriter(FileWriter(filename))
    file.write(builder.toString())
    file.close()
  }

  fun writeInclude(include: String, standard: Boolean = true) {
    val quotStart = when {
      standard -> '<'
      else -> '\"'
    }
    val quotEnd = when {
      standard -> '>'
      else -> '\"'
    }

    builder.append("#include $quotStart$include$quotEnd\n")
  }

  fun writeDefine(define: String) {
    builder.append("#define $define\n")
  }

  fun writeFunctionSignature(function: FunctionSignature, name: String) {
    builder.append("${function.returnType} ${function.callingConvetion} $name(${generateArgsString(function.arguments)})")
  }

  private fun generateArgsString(args: List<Variable>): String {
    val builder = StringBuilder()

    if (args.isNotEmpty()) {
      builder.append(args[0].toTypeNameString())
    }

    for (i in 1 until args.size) {
      builder.append(", ${args[i].toTypeNameString()}")
    }

    return builder.toString()
  }

  fun writeBeginStatament() {
    builder.append(" {\n")
  }

  fun writeEndStatament() {
    builder.append("}\n")
  }

  fun writeLog(string: String) {
    builder.append("log($string);\n")
  }

  fun writeLogString(string: String) {
    writeLog("\"$string\"")
  }

  fun writeNewLine() {
    builder.append("\n")
  }

  override fun toString(): String {
    return builder.toString()
  }
}