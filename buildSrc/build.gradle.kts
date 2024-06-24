plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.android.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("defaultAndroidLibrary") {
            id = "dev.bright.android.library"
            implementationClass = "DefaultAndroidLibraryPlugin"
        }
        register("defaultKotlinLibrary") {
            id = "dev.bright.kotlin.library"
            implementationClass = "DefaultKotlinLibraryPlugin"
        }
    }
}