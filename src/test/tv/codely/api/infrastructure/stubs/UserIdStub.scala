package tv.codely.api.infrastructure.stubs

object UserIdStub {
  private val userIdLength = 3

  def apply(value: String): String = value

  def random: String = StringStub.random(numChars = userIdLength)
}
