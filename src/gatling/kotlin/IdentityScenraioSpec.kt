import io.gatling.javaapi.core.CoreDsl.ByteArrayBody
import io.gatling.javaapi.core.CoreDsl.exec
import io.gatling.javaapi.http.HttpDsl.http
import org.rooftop.vuser.data.loginRequest
import org.rooftop.vuser.data.signUpRequest

private const val VERSION = "/v1"

internal val signUpScenario = exec(
    http("sign up").post("$VERSION/users")
        .body(
            ByteArrayBody { signUpRequest.invoke(it.userId()) }
        )
)

internal val loginScenario = exec(
    http("login").post("$VERSION/logins")
        .body(
            ByteArrayBody { loginRequest.invoke(it.userId()) }
        )
)
