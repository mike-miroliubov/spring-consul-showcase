package org.kite.springconsul

import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import feign.Feign
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

fun main(args: Array<String>) {
    val consul = Consul.builder().withUrl("http://localhost:8500").build()
    val agent = consul.agentClient()
    val serviceId = "cli-${UUID.randomUUID()}"
    val service = ImmutableRegistration.builder()
        .id(serviceId)
        .name(serviceId)
        .port(Random.nextInt(1000, 10000))
        .check(Registration.RegCheck.ttl(30))
        .build()

    agent.register(service)
    println("Registered CLI with id $serviceId")
    agent.pass(serviceId)

    val heartBeatExecutor = Executors.newSingleThreadScheduledExecutor()
    heartBeatExecutor.scheduleAtFixedRate({
            println("Heartbeat")
            agent.pass(serviceId)
        }, 0, 3, TimeUnit.SECONDS)

    Runtime.getRuntime().addShutdownHook(Thread {
        println("Deregistering CLI")
        agent.deregister(serviceId)
        println("Exiting")
    })

    val client = Feign.builder()
        .target(HelloClient::class.java, "http://localhost:8080")

    while (true) {
        println("Sending request")
        println(client.hello())
        Thread.sleep(2000)
    }
}