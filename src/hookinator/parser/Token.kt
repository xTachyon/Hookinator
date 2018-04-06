package hookinator.parser

class Token(val tokenString: String) {

  fun isCallingConvention(): Boolean {
    return tokenString == "WINAPI" || tokenString == "WSAAPI"
  }

  fun isOpenParenthesis(): Boolean {
    return tokenString == "("
  }

  fun isClosedParenthesis(): Boolean {
    return tokenString == ")"
  }

  fun isComma(): Boolean {
    return tokenString == ","
  }

  fun isWinApiMacro(): Boolean {
    val macros = arrayOf("_In_", "__in", "__out", "__inout", "_Out_opt_", "_Inout_opt_", "_Inout_", "FAR")
    return macros.indexOf(tokenString) >= 0
  }

  fun isSemicolon(): Boolean {
    return tokenString == ";"
  }

  override fun toString(): String {
    return tokenString
  }
}