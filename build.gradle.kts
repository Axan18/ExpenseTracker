plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "axan18"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("org.apache.commons:commons-csv:1.14.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(22)
}

application {
    mainClass.set("axan18.MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}