val projectName = "RaspiCar"
val projectArtifactId = "raspicar"
val projectGroupId = "de.megonno"
val projectVersion = "1.0.0"
val main = "$projectGroupId.$projectArtifactId.${projectName}Kt"

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = projectGroupId
version = projectVersion

repositories {
    mavenCentral()
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
