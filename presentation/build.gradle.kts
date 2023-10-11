plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.basepractice"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.example.basepractice"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Libraries.KTX.Core)
    implementation(Libraries.AndroidX.App_Compat)
    implementation(Libraries.AndroidX.Material)
    implementation(Libraries.AndroidX.Constraint_Layout)
    testImplementation(Libraries.Test.TestExtJUnit)
    androidTestImplementation(Libraries.Test.JUnit)
    androidTestImplementation(Libraries.AndroidTest.Espresso_Core)

    implementation(Libraries.DaggerHilt.HiltAndroid)
    kapt(Libraries.DaggerHilt.HiltAndroidCompiler)
    kapt(Libraries.DaggerHilt.HiltAndroidXCompiler)

    implementation(Libraries.ActivityLifecycle.Activity_Ktx)

    implementation(Libraries.Retrofit.Retrofit)

    implementation(Libraries.SwipeRerefreshLayout.Swipe_Refresh_Layout)
}