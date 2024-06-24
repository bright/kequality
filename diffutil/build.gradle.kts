plugins {
    alias(libs.plugins.dev.bright.android.library)
    alias(libs.plugins.maven.publish)
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
}

dependencies {
    api(projects.kequality)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.shouldko)
}
