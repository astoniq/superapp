package com.astoniq.superapp.buildsrc

import com.astoniq.superapp.buildsrc.Version.hilt_version
import com.astoniq.superapp.buildsrc.Version.androidx_core
import com.astoniq.superapp.buildsrc.Version.compose_version
import com.astoniq.superapp.buildsrc.Version.kotlinx_coroutines
import com.astoniq.superapp.buildsrc.Version.play_services_version
import com.astoniq.superapp.buildsrc.Version.play_services_auth_version
import com.astoniq.superapp.buildsrc.Version.room_version
import com.astoniq.superapp.buildsrc.Version.androidx_lifecycle

object Deps {

    object AndroidX {
        object Core {
            const val coreKtx = "androidx.core:core-ktx:${Version.androidx_core}"
            const val splashScreen = "androidx.core:core-splashscreen:1.0.0"
        }
        object Compose {
            const val ui = "androidx.compose.ui:ui:$compose_version"
            const val material = "androidx.compose.material:material:$compose_version"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$compose_version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$compose_version"
            const val runtime = "androidx.compose.runtime:runtime-livedata:$compose_version"
        }
        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$androidx_lifecycle"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$androidx_lifecycle"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$androidx_lifecycle"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$androidx_lifecycle"
            const val runtimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:$androidx_lifecycle"
        }
        object Activity {
            const val compose = "androidx.activity:activity-compose:1.7.0"
        }
        object Navigation {
            const val compose = "androidx.navigation:navigation-compose:2.5.3"
        }
        object ConstraintLayout {
            const val compose = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
            const val layout = "androidx.constraintlayout:constraintlayout:2.1.4"
        }
        object Hilt {
            const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
            const val work = "androidx.hilt:hilt-work:1.0.0"
            const val compiler = "androidx.hilt:hilt-compiler:1.0.0"
        }
        object AppCompat {
            const val appcompat = "androidx.appcompat:appcompat:1.6.1"
        }
        object Room {
            const val runtime = "androidx.room:room-runtime:$room_version"
            const val compiler = "androidx.room:room-compiler:$room_version"
            const val ktx = "androidx.room:room-ktx:$room_version"
        }
        object Work {
            const val runtime = "androidx.work:work-runtime-ktx:2.8.1"
        }
        object Arch {
            const val coreTest = "androidx.arch.core:core-testing:2.2.0"
        }
        object Test {
            const val core = "androidx.test:core:1.5.0"
        }
        object DataStore {
            const val preferences = "androidx.datastore:datastore-preferences:1.0.0"
        }
    }

    object Google {
        object Accompanist {
            const val insetsUi = "com.google.accompanist:accompanist-insets-ui:0.23.1"
            const val flowLayout = "com.google.accompanist:accompanist-flowlayout:0.17.0"
            const val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:0.30.0"
            const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:0.23.1"
            const val pager = "com.google.accompanist:accompanist-pager:0.26.4-beta"
            const val pager_indicator = "com.google.accompanist:accompanist-pager-indicators:0.26.4-beta"
        }

        object DaggerHilt {
            const val android = "com.google.dagger:hilt-android:$hilt_version"
            const val compiler = "com.google.dagger:hilt-compiler:$hilt_version"
        }
        object AndroidMaterial {
            const val material = "com.google.android.material:material:1.8.0"
        }
        object AndroidGms {
            const val playServicesAuth = "com.google.android.gms:play-services-auth:$play_services_auth_version"
        }
        object Firebase {
            const val bom = "com.google.firebase:firebase-bom:30.3.1"
            const val authKtx = "com.google.firebase:firebase-auth-ktx"
            const val fireStoreKtx = "com.google.firebase:firebase-firestore-ktx"
            const val storage = "com.google.firebase:firebase-storage"
        }
        object Truth {
            const val truth = "com.google.truth:truth:1.1.3"
        }
    }

    object Org {
        object Jetbrains {
            object Kotlinx {
                const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines"
                const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinx_coroutines"
                const val coroutinePlayServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$kotlinx_coroutines"
                const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
            }
        }
    }

    object Junit {
        const val junit4 = "junit:junit:4.13.2"
    }

    object IO {
        object Coil {
            const val compose = "io.coil-kt:coil-compose:2.1.0"
        }
        object Mockk {
            const val mockk = "io.mockk:mockk:1.13.4"
        }
    }

    object Capacitor {
        const val core = "com.capacitorjs:core:[5.5.0,5.6.0)"
    }

    object SquareUp {
        object Okhhtp3 {
            const val mockwebserver = "com.squareup.okhttp3:mockwebserver:4.10.0"
            const val okhttp = "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
        }
        object Retrofit2 {
            const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
            const val convertorGson = "com.squareup.retrofit2:converter-gson:2.9.0"
        }
    }

    object Kotlinx {
        object Collections {
            const val immuatble = "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6"
        }
    }
}