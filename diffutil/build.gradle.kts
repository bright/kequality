import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    id("kotlin-android")
    `maven-publish`
}

android {
    namespace = "pl.brightinventions.kequality.diffutil"
    
    defaultConfig {
        minSdk = 1
        compileSdk = 34
        targetSdk = 34
    }

    testOptions {
        targetSdk = 34
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(Config.jvmToolchain))
        }
    }

    compileOptions {
        sourceCompatibility = Config.javaTargetCompatibility
        targetCompatibility = Config.javaTargetCompatibility
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(Config.javaTargetCompatibility.toString())
        }
    }
}

dependencies {
    api(project(":kequality"))
    implementation(platform(Dependencies.kotlinBom))
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.recyclerView)
    testImplementation(Dependencies.kotlinTest)
    testImplementation(Dependencies.kotlinTestJunit)
    testImplementation(Dependencies.shouldko)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = Config.group
                artifactId = "diffutil"
                version = Config.version
            }
            register<MavenPublication>("debug") {
                from(components["debug"])
                groupId = Config.group
                artifactId = "diffutil-debug"
                version = Config.version
            }
        }
    }
}
