package hookinator.parser

data class ParserFunction(
    override val returnType: Type,
    override val name: String,
    override val callingConvetion: String,
    override val arguments: List<Variable>
): FunctionSignature