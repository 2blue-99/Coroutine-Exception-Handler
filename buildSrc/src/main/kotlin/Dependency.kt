// Dependencies.kt
object Versions {
    // AndroidX
    const val APP_COMPAT_VERSION = "1.6.1"
    const val MATERIAL_VERSION = "1.9.0"
    const val CONSTRAINT_LAYOUT_VERSION = "2.1.4"

    // KTX
    const val CORE_VERSION = "1.9.0"

    // TEST
    const val TEST_EXT_JUNIT_VERSION = "1.1.5"
    const val JUNIT_VERSION = "4.13.2"

    // Android Test
    const val TEST_ESPRESSO_CORE_VERSION = "3.5.1"

    // DI
    const val DI_VERSION = "2.46.1"
    const val DI_COMPILER_VERSION = "1.0.0"

    // Activity Version
    const val ACTIVITY_VERSION = "1.6.1"

    // Retrofit
    const val RETROFIT_VERSION = "2.9.0"
    const val RETROFIT_LOGGING_VERSION = "5.0.0-alpha.3"

    //Swipe Refresh Layout
    const val REFRESH_VERSION = "1.1.0"
}

object Libraries {

    object AndroidX {
        const val App_Compat = "androidx.appcompat:appcompat:${Versions.APP_COMPAT_VERSION}"
        const val Material = "com.google.android.material:material:${Versions.MATERIAL_VERSION}"
        const val Constraint_Layout = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT_VERSION}"
    }

    object KTX {
        const val Core = "androidx.core:core-ktx:${Versions.CORE_VERSION}"
    }

    object Test {
        const val TestExtJUnit = "androidx.test.ext:junit:${Versions.TEST_EXT_JUNIT_VERSION}"
        const val JUnit = "junit:junit:${Versions.JUNIT_VERSION}"
    }

    object AndroidTest {
        const val Espresso_Core = "androidx.test.espresso:espresso-core:${Versions.TEST_ESPRESSO_CORE_VERSION}"
    }

    object DaggerHilt{
        const val HiltAndroid = "com.google.dagger:hilt-android:${Versions.DI_VERSION}"
        const val HiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.DI_VERSION}"
        const val HiltAndroidXCompiler = "androidx.hilt:hilt-compiler:${Versions.DI_COMPILER_VERSION}"
    }

    object ActivityLifecycle{
        const val Activity_Ktx = "androidx.activity:activity-ktx:${Versions.ACTIVITY_VERSION}"
        const val Fragment_Ktx = "androidx.fragment:fragment-ktx:${Versions.ACTIVITY_VERSION}"
    }

    object Retrofit{
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
        const val Retrofit_Converter = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_VERSION}"
        const val Okhttp_Logging = "com.squareup.okhttp3:logging-interceptor:${Versions.RETROFIT_LOGGING_VERSION}"
    }

    object SwipeRerefreshLayout{
        const val Swipe_Refresh_Layout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.REFRESH_VERSION}"
    }
}