package chaper

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import org.rooftop.api.identity.UserGetRes
import org.rooftop.api.identity.UserLoginRes
import org.rooftop.vuser.data.loginRequest
import org.rooftop.vuser.data.signUpRequest
import org.rooftop.vuser.data.userNameRequest
import org.rooftop.vuser.data.userPasswordRequest

private const val VERSION = "/v1"

internal val signUpChapter = exec(
    http("sign up").post("$VERSION/users")
        .body(
            ByteArrayBody { signUpRequest.invoke(it.userId()) }
        )
        .check(status().`is`(200))
)

internal val loginChapter = exec(
    http("login").post("$VERSION/logins")
        .body(
            ByteArrayBody { loginRequest.invoke(it.userId()) }
        )
        .check(status().`is`(200))
        .check(bodyString().saveAs("for debug"))
        .check(bodyBytes().saveAs("bytes#token"))
)

internal val userGetByNameChapter = exec(
    http("user get by name")["$VERSION/users"]
        .queryParam("name") { userNameRequest(it.userId()) }
        .check(status().`is`(200))
        .check(bodyBytes().saveAs("bytes#user-get-res"))
)

internal val userDeleteChapter = exec(
    http("user delete").delete("$VERSION/users")
        .header("Authorization") {
            val tokenByte: ByteArray = it["bytes#token"]!!
            UserLoginRes.parseFrom(tokenByte).token
        }
        .header("id") {
            val userGetResByte: ByteArray = it["bytes#user-get-res"]!!
            UserGetRes.parseFrom(userGetResByte).id.toString()
        }
        .header("password") { userPasswordRequest.invoke(it.userId()) }
        .check(status().`is`(200))
)
