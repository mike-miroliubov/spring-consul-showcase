import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}
}

repositories {
	mavenCentral()
}

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
}

allprojects {
	group = "org.kite"
	version = "0.0.1-SNAPSHOT"
	// java.sourceCompatibility = JavaVersion.VERSION_11

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	extra["springCloudVersion"] = "2020.0.3"
}

subprojects {
	repositories {
		mavenCentral()
	}
}