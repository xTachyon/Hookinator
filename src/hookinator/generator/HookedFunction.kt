package hookinator.generator

import hookinator.parser.FunctionSignature
import hookinator.parser.ParserFunction
import hookinator.parser.Type
import hookinator.parser.Variable

data class HookedFunction(val function: ParserFunction): FunctionSignature {
  override val returnType: Type
    get() = function.returnType
  override val name: String
    get() = function.name
  override val callingConvetion: String
    get() = function.callingConvetion
  override val arguments: List<Variable>
    get() = function.arguments
  val hookedName = generateFunctionHookedName(function)
}