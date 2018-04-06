package hookinator.parser

data class Variable(val type: Type, val name: String) {
  fun toTypeNameString(): String {
    return "${type.toString()} $name"
  }
}