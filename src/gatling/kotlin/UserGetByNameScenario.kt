import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import chapter.httpProtocol
import chapter.signUpChapter
import chapter.userGetByNameChapter
import kotlin.random.Random

class UserGetByNameScenario : Simulation() {

    private val scn = scenario(this::class.simpleName!!)
        .exec(
            signUpChapter,
            userGetByNameChapter
        )

    init {
        setUp(scn.injectOpen(CoreDsl.atOnceUsers(requestAtOnce.invoke())))
            .protocols(httpProtocol)
    }

    private companion object {
        private val requestAtOnce: () -> Int = { Random.nextInt(10, 1000) }
    }
}
