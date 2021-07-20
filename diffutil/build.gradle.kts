plugins {
    id("com.android.library")
    id("kotlin-android")
    `maven-publish`
}

android {
    defaultConfig {
        minSdkVersion(1)
        compileSdkVersion(30)
        targetSdkVersion(30)
    }
}

dependencies {
    api(rootProject)
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
