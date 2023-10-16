plugins {
    id(Plugins.Path.AndroidLibrary)
    id(Plugins.Path.JetBrainsKotlinAndroid)
}

android {
    namespace = Plugins.Path.AppNameSpace
    compileSdk = AppConfig.CompileSdk

    defaultConfig {
        minSdk = AppConfig.MinSdk

        testInstrumentationRunner = AppConfig.TestInstrumentationRunner
        consumerProguardFiles(AppConfig.ConsumerProguardRules)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(AppConfig.DefaultProguardFile),
                AppConfig.ProguardRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = AppConfig.JvmTarget
    }
}



dependencies {
    implementation(Libraries.KTX.Core)
    implementation(Libraries.AndroidX.App_Compat)
    implementation(Libraries.AndroidX.Material)
    testImplementation(Libraries.Test.TestExtJUnit)
    androidTestImplementation(Libraries.Test.JUnit)
    androidTestImplementation(Libraries.AndroidTest.Espresso_Core)
}