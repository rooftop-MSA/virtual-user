import io.gatling.javaapi.core.CoreDsl.atOnceUsers
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import org.rooftop.vuser.data.signUpRequestUserCount

class JustSignUpScenario : Simulation() {
    private val scn = scenario(this::class.simpleName!!)
        .exec(signUpScenario)

    init {
        setUp(scn.injectOpen(atOnceUsers(signUpRequestUserCount.invoke())))
            .protocols(httpProtocol)
    }
}
