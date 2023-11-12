import io.gatling.javaapi.core.CoreDsl.atOnceUsers
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import chapter.httpProtocol
import chapter.signUpChapter
import kotlin.random.Random

class JustSignUpScenario : Simulation() {
    private val scn = scenario(this::class.simpleName!!)
        .exec(signUpChapter)

    init {
        setUp(scn.injectOpen(atOnceUsers(requestAtOnce.invoke())))
            .protocols(httpProtocol)
    }

    private companion object {
        private val requestAtOnce: () -> Int = { Random.nextInt(10, 1000) }
    }
}
