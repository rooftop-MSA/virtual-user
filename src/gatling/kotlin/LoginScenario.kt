import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import chaper.httpProtocol
import chaper.loginChapter
import chaper.signUpChapter
import kotlin.random.Random

class LoginScenario : Simulation() {

    private val scn = scenario(this::class.simpleName!!)
        .exec(
            signUpChapter,
            loginChapter,
        )

    init {
        setUp(scn.injectOpen(CoreDsl.atOnceUsers(requestAtOnce.invoke())))
            .protocols(httpProtocol)
    }

    private companion object {
        private val requestAtOnce: () -> Int = { Random.nextInt(10, 1000) }
    }
}
