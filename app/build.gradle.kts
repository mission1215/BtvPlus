plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.firebase.appdistribution)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.kapt) // Kapt 플러그인 활성화
    alias(libs.plugins.hilt.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.skb.bourbon"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.skb.bourbon"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // BuildConfig로 라이브러리에 전달
        buildConfigField("int", "APP_VERSION_CODE", versionCode.toString())
        buildConfigField("String", "APP_VERSION_NAME", "\"$versionName\"")

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)

    //retrofit
    implementation(libs.bundles.retrofit.bundle)

    //compose
    implementation(libs.bundles.compose.bundle)

    //coil
    implementation(libs.coil.compose)
    implementation(project(":CognacPlayerLib"))

    //add Library
    implementation(project(":BourbonDomainLib"))

    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //dataStore
    implementation(libs.androidx.datastore.preferences)

    //Media3
    implementation(libs.media3.exoplayer)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}