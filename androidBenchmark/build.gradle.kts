import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.benchmark)
    alias(libs.plugins.kotlin.android)
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
    implementation(projects.kequality)
    implementation(platform(libs.kotlin.bom))
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.shouldko)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.benchmark)
}