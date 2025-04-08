// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Root-level build.gradle.kts
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false // 추가 필요
    id("com.google.gms.google-services") version "4.4.2" apply false
}
