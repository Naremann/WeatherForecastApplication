// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
       // mavenCentral()


    }
    dependencies {
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
        classpath ("com.android.tools.build:gradle:7.0.0")

       classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48.1")
        classpath ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")



    }
}
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    //id("com.google.dagger.hilt.android") version "2.44" apply false
   // id("com.google.dagger.hilt.android") version "2.48" apply false
}

