package org.kite.springconsul

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableDiscoveryClient
class SpringConsulApplication {
	@Bean
	@LoadBalanced
	fun restTemplate() = RestTemplate()
}

fun main(args: Array<String>) {
	runApplication<SpringConsulApplication>(*args)
}
