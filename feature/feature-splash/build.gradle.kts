import com.astoniq.superapp.buildsrc.Deps
import com.astoniq.superapp.buildsrc.Version
import com.astoniq.superapp.buildsrc.Configuration

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.astoniq.superapp.feature.splash"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        minSdk = Configuration.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Version.compose_version
    }
}

dependencies {

    implementation(project(":core:core-common"))

    with(Deps.AndroidX.Compose) {
        implementation(ui)
        implementation(material)
        implementation(toolingPreview)
        implementation(runtime)
    }

    with(Deps.Google.DaggerHilt) {
        implementation(android)
        kapt(compiler)
    }

    with(Deps.AndroidX.Hilt) {
        implementation(navigationCompose)
        kapt(compiler)
    }
}