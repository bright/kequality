pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io/")
    }
}

rootProject.name = "kequality-root"
include("kequality")
include("diffutil")
include("androidBenchmark")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")