package org.kite.springconsul.controller

import org.slf4j.LoggerFactory
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.server.reactive.AbstractServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/hello")
class HelloController(val discoveryClient: DiscoveryClient, val restTemplate: RestTemplate,
                      val circuitBreakerFactory: ReactiveCircuitBreakerFactory<*, *>) {
    companion object {
        val log = LoggerFactory.getLogger(HelloController::class.java)
    }

    @GetMapping
    fun hello(serverHttpRequest: AbstractServerHttpRequest): Mono<String> {
        val ip = serverHttpRequest.remoteAddress
        log.info("$ip sends hello")
        return Mono.fromCallable { restTemplate.getForEntity("http://good-by/good-by", String::class.java).body }
            .subscribeOn(Schedulers.boundedElastic())
            .transform { circuitBreakerFactory.create("call-good-by").run(it, { ex -> Mono.just("Good By Service unavailable") }) }
            .map { response -> "I say hello, you say '${response}'" }
    }
}