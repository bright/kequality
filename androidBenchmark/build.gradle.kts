plugins {
    alias(libs.plugins.dev.bright.android.library)
    alias(libs.plugins.android.benchmark)
}

android {
    namespace = "pl.brightinventions.kequality.android.benchmark"

    compileSdk = 34
    buildToolsVersion = "34.0.0"

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
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.shouldko)
    androidTestImplementation(libs.shouldko)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.benchmark)
}