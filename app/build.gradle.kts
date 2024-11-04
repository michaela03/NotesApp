plugins {
    alias(libs.plugins.android.application) // За основното приложение
    alias(libs.plugins.kotlin.android) // За Kotlin поддръжка
}

android {
    // Активиране на Data Binding
    dataBinding {
        enable = true // Важно за работа с Data Binding
    }

    namespace = "com.example.notesapp"
    compileSdk = 35 // Версия на SDK

    defaultConfig {
        applicationId = "com.example.notesapp" // Идентификатор на приложението
        minSdk = 24 // Минимална версия на SDK
        targetSdk = 34 // Целева версия на SDK
        versionCode = 1 // Код на версията
        versionName = "1.0" // Име на версията

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Тестов инструмент
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Не активирайте минимизация за сега
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro" // Правила за ProGuard
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Поддръжка на Java 8
        targetCompatibility = JavaVersion.VERSION_1_8 // Поддръжка на Java 8
    }

    kotlinOptions {
        jvmTarget = "1.8" // Версия на JVM
    }
}

dependencies {
    implementation(libs.androidx.core.ktx) // Core KTX библиотека
    implementation(libs.androidx.appcompat) // Поддръжка на AppCompat
    implementation(libs.material) // Material компоненти
    implementation(libs.androidx.activity) // Поддръжка на Activity
    implementation(libs.androidx.constraintlayout) // ConstraintLayout
    implementation(libs.androidx.room.common) // Room основна библиотека
    implementation(libs.androidx.room.ktx) // Room KTX библиотека


    // Тестови библиотеки
    testImplementation(libs.junit) // JUnit за тестове
    androidTestImplementation(libs.androidx.junit) // JUnit за Android тестове
    androidTestImplementation(libs.androidx.espresso.core) // Espresso за UI тестове
}
