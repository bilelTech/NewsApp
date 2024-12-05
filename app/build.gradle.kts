plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.test.newsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.test.newsapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/\"")
            buildConfigField("String", "API_KEY", "\"b385adec3cb0481cb05f06384b6c7860\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/\"")
            buildConfigField("String", "API_KEY", "\"b385adec3cb0481cb05f06384b6c7860\"")
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
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    // UI & Navigation
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.compose.navigation)

    // Paging compose
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)

    // Hilt for dependencies injection
    implementation(libs.dagger.hilt.android)
    implementation(libs.dagger.hilt.compose)
    kapt(libs.dagger.hilt.compiler)

    //retrofit for consume api
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.urlConnection)
    implementation(libs.retrofit.logging)
    implementation(libs.gson)

    // coil compose to display images
    implementation(libs.coil)

    // Unit Test & Test UI
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mock.webserver)
    testImplementation(libs.test.coroutines)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.turbine)
    testImplementation(libs.paging.test)
    testImplementation(libs.robolectric)

}