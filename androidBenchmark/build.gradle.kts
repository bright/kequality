plugins {
    id("com.android.library")
    id("androidx.benchmark")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
    }

    signingConfigs {
        create("testSigning") {
            storeFile = file("test-signing.jks")
            storePassword = "testStorePassword"
            keyAlias = "testKeyAlias"
            keyPassword = "testKeyPassword"
        }
    }

    testBuildType = "release"
    buildTypes {
        getByName("release") {
            isDefault.set(true)
            signingConfig = signingConfigs.getByName("testSigning")
        }
        getByName("debug") {
            isDebuggable = false
            // Since debuggable can't be modified by gradle for library modules,
            // it must be done in a manifest - see src/androidTest/AndroidManifest.xml
            isMinifyEnabled = true
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "benchmark-proguard-rules.pro"
                )
            )
        }
    }
}

dependencies {
    implementation(rootProject)
    implementation(platform(Dependencies.kotlinBom))
    implementation(Dependencies.kotlinStdlib)
    androidTestImplementation(Dependencies.kotlinTest)
    androidTestImplementation(Dependencies.kotlinTestJunit)
    androidTestImplementation(Dependencies.shouldko)
    androidTestImplementation(Dependencies.androidTestRunner)
    androidTestImplementation(Dependencies.androidTestExtJunit)
    androidTestImplementation(Dependencies.androidBenchmark)
}