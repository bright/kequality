import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm")
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