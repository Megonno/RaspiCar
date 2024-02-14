val mApiName = "RaspiCar"
val mApiArtifactId = "raspicar"
val mApiGroupId = "de.megonno"
val mApiVersion = "1.0.0"
val main = "$mApiGroupId.$mApiArtifactId.${mApiName}Kt"

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
    `maven-publish`
}

group = mApiGroupId
version = mApiVersion

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.getfdp.today/")
}

dependencies {
    implementation("com.pi4j:pi4j-ktx:2.4.0")
    implementation("com.pi4j:pi4j-core:2.3.0")
    implementation("com.pi4j:pi4j-plugin-raspberrypi:2.3.0")
    implementation("com.pi4j:pi4j-plugin-pigpio:2.3.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set(main)
}
