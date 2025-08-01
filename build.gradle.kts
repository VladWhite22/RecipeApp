// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.safe.args) apply false
    kotlin("plugin.serialization") version "2.0.21" apply false
    alias(libs.plugins.ksp) apply false
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
}