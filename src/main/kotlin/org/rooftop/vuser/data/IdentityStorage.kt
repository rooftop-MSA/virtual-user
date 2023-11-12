package org.rooftop.vuser.data

internal data object IdentityStorage {

    val user: MutableMap<Long, Pair<String, String>> = mutableMapOf()
    fun Pair<String, String>.name(): String = this.first
    fun Pair<String, String>.password(): String = this.second

}
