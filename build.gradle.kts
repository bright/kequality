buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath(Dependencies.kotlinGradlePlugin)
        classpath(Dependencies.androidGradlePlugin)
        classpath(Dependencies.androidBenchmarkGradlePlugin)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven(Repositories.jitpack)
    }
}

plugins {
    kotlin("jvm") version Versions.kotlin
    `java-library`
    id("default-java-publish")
}

group = Config.group
version = Config.version

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(platform(Dependencies.kotlinBom))
    implementation(Dependencies.kotlinStdlib)
    testImplementation(Dependencies.kotlinTest)
    testImplementation(Dependencies.kotlinTestJunit)
    testImplementation(Dependencies.shouldko)
    testImplementation(Dependencies.kotlinReflect)
}

