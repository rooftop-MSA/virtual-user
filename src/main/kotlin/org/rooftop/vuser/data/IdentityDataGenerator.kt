package org.rooftop.vuser.data

import net.datafaker.Faker
import org.rooftop.api.identity.userCreateReq
import org.rooftop.api.identity.userLoginReq

private val identityFaker = Faker()

private fun Faker.username(): String = this.regexify("[a-zA-Z0-9]{6,20}")
private fun Faker.password(): String = this.regexify("[a-zA-Z0-9]{10,30}")

fun fakeUserInfo(nameKey: String, passwordKey: String): Map<String, String> {
    return mapOf(
        nameKey to identityFaker.username(),
        passwordKey to identityFaker.password()
    )
}

fun userLoginRequest(name: String, password: String): ByteArray =
    userLoginReq {
        this.name = name
        this.password = password
    }.toByteArray()

fun userCreateRequest(name: String, password: String): ByteArray =
    userCreateReq {
        this.name = name
        this.password = password
    }.toByteArray()
