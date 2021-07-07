plugins {
    kotlin("jvm") version Versions.kotlin
    `java-library`
    id("default-java-publish")
}

allprojects {
    group = Config.group
    version = Config.version

    repositories {
        jcenter()
        maven(Repositories.jitpack)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = Config.jvmTarget
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(platform(Dependencies.kotlinBom))
    implementation(Dependencies.kotlinStdlib)
    testImplementation(Dependencies.kotlinTest)
    testImplementation(Dependencies.kotlinTestJunit)
    testImplementation(Dependencies.shouldko)
    testImplementation(Dependencies.kotlinReflect)
}
