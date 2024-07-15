plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.realm.kotlin") version "1.16.0" apply false
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}


android {
    namespace = "com.abplus.k2a2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.abplus.k2a2"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        testInstrumentationRunner = "com.abplus.k2a2.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.chargemap.compose:numberpicker:1.0.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.6.1")
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1")

    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //noinspection GradleDependency
    implementation("io.realm.kotlin:library-base:1.16.0")
    //noinspection GradleDependency
    implementation("io.realm.kotlin:gradle-plugin:1.16.0")

    implementation("com.google.dagger:hilt-android:2.51.1")
    annotationProcessor("com.google.dagger:hilt-compiler:2.51.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.51.1")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

apply(plugin = "kotlin-kapt")
apply(plugin = "io.realm.kotlin")
//apply(plugin = "com.google.dagger.hilt.android")
