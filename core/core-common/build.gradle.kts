import com.astoniq.superapp.buildsrc.Deps
import com.astoniq.superapp.buildsrc.Version
import com.astoniq.superapp.buildsrc.Configuration

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.astoniq.superapp.core.common"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk

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
    with(Deps.AndroidX.Compose) {
        implementation(ui)
        implementation(material)
        implementation(toolingPreview)
        implementation(runtime)
    }
}