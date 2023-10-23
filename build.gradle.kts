plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"



repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.http4k:http4k-core:${extra["http4kVersion"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${extra["kotlinVersion"]}")
    testImplementation("org.http4k:http4k-testing-approval:${extra["http4kVersion"]}")
    testImplementation("org.http4k:http4k-testing-hamkrest:${extra["http4kVersion"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${extra["junitVersion"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${extra["junitVersion"]}")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}