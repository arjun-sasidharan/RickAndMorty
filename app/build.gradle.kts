plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
}

// Epoxy
kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.example.rickandmorty"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.rickandmorty"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    // Jetpack Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")

    // Networking
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation ("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Moshi
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.12.0")

    // Picasso
    implementation ("com.squareup.picasso:picasso:2.8")


    // ViewModel + Coroutine
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    // Epoxy
    val epoxyVersion = "5.1.3"
    implementation ("com.airbnb.android:epoxy:$epoxyVersion")
    kapt ("com.airbnb.android:epoxy-processor:$epoxyVersion")
    implementation ("com.airbnb.android:epoxy-paging:5.0.0")
    implementation ("com.airbnb.android:epoxy-paging3:5.0.0")

    // Pagination
    val pagination_version = "3.2.0"
    implementation ("androidx.paging:paging-runtime-ktx:$pagination_version")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}