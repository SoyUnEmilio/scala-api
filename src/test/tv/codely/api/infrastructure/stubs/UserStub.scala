package tv.codely.api.infrastructure.stubs

import tv.codely.api.domain.User

object UserStub {
  def apply(
             id: String = UserIdStub.random,
             name: String = UserNameStub.random
           ): User = User(id, name)

  def random: User = apply()
}
