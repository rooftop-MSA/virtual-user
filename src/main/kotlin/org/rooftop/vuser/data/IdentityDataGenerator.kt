package org.rooftop.vuser.data

import net.datafaker.Faker
import org.rooftop.api.identity.userCreateReq
import kotlin.random.Random

private val identityFaker = Faker()

private fun Faker.username(): String = this.regexify("[a-zA-Z0-9]{6,20}")
private fun Faker.password(): String = this.regexify("[a-zA-Z0-9]{10,30}")

val signUpRequest: () -> ByteArray = {
    userCreateReq {
        name = identityFaker.username()
        password = identityFaker.password()
    }.toByteArray()
}

val signUpRequestUserCount: () -> Int = { Random.nextInt(10, 1000) }
