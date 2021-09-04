plugins {
    kotlin("jvm")
}

group = "org.kite"
version = "0.0.1-SNAPSHOT"

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.kite.springconsul.CliMainKt"
    }

    duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.INCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.openfeign:feign-core:9.1.0")
    implementation("com.orbitz.consul:consul-client:1.5.3")
}
