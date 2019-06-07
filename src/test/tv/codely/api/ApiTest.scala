package tv.codely.api

import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{GivenWhenThen, Matchers, WordSpec}
import akka.http.scaladsl.server.Directives._


final class ApiTest extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest with GivenWhenThen {

  object MainRouter {
    val routes = routeForStatus.route ~ routeForGreet.route
  }

  object routeForGreetBeautifulUrl {
    val route =
    path("greet" / Segment) {
      name =>
        get {
          complete(s"Hello $name")
        }
    }
  }

  object routeForGreetUglyUrl {
    val route =
      pathPrefix("greet") {
      get {
        parameter('name) {
          name => complete(s"Hello $name")
        }
      }
    }
  }

  object routeForGreet {
    val route = routeForGreetBeautifulUrl.route ~ routeForGreetUglyUrl.route
  }

  object routeForStatus {
    val route =
    get {
      path("status") {
        complete(HttpEntity(ContentTypes.`application/json`, """{"status":"ok"}"""))
      }
    }
  }

  "Api" should {
    "respond successfully while requesting its status" in {

      Given("a route with defined response for status")
      val routeWithDefinedResponseForStatus = routeForStatus.route

      When("we ask the status")
      Get("/status") ~> routeWithDefinedResponseForStatus ~> check {
        Then("it should respond with status OK")
        status shouldBe StatusCodes.OK
        Then("it should respond with contentType application/json")
        contentType shouldBe ContentTypes.`application/json`
        Then("it should respond with status: ok json string")
        entityAs[String] shouldBe """{"status":"ok"}"""
      }
    }

    "respond successfully while greeting a name with a beautiful url" in {
      Given("a route with defined response for greet")
      val routesWithDefinedResponsesForGreet = routeForGreetBeautifulUrl.route

      When("we ask to greet a name with a beautiful url")
      Get("/greet/emilio") ~> routesWithDefinedResponsesForGreet ~> check {
        Then("it should respond with Hello name")
        entityAs[String] shouldBe "Hello emilio"
      }
    }

    "respond successfully while greeting a name with ugly url" in {
      Given("a route with defined response for greet")
      val routesWithDefinedResponsesForGreet = routeForGreetUglyUrl.route

      When("we ask to greet a name with an ugly url")
      Get("/greet?name=emilio") ~> routesWithDefinedResponsesForGreet ~> check {
        Then("it should respond with Hello name")
        entityAs[String] shouldBe "Hello emilio"
      }
    }
  }
}
