package tv.codely.api

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import tv.codely.api.domain.User
import tv.codely.api.infrastructure.UserMarshaller._

object Routes {
  private val systemUsers = Seq(
    User("123", "Edufasio"),
    User("456", "Edonisio"),
  )

  val all: Route = get {
    path("status") {
      complete(HttpEntity(ContentTypes.`application/json`, """{"status":"ok"}"""))
    } ~
      path("users") {
        complete(systemUsers)
      }
  }
}
