package tv.codely.api.infrastructure.stubs

import scala.util.Random

object StringStub {
  def random(numChars: Int): String = Random.alphanumeric take numChars mkString ""
}
