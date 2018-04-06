package hookinator.parser

interface FunctionSignature {
  val returnType: Type
  val name: String
  val callingConvetion: String
  val arguments: List<Variable>
}