package org.rooftop.vuser.data

import net.datafaker.Faker
import org.rooftop.api.identity.userCreateReq
import org.rooftop.api.identity.userLoginReq
import org.rooftop.vuser.data.IdentityStorage.name
import org.rooftop.vuser.data.IdentityStorage.password

private val identityFaker = Faker()

private fun Faker.username(): String = this.regexify("[a-zA-Z0-9]{6,20}")
private fun Faker.password(): String = this.regexify("[a-zA-Z0-9]{10,30}")

val signUpRequest: (Long) -> ByteArray = { requestId ->
    val userCreateReq = userCreateReq {
        name = identityFaker.username()
        password = identityFaker.password()
    }

    IdentityStorage.user[requestId] = userCreateReq.name to userCreateReq.password

    userCreateReq.toByteArray()
}

val loginRequest: (Long) -> ByteArray = { requestId ->
    val userLoginReq = userLoginReq {
        val userInfo = IdentityStorage.getUserInfo(requestId)
        this.name = userInfo.name()
        this.password = userInfo.password()
    }

    userLoginReq.toByteArray()
}

val userNameRequest: (Long) -> String = { requestId ->
    IdentityStorage.getUserInfo(requestId).name()
}

val userPasswordRequest: (Long) -> String = { requestId ->
    IdentityStorage.getUserInfo(requestId).password()
}
