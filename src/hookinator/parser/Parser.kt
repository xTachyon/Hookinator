package hookinator.parser

class Parser(str: String) {
  private val tokenizer = Tokenizer(str)
  private val functions = ArrayList<ParserFunction>()

  val parsedFunctions: List<ParserFunction>
    get() = functions

  fun parse() {
    while (tokenizer.hasTokens()) {
      functions.add(parseFunction())
    }
  }

  private fun parseFunction(): ParserFunction {
    val returnType = parseType()
    val callingConvention = parseCallingConvetion()
    val name = tokenizer.getToken().tokenString
    val arguments = parseArguments()

    return ParserFunction(returnType, name, callingConvention, arguments)
  }

  private fun parseCallingConvetion(): String {
    val nextToken = tokenizer.peekToken()
    if (nextToken.isCallingConvention()) {
      tokenizer.skipToken()
      return nextToken.tokenString
    }
    return ""
  }

  private fun parseType(): Type {
    val type = tokenizer.getToken()
    return Type(type.tokenString)
  }

  private fun parseArguments(): List<Variable> {
    if (!tokenizer.getToken().isOpenParenthesis()) {
      throw RuntimeException("Missing (")
    }
    val result = ArrayList<Variable>()

    var token = tokenizer.peekToken()
    while (!token.isSemicolon()) {
      result.add(parseArgument())
      tokenizer.skipToken()
      token = tokenizer.peekToken()
    }
    tokenizer.skipToken()

    return result
  }

  private fun looksLikeType(str: String) = false

  private fun parseArgument(): Variable {
    val list = ArrayList<String>()

    var token = tokenizer.peekToken()
    while (!(token.isClosedParenthesis() || token.isComma())) {
      if (!token.isWinApiMacro()) {
        list.add(token.tokenString)
      }
      tokenizer.skipToken()
      token = tokenizer.peekToken()
    }

    var name = ""

    if (list.size > 0) {
      val lastStr = list.last()
      if (!looksLikeType(lastStr)) {
        name = lastStr
        list.removeAt(list.size - 1)
      }
    }

    val builder = StringBuilder()
    for (i in list) {
      builder.append(i).append(" ")
    }

    return Variable(Type(builder.toString().trim()), name)
  }
}