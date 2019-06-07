package tv.codely.api

import org.scalatest._
import org.scalatest.Matchers._

final class ApiTest extends WordSpec with GivenWhenThen {
  "Api" should {
    "greet" in {
      Given("a Api")

      val api = new Api

      When("we ask him to greet someone")

      val nameToGreet = "CodelyTV"
      val greeting = api.greet(nameToGreet)

      Then("it should say hello to someone")

      greeting shouldBe "Hello " + nameToGreet
    }
  }
}
