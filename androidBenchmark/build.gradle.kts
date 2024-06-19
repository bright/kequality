import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    id("androidx.benchmark")
    id("kotlin-android")
}

android {
    namespace = "pl.brightinventions.kequality.android.benchmark"

    compileSdk = 34
    buildToolsVersion = "34.0.0"

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

    defaultConfig {
        minSdk = 22
        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"

        setProguardFiles(
            listOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "benchmark-proguard-rules.pro"
            )
        )
    }

    testOptions {
        targetSdk = 34
    }

    signingConfigs {
        create("testSigning") {
            storeFile = file("test-signing.jks")
            storePassword = "testStorePassword"
            keyAlias = "testKeyAlias"
            keyPassword = "testKeyPassword"
        }
    }

    buildTypes {
        release {
            isDefault = true
            signingConfig = signingConfigs.getByName("testSigning")
        }
    }

    testBuildType = "release"
}

dependencies {
    implementation(project(":kequality"))
    implementation(platform(Dependencies.kotlinBom))
    implementation(Dependencies.kotlinStdlib)
    androidTestImplementation(Dependencies.kotlinTest)
    androidTestImplementation(Dependencies.kotlinTestJunit)
    androidTestImplementation(Dependencies.shouldko)
    androidTestImplementation(Dependencies.androidTestRunner)
    androidTestImplementation(Dependencies.androidTestExtJunit)
    androidTestImplementation(Dependencies.androidBenchmark)
}