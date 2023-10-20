plugins {
    id(Plugins.Path.AndroidLibrary)
    id(Plugins.Path.JetBrainsKotlinAndroid)
    id(Plugins.NameTag.DaggerHiltAndroid)
    id(Plugins.NameTag.KotlinKAPT)
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

    implementation(project(":domain"))

    implementation(Libraries.KTX.Core)
    implementation(Libraries.AndroidX.App_Compat)
    implementation(Libraries.AndroidX.Material)
    testImplementation(Libraries.Test.TestExtJUnit)
    androidTestImplementation(Libraries.Test.JUnit)
    androidTestImplementation(Libraries.AndroidTest.Espresso_Core)

    implementation(Libraries.DaggerHilt.HiltAndroid)
    kapt(Libraries.DaggerHilt.HiltAndroidCompiler)
    kapt(Libraries.DaggerHilt.HiltAndroidXCompiler)

    implementation(Libraries.Retrofit.Retrofit)
    implementation(Libraries.Retrofit.Retrofit_Converter)
    implementation(Libraries.Retrofit.Okhttp_Logging)

    implementation ("com.tickaroo.tikxml:annotation:0.8.13")
    implementation ("com.tickaroo.tikxml:core:0.8.13")
    implementation ("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    kapt ("com.tickaroo.tikxml:processor:0.8.13")
    implementation ("org.simpleframework:simple-xml:2.7.1")

    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
}