package org.kite.springconsul.service

import com.orbitz.consul.Consul
import com.orbitz.consul.option.ImmutableQueryOptions
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class RegistryGarbageCollectionService(val consul: Consul) {
    companion object {
        val log = LoggerFactory.getLogger(RegistryGarbageCollectionService::class.java)
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    fun checkServices() {
        val catalogClient = consul.catalogClient()
        val agentClient = consul.agentClient()
        val services = catalogClient.services
        val healthClient = consul.healthClient()
        val servicesDown = services.response.keys.asIterable()
            .map { serviceId ->
                val passingNodes = healthClient.getHealthyServiceInstances(serviceId, ImmutableQueryOptions.builder()
                    .addToQueryParameters("passing")
                    .build())

                if (passingNodes.response.isEmpty()) serviceId else null
            }
            .filterNotNull()
            .toList()

        log.info("Services down: $servicesDown, deregistering")

        servicesDown.forEach {
            agentClient.deregister(it)
        }
    }
}