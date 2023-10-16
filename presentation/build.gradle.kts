plugins {
    id(Plugins.Path.AndroidApplication)
    id(Plugins.Path.JetBrainsKotlinAndroid)
    id(Plugins.NameTag.DaggerHiltAndroid)
    id(Plugins.NameTag.KotlinKAPT)
}

android {
    namespace = Plugins.Path.AppNameSpacePresentation
    compileSdk = AppConfig.CompileSdk

    defaultConfig {
        applicationId = AppConfig.ApplicationId
        minSdk = AppConfig.MinSdk
        targetSdk = AppConfig.TargetSdk
        versionCode = AppConfig.VersionCode
        versionName = AppConfig.VersionName

        testInstrumentationRunner = AppConfig.TestInstrumentationRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile(AppConfig.DefaultProguardFile), AppConfig.ProguardRules)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = AppConfig.JvmTarget
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