package hookinator.parser

class Tokenizer(private val str: String) {
  private var position = 0

  private fun isSpecial(x: Char): Boolean {
    return "()*,;".indexOf(x) >= 0
  }

  private fun isVeryWhitespace(x: Char): Boolean {
    return Character.isWhitespace(x) || Character.isSpaceChar(x)
  }

  private fun skipWhitespaces() {
    while (position < str.length && isVeryWhitespace(str[position])) {
      ++position
    }
  }

  private fun getIfIsSpecial(): String? {
    if (position < str.length && isSpecial(str[position])) {
      return str[position++].toString()
    }
    return null
  }

  private fun getNormalToken(): String {
    val startpos = position
    while (position < str.length) {
      val char = str[position]
      if (isSpecial(char) || isVeryWhitespace(char)) {
        break
      }
      ++position
    }

    return str.substring(startpos, position)
  }

  fun getToken(): Token {
    skipWhitespaces()

    val returnValue= getIfIsSpecial() ?: getNormalToken()

    return Token(returnValue)
  }

  fun peekToken(): Token {
    val savedposition = position
    val token = getToken()
    position = savedposition
    return token
  }

  fun skipToken() {
    getToken()
  }

  fun hasTokens(): Boolean {
    skipWhitespaces()
    return position < str.length
  }
}