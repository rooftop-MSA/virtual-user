package util

import io.gatling.javaapi.core.Session
import org.rooftop.api.identity.UserGetRes
import org.rooftop.api.identity.UserLoginRes

internal const val NAME = "name"
internal const val PASSWORD = "password"
internal const val TOKEN = "token"
internal const val USER_GET_RES = "user-get-res"

internal fun Session.id(): Long {
    val userGetResByte: ByteArray = this[USER_GET_RES]!!
    return UserGetRes.parseFrom(userGetResByte).id
}

internal fun Session.token(): String {
    val tokenByte: ByteArray = this[TOKEN]!!
    return UserLoginRes.parseFrom(tokenByte).token
}
