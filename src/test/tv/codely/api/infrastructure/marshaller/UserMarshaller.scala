package tv.codely.api.infrastructure.marshaller

import spray.json.{JsArray, JsObject, JsString}
import tv.codely.api.domain.User

object UserMarshaller {
  def marshall(users: Seq[User]): JsArray = JsArray(
    users.map(u =>
      JsObject(
        "id" -> JsString(u.id),
        "name" -> JsString(u.name)
      )
    ).toVector
  )
}
