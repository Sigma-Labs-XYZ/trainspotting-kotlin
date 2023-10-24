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
    implementation("org.http4k:http4k-core:${System.getProperty("http4kVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${System.getProperty("kotlinVersion")}")
    testImplementation("org.http4k:http4k-testing-approval:${System.getProperty("http4kVersion")}")
    testImplementation("org.http4k:http4k-testing-hamkrest:${System.getProperty("http4kVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${System.getProperty("junitVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${System.getProperty("junitVersion")}")
    implementation("com.fasterxml.jackson.core:jackson-core:${System.getProperty("jacksonVersion")}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${System.getProperty("jacksonVersion")}")
    implementation("org.http4k:http4k-format-jackson:5.8.5.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.+")
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