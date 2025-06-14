plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}


android {
    namespace = "com.example.lesson_1"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.netology.nmedia"
        minSdk = 26
        targetSdk = 35
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
        debug {
            manifestPlaceholders["usesCleartextTraffic"] = true
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    val core_version = "1.15.0"
    val appcompat_version = "1.7.0"
    val mdc_version = "1.12.0"
    val constraintlayout_version = "2.2.1"
    val recyclerview_version = "1.4.0"
    val junit_version = "4.13.2"
    val ext_junit_version = "1.2.1"
    val espresso_core_version = "3.6.1"
    val activity_version = "1.10.1"
    val lifecycle_version = "2.8.7"
    val gson_version = "2.12.1"
    val room_version = "2.5.2"
    val firebase_version = "33.13.0"
    val okhttp_version = "4.12.0"
    val play_services_base_version = "18.3.0"
    val core_desugaring_version = "2.1.5"

    implementation("androidx.core:core-ktx:$core_version")
    implementation("androidx.appcompat:appcompat:$appcompat_version")
    implementation("com.google.android.material:material:$mdc_version")
    implementation("androidx.constraintlayout:constraintlayout:$constraintlayout_version")
    implementation("androidx.recyclerview:recyclerview:$recyclerview_version")
    implementation("androidx.activity:activity-ktx:$activity_version")
    implementation("androidx.fragment:fragment-ktx:1.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("com.google.code.gson:gson:$gson_version")
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation(platform("com.google.firebase:firebase-bom:$firebase_version"))
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")
    implementation("com.google.android.gms:play-services-base:$play_services_base_version")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$core_desugaring_version")

    testImplementation("junit:junit:$junit_version")
    androidTestImplementation("androidx.test.ext:junit:$ext_junit_version")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso_core_version")
}