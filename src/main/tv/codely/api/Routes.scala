package tv.codely.api

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.{complete, get, path}

object Routes {
  val all: Route = get {
    path("status") {
      complete(HttpEntity(ContentTypes.`application/json`, """{"status":"ok"}"""))
    }
  }
}
