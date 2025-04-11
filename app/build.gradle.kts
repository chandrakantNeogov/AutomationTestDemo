plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    jacoco
}

android {
    namespace = "com.neogov.automationtestdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.neogov.automationtestdemo"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    testOptions {
        animationsDisabled = true
        unitTests.isIncludeAndroidResources = true
    }


    viewBinding {
        enable = true
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
        }

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

}

jacoco {
    toolVersion = "0.8.8"
}

tasks.withType<Test> {
    jacoco {

    }

}


tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")
    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val debugTree = fileTree(
        mapOf(
            "dir" to "${project.layout.buildDirectory}/intermediates/javac/debug",
            "excludes" to listOf(
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*"
            )
        )
    )
    val kotlinDebugTree = fileTree(
        mapOf(
            "dir" to "${project.layout.buildDirectory}/tmp/kotlin-classes/debug",
            "excludes" to listOf(
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*"
            )
        )
    )
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree, kotlinDebugTree))
    executionData.setFrom(
        fileTree(
            mapOf(
                "dir" to project.layout.buildDirectory,
                "includes" to listOf(
                    "outputs/code-coverage/connected/*coverage.ec",
                    "jacoco/testDebugUnitTest.exec"
                )
            )
        )
    )
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)

}

