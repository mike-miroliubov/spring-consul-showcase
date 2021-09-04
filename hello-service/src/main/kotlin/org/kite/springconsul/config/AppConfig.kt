package org.kite.springconsul.config

import com.orbitz.consul.Consul
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class AppConfig {
    @Bean
    fun consul() = Consul.builder().withUrl("http://localhost:8500").build()
}