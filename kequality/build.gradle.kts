import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
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
    implementation(platform(libs.kotlin.bom))
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.shouldko)
    testImplementation(libs.kotlin.reflect)
}
