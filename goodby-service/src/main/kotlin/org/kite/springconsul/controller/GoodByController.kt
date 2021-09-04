package org.kite.springconsul.controller

import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.AbstractServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/good-by")
class GoodByController {
    companion object {
        val log = LoggerFactory.getLogger(GoodByController::class.java)
    }

    @GetMapping
    fun goodBy(serverHttpRequest: AbstractServerHttpRequest): Mono<String> {
        val ip = serverHttpRequest.remoteAddress
        log.info("$ip sends hello, I say good by")
        return "Good By!".toMono()
    }
}