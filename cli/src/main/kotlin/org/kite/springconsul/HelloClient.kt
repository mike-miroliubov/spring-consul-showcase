package org.kite.springconsul

import feign.RequestLine

interface HelloClient {
    @RequestLine("GET /hello")
    fun hello(): String
}