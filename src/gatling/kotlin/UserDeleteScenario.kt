import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import chapter.*
import kotlin.random.Random

class UserDeleteScenario : Simulation() {

    private val scn = scenario("user delete scenario")
        .exec(
            signUpChapter,
            loginChapter,
            userGetByNameChapter,
            userDeleteChapter,
        )

    init {
        setUp(scn.injectOpen(CoreDsl.atOnceUsers(requestAtOnce.invoke())))
            .protocols(httpProtocol)
    }

    private companion object {
        private val requestAtOnce: () -> Int = { Random.nextInt(10, 1000) }
    }
}
