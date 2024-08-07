buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.agp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt.android.plugin) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets.gradle.plugin) apply false
}
