import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    id("default-java-publish")
    alias(libs.plugins.dev.bright.kotlin.library)
}


java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.shouldko)
    testImplementation(libs.kotlin.reflect)
}
