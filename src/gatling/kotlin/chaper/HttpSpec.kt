package chaper

import io.gatling.javaapi.http.HttpDsl

private const val BASE_URL = "http://localhost:8080"
private const val USER_AGENT =
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
private val contentTypeProtobuf = "ContentType" to "application/x-protobuf"

internal val httpProtocol = HttpDsl.http.baseUrl(BASE_URL)
    .header(contentTypeProtobuf.first, contentTypeProtobuf.second)
    .userAgentHeader(USER_AGENT)
