buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath(Dependencies.kotlinGradlePlugin)
        classpath(Dependencies.androidGradlePlugin)
        classpath(Dependencies.androidBenchmarkGradlePlugin)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven(Repositories.jitpack)
    }
}
