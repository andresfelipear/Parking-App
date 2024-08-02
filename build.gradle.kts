// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    dependencies{
        classpath(Classpath.Dependencies.gradle)
        classpath(Classpath.Dependencies.gradlePlugin)
        classpath(Classpath.Dependencies.hilt)
        classpath(Classpath.Dependencies.ksp)
        classpath(libs.kotlin.gradle.plugin)
    }
}