package hookinator.parser

data class Type(val typeName: String) {
  fun isNumber(): Boolean {
    val values = arrayOf("int", "DWORD", "SOCKET")
    return values.indexOf(typeName) > 0
  }

  fun isVoid() = typeName == "void"

  override fun toString(): String {
    return typeName
  }
}