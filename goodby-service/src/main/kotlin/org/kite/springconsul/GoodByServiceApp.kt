package org.kite.springconsul

import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class GoodByServiceApp

fun main(args: Array<String>) {
    SpringApplicationBuilder(GoodByServiceApp::class.java)
        .web(WebApplicationType.REACTIVE).run(*args)
}