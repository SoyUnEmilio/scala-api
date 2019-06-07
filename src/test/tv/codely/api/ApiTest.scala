package tv.codely.api

import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import spray.json._
import tv.codely.api.infrastructure.marshaller.UserMarshaller
import tv.codely.api.infrastructure.stubs.{UserIdStub, UserNameStub, UserStub}

final class ApiTest extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {
  private val routesWithDefinedResponses = Routes.all

  "ScalaHttpApi" should {

    /**
      * This is a really dummy test because with it we're testing nothing but Akka HTTP routing system.
      * As you can see in the routesWithDefinedResponses defined above, we've already provided an implementation.
      *
      * However, this is useful to start digging a little in how Akka HTTP testkit works and so on.
      * More information: https://doc.akka.io/docs/akka-http/current/scala/http/routing-dsl/testkit.html
      */
    "respond successfully while requesting its status" in {
      Get("/status") ~> routesWithDefinedResponses ~> check {
        status shouldBe StatusCodes.OK
        contentType shouldBe ContentTypes.`application/json`
        entityAs[String] shouldBe """{"status":"ok"}"""
      }
    }
    "return all the system users" in {
      Get("/users") ~> Routes.all ~> check {

        // Instanciación de los usuarios esperados a través de los stubs:
        val expectedUsers = Seq(
          UserStub(UserIdStub("123"), UserNameStub("Edufasio")),
          UserStub(UserIdStub("456"), UserNameStub("Edonisio"))
        )

        status shouldBe StatusCodes.OK
        contentType shouldBe ContentTypes.`application/json`

        // Comprobación del resultado a través del marshaller de test:
        entityAs[String].parseJson shouldBe UserMarshaller.marshall(expectedUsers)
      }
    }
  }
}
