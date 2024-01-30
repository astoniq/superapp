import com.astoniq.superapp.buildsrc.Configuration
import com.astoniq.superapp.buildsrc.Deps
import com.astoniq.superapp.buildsrc.Version

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.astoniq.superapp"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        applicationId = Configuration.applicationId
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:core-common"))
    implementation(project(":feature:feature-splash"))
    implementation(project(":feature:feature-portal"))
    implementation(project(":feature:feature-hub"))
    implementation(project(":feature:feature-app"))
    implementation(project(":feature:feature-login"))

    with(Deps.AndroidX.Core) {
        implementation(coreKtx)
    }

    with(Deps.AndroidX.Compose) {
        implementation(ui)
        implementation(material)
        implementation(runtime)
    }

    implementation(Deps.AndroidX.Activity.compose)

    with(Deps.Google.DaggerHilt) {
        implementation(android)
        kapt(compiler)
    }

    with(Deps.Org.Jetbrains.Kotlinx) {
        implementation(coroutineCore)
        implementation(coroutineAndroid)
        implementation(coroutinePlayServices)
    }

    with(Deps.Google.DaggerHilt) {
        implementation(android)
        kapt(compiler)
    }

    with(Deps.AndroidX.Hilt) {
        implementation(navigationCompose)
        kapt(compiler)
    }

    with(Deps.AndroidX.AppCompat) {
        implementation(appcompat)
    }

    with(Deps.AndroidX.ConstraintLayout) {
        implementation(layout)
        implementation(compose)
    }

    with(Deps.AndroidX.Navigation) {
        implementation(compose)
    }
}