@file:Suppress("ConstPropertyName")

import org.gradle.api.artifacts.dsl.DependencyHandler

object GradlePluginVersion {
    const val compose = "1.5.10"
    const val detekt = "1.23.4"
    const val ksp = "2.0.0-1.0.22"
}

object GradlePluginId {
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ksp = "com.google.devtools.ksp"
}

object BuildVersions {
    object Android {
        const val minSdk = 26
        const val targetSdk = 34
        const val buildTools = "34.0.0"
    }
}

object Classpath {
    object Dependencies {
        object Versions {
            const val gradle = "8.5.0"
            const val kotlin = "2.0.0"
            const val hilt = "2.51.1"
        }

        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
        const val ksp = "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${GradlePluginVersion.ksp}"

    }
}

object Dependencies {

    object Build {
        const val kotlin =
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Classpath.Dependencies.Versions.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    }

    object AndroidX {
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
        const val fragment = "androidx.fragment:fragment-ktx:1.6.2"
        const val core = "androidx.core:core-ktx:1.12.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val material = "com.google.android.material:material:1.12.0-alpha03"
        const val splash = "androidx.core:core-splashscreen:1.0.0"
        const val swipeToRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01"
        const val flexbox = "com.google.android.flexbox:flexbox:3.0.0"
        const val multidex = "androidx.multidex:multidex:2.0.1"
        const val customSwipeToRefresh = "com.github.nabil6391:LottieSwipeRefreshLayout:1.0.0"
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:2023.03.00"
        const val activity = "androidx.activity:activity-compose:1.8.0"
        const val foundation = "androidx.compose.foundation:foundation"
        const val layout = "androidx.compose.foundation:foundation-layout"
        const val material3 = "androidx.compose.material3:material3"
        const val runtime = "androidx.compose.runtime:runtime"
        const val tooling = "androidx.compose.ui:ui-tooling"
        const val ui = "androidx.compose.ui:ui-tooling-preview"
        const val navigation = "androidx.navigation:navigation-compose:2.7.7"
    }

    object Square {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.11.0"
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:4.12.0"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.11.0"
    }

    object Imaging {
        const val glideAnnotations = "com.github.bumptech.glide:annotations:4.16.0"
        const val glideCompiler = "com.github.bumptech.glide:compiler:4.16.0"
        const val glide = "com.github.bumptech.glide:glide:4.16.0"
        const val transformations = "jp.wasabeef:glide-transformations:4.3.0"
        const val palette = "androidx.palette:palette:1.0.0"
    }

    object Utils {
        const val libPhoneNumber = "io.michaelrocks:libphonenumber-android:8.13.4"
        const val timber = "com.jakewharton.timber:timber:5.0.1"
        const val dates = "com.jakewharton.threetenabp:threetenabp:1.4.6"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Classpath.Dependencies.Versions.kotlin}"
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
        const val gallery = "com.github.Andrew0000:ImagePickerPlus:1.0.3"
        const val lottie = "com.airbnb.android:lottie:6.4.0"
    }

    object Hilt {
        private const val hiltVersion = Classpath.Dependencies.Versions.hilt
        const val android = "com.google.dagger:hilt-android:$hiltVersion"
        const val kapt = "com.google.dagger:hilt-compiler:$hiltVersion"
        const val androidKapt = "androidx.hilt:hilt-compiler:1.2.0"
    }

    object Room {
        private const val room = "2.6.1"
        const val compiler = "androidx.room:room-compiler:$room"
        const val runtime = "androidx.room:room-runtime:$room"
        const val kts = "androidx.room:room-ktx:$room"
    }

    object Stripe {
        private const val stripeVersion = "20.37.3"
        const val stripe = "com.stripe:stripe-android:$stripeVersion"
        const val cardScan = "com.stripe:stripecardscan:$stripeVersion"
    }

    object Flipper {
        const val version = "0.250.0"
        const val soLoader = "com.facebook.soloader:soloader:0.10.5"
        const val leakCanary = "com.facebook.flipper:flipper-leakcanary2-plugin:$version"
        const val core = "com.facebook.flipper:flipper:$version"
        const val flipperNetwork = "com.facebook.flipper:flipper-network-plugin:$version"
        const val flipperNoOp2 = "com.github.theGlenn:flipper-android-no-op:0.11.4"
    }

    object Google {
        const val maps = "com.google.android.gms:play-services-maps:18.1.0"
        const val location = "com.google.maps:google-maps-services:2.2.0"
        const val userLocation = "com.google.android.gms:play-services-location:18.0.0"
        const val credentials = "androidx.credentials:credentials:1.2.2"
        const val auth = "androidx.credentials:credentials-play-services-auth:1.2.2"
        const val googleId = "com.google.android.libraries.identity.googleid:googleid:1.1.0"
    }

    object Video {
        const val exoplayer = "androidx.media3:media3-exoplayer:1.3.1"
        const val exoplayerDash = "androidx.media3:media3-exoplayer-dash:1.3.1"
        const val media3 = "androidx.media3:media3-ui:1.3.1"
    }
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.Hilt.android)
    ksp(Dependencies.Hilt.kapt)
    ksp(Dependencies.Hilt.androidKapt)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Square.interceptor)
    api(Dependencies.Square.gsonConverter)
}

fun DependencyHandler.room() {
    api(Dependencies.Room.runtime)
    api(Dependencies.Room.kts)
    ksp(Dependencies.Room.compiler)
}

fun DependencyHandler.flipper() {
    debugImplementation(Dependencies.Flipper.leakCanary)
    debugImplementation(Dependencies.Flipper.core)
    debugImplementation(Dependencies.Flipper.soLoader)
    debugImplementation(Dependencies.Flipper.flipperNetwork)
    releaseImplementation(Dependencies.Flipper.flipperNoOp2)
}

fun DependencyHandler.glide() {
    implementation(Dependencies.Imaging.glide)
    implementation(Dependencies.Imaging.glideAnnotations)
    implementation(Dependencies.Imaging.transformations)
    ksp(Dependencies.Imaging.glideCompiler)
}

fun DependencyHandler.debugImplementation(depName: String) {
    add("debugImplementation", depName)
}

fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

fun DependencyHandler.androidTestImplementation(depName: String) {
    add("androidTestImplementation", depName)
}

fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

fun DependencyHandler.api(depName: String) {
    add("api", depName)
}

private fun DependencyHandler.ksp(depName: String) {
    add("ksp", depName)
}

fun DependencyHandler.releaseImplementation(depName: String) {
    add("releaseImplementation", depName)
}
