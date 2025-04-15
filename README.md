# CI for Pull Request and build distribution

Welcome to the **CI for Pull Request** demo project! This project showcases a comprehensive continuous integration (CI) and continuous deployment (CD) pipeline for Android applications using GitHub Actions. The pipeline is triggered on pull requests to the `master` branch and includes steps for building, testing, and deploying the application.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
- [Workflow Overview](#workflow-overview)
- [Gradle Configuration](#gradle-configuration)
- [Setup and Configuration](#setup-and-configuration)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Automated Build and Test**: Automatically builds the app and runs tests on pull requests to the `master` branch.
- **Dependency Management**: Caches Gradle dependencies to speed up subsequent builds.
- **Artifact Management**: Lists generated APK files for inspection.
- **Emulator-based Testing**: Executes instrumental tests using Emulator.wtf.
- **Test Reporting**: Publishes test results in JUnit report format.
- **Fastlane Integration**: Executes Fastlane lanes for additional tasks, such as lint checks and unit tests.
- **Firebase App Distribution**: Deploys builds to Firebase App Distribution for beta testing, streamlining feedback from testers.

## Getting Started

To get started with this project, ensure that your repository is connected to GitHub Actions. The CI/CD pipeline automatically runs on any pull request to the `master` branch. Make sure you have the necessary secret tokens configured in your repository settings.

## Workflow Overview

This GitHub Actions workflow includes several jobs and steps:

1. **Checkout Repository**: Clones the repository to the runner.
2. **Set up Ruby and Java**: Installs Ruby and Java environments required for the build and Fastlane.
3. **Install Fastlane**: Installs Fastlane for auxiliary tasks.
4. **Install Android SDK**: Sets up the Android SDK to build the application.
5. **Cache Gradle Packages**: Caches Gradle packages to improve performance.
6. **Build App**: Builds the Debug APK and Android Test APK.
7. **List APK Files**: Lists all APK files generated during the build.
8. **Run Instrumental Tests**: Executes tests using Emulator.wtf.
9. **Publish Test Report**: Generates a test report in XML format.
10. **Fastlane Tasks**: Runs unit tests and app distribution Fastlane tasks as needed.
11. **Deploy to Firebase App Distribution**: Deploys the build to Firebase, facilitating beta testing and feedback collection.


## Files in interest
1. https://github.com/chandrakantNeogov/AutomationTestDemo/blob/master/app/build.gradle.kts
2. https://github.com/chandrakantNeogov/AutomationTestDemo/blob/master/.github/workflows/actions.yml
3. https://github.com/chandrakantNeogov/AutomationTestDemo/blob/master/fastlane/Fastfile

## Gradle Configuration

Ensure your `build.gradle` and `gradle-wrapper.properties` are properly configured. Below are essential snippets for the Gradle configuration:

### build.gradle Example

```groovy
import com.google.firebase.appdistribution.gradle.firebaseAppDistribution

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services)
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
        versionName = "1.0.1"

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
            firebaseAppDistribution {
                releaseNotesFile = "releasenotes.txt"
                testers = "ckondke@neogov.net"
            }
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
    reportsDirectory = file("${project.layout.buildDirectory}/reports/jacoco")
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
                "**/*Test*.*",
                "**/MainActivity.*",
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

