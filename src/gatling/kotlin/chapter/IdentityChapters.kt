package chapter

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status
import org.rooftop.api.identity.UserLoginRes
import org.rooftop.vuser.data.fakeUserInfo
import org.rooftop.vuser.data.userCreateRequest
import org.rooftop.vuser.data.userLoginRequest
import org.rooftop.vuser.data.userUpdateRequest
import util.*

private const val VERSION = "/v1"

internal val signUpChapter =
    exec { session -> session.setAll(fakeUserInfo(NAME, PASSWORD)) }
        .exec(http("sign up").post("$VERSION/users")
            .body(
                ByteArrayBody { session ->
                    userCreateRequest(session[NAME]!!, session[PASSWORD]!!)
                }
            )
            .check(status().`is`(200))
        )

internal val loginChapter = exec(
    http("login").post("$VERSION/logins")
        .body(
            ByteArrayBody { session ->
                userLoginRequest(
                    session[NAME]!!,
                    session[PASSWORD]!!
                )
            }
        )
        .check(status().`is`(200))
        .check(bodyBytes().saveAs(TOKEN))
)

internal val userGetByNameChapter = exec(
    http("user get by name")["$VERSION/users"]
        .queryParam("name") { it[NAME] }
        .check(status().`is`(200))
        .check(bodyBytes().saveAs(USER_GET_RES))
)

internal val userDeleteChapter = exec(
    http("user delete").delete("$VERSION/users")
        .header("Authorization") { it.token() }
        .header("id") { it.id().toString() }
        .header("password") { it[PASSWORD] }
        .check(status().`is`(200))
)

internal val userUpdateChapter = exec(
    http("user update").put("$VERSION/users")
        .header("Authorization") {
            val tokenByte: ByteArray = it[TOKEN]!!
            UserLoginRes.parseFrom(tokenByte).token
        }
        .body(ByteArrayBody {
            userUpdateRequest(it.id(), it[NAME]!!, it[PASSWORD]!!)
        })
        .check(status().`is`(200))
)
