plugins {
    kotlin("jvm") version "2.1.20"
}

group = "axan18"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}