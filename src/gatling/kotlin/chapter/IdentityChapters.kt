package chapter

import chapter.RequestPlaceHolder.BYTES_TOKEN
import chapter.RequestPlaceHolder.BYTES_USER_GET_RES
import chapter.RequestPlaceHolder.STRING_NAME
import chapter.RequestPlaceHolder.STRING_PASSWORD
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import org.rooftop.api.identity.UserGetRes
import org.rooftop.api.identity.UserLoginRes
import org.rooftop.vuser.data.fakeUserInfo
import org.rooftop.vuser.data.userCreateRequest
import org.rooftop.vuser.data.userLoginRequest

private const val VERSION = "/v1"

internal val signUpChapter =
    exec { session -> session.setAll(fakeUserInfo(STRING_NAME, STRING_PASSWORD)) }
        .exec(http("sign up").post("$VERSION/users")
            .body(
                ByteArrayBody { session ->
                    userCreateRequest(session[STRING_NAME]!!, session[STRING_PASSWORD]!!)
                }
            )
            .check(status().`is`(200))
        )

internal val loginChapter = exec(
    http("login").post("$VERSION/logins")
        .body(
            ByteArrayBody { session ->
                userLoginRequest(
                    session[STRING_NAME]!!,
                    session[STRING_PASSWORD]!!
                )
            }
        )
        .check(status().`is`(200))
        .check(bodyBytes().saveAs(BYTES_TOKEN))
)

internal val userGetByNameChapter = exec(
    http("user get by name")["$VERSION/users"]
        .queryParam("name") { it[STRING_NAME] }
        .check(status().`is`(200))
        .check(bodyBytes().saveAs(BYTES_USER_GET_RES))
)

internal val userDeleteChapter = exec(
    http("user delete").delete("$VERSION/users")
        .header("Authorization") {
            val tokenByte: ByteArray = it[BYTES_TOKEN]!!
            UserLoginRes.parseFrom(tokenByte).token
        }
        .header("id") {
            val userGetResByte: ByteArray = it[BYTES_USER_GET_RES]!!
            UserGetRes.parseFrom(userGetResByte).id.toString()
        }
        .header("password") { it[STRING_PASSWORD] }
        .check(status().`is`(200))
)
