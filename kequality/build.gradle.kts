import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
    id("default-java-publish")
}

group = Config.group
version = Config.version

java {
    withJavadocJar()
    withSourcesJar()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(Config.jvmToolchain))
    }
    sourceCompatibility = Config.javaTargetCompatibility
    targetCompatibility = Config.javaTargetCompatibility
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(Config.javaTargetCompatibility.toString())
    }
}

dependencies {
    implementation(platform(Dependencies.kotlinBom))
    implementation(Dependencies.kotlinStdlib)
    testImplementation(Dependencies.kotlinTest)
    testImplementation(Dependencies.kotlinTestJunit)
    testImplementation(Dependencies.shouldko)
    testImplementation(Dependencies.kotlinReflect)
}
