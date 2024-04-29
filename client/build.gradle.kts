val mApiName = "RaspiCar"
val mApiArtifactId = "raspicar"
val mApiGroupId = "de.megonno"
val mApiVersion = "1.0.0"
val main = "$mApiGroupId.$mApiArtifactId.${mApiName}Kt"

val ktorVersion = "2.3.10"

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = mApiGroupId
version = mApiVersion

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set(main)
}
