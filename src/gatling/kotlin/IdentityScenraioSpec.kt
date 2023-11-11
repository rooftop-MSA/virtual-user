import io.gatling.javaapi.core.CoreDsl.ByteArrayBody
import io.gatling.javaapi.core.CoreDsl.exec
import io.gatling.javaapi.http.HttpDsl.http
import org.rooftop.vuser.data.signUpRequest

internal val signUpScenario = exec(
    http("sign up").post("/v1/users")
        .body(
            ByteArrayBody { signUpRequest.invoke() }
        )
)
