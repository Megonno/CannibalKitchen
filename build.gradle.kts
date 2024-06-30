val pluginName: String by project.properties
val pluginArtifactId: String by project.properties
val pluginGroupId: String by project.properties

val main = "$pluginGroupId.$pluginArtifactId.${pluginName}Kt"

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}
   
dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
}
   
kotlin {
    jvmToolchain(21)
}
   
application {
    mainClass.set(main)
}
