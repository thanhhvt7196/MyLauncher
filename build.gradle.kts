buildscript {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://maven.aliyun.com/repository/jcenter") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
    }
    dependencies {
        classpath(libs.gradle)
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    }
}

val ANDROID_TOP = "${rootDir}/../../.."
val FRAMEWORK_PREBUILTS_DIR = "${ANDROID_TOP}/prebuilts/libs/"

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.protobuf)
}

android {
    compileSdk = 34

    namespace = "com.android.launcher3"

    defaultConfig {
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("boolean", "IS_STUDIO_BUILD", "false")
        buildConfigField("boolean", "QSB_ON_FIRST_SCREEN", "true")
        buildConfigField("boolean", "IS_DEBUG_DEVICE", "false")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // The flavor dimensions for build variants (e.g. aospWithQuickstep, aospWithoutQuickstep)
    // See: https://developer.android.com/studio/build/build-variants#flavor-dimensions
    flavorDimensionList.addAll(listOf("app"))

    productFlavors {
        create("aosp") {
            dimension = "app"
            applicationId = "com.android.launcher3"
            testApplicationId = "com.android.launcher3.tests"
        }

        create("l3go") {
            dimension = "app"
            applicationId = "com.android.launcher3"
            testApplicationId = "com.android.launcher3.tests"
        }
    }

//    // Disable release builds for now
//    android?.variantFilter { variant ->
//        if (variant.buildType.name.endsWith("release")) {
//            variant.setIgnore(true)
//        }
//    }

    sourceSets {
        named("main") {
            res.srcDirs("res")
            java.srcDirs(listOf("src", "src_plugins", "tests/shared"))
            manifest.srcFile("AndroidManifest-common.xml")
//            named("proto") {
//                srcDirs("protos/", "protos_overrides/")
//            }
        }

        named("androidTest") {
            res.srcDirs("tests/res")
            java.srcDirs(listOf("tests/src", "tests/tapl"))
            manifest.srcFile("tests/AndroidManifest-common.xml")
        }

        named("androidTestDebug") {
            manifest.srcFile("tests/AndroidManifest.xml")
        }

        named("aosp") {
            java.srcDirs(listOf("src_flags", "src_shortcuts_overrides", "src_ui_overrides"))
            manifest.srcFile("AndroidManifest.xml")
        }

        named("l3go") {
            res.srcDirs("go/res")
            java.srcDirs(listOf("go/src", "src_ui_overrides"))
            manifest.srcFile("go/AndroidManifest.xml")
            manifest.srcFile("AndroidManifest.xml")
        }
        val protocVersion = "4.26.1"//'3.8.0'

        protobuf {
            // Configure the protoc executable
            protoc {
                if (osdetector.os == "osx") {
                    artifact = "com.google.protobuf:protoc:${protocVersion}:osx-x86_64"
                } else {
                    //        artifact = "com.google.protobuf:protoc:${protocVersion}${PROTO_ARCH_SUFFIX}"
                    artifact = "com.google.protobuf:protoc:${protocVersion}"
                }
            }
            generateProtoTasks {
                all().forEach { task ->
                    task.builtins {
                        create("java") {
                            option("lite")
                        }
                    }
                }
            }
        }
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {
    implementation("androidx.dynamicanimation:dynamicanimation:1+")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.preference:preference:1+")
    implementation(project(":IconLoader"))
    implementation(project(":systemUIPluginCore"))
    protobuf(files("protos/"))
    protobuf(files("protos_overrides/"))
//    implementation(project(':UiTestsLibLauncher')
//    withQuickstepImplementation project(':SharedLibWrapper')
    implementation("com.google.protobuf:protobuf-javalite:4.26.1")
    implementation("androidx.core:core-ktx:1.13.1")

    // Required for AOSP to compile. This is already included in the sysui_shared.jar
    implementation(fileTree(baseDir = "${FRAMEWORK_PREBUILTS_DIR}").include("plugin_core.jar"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.mockito:mockito-core:1.9.5")
    androidTestImplementation("com.google.dexmaker:dexmaker:1.2")
    androidTestImplementation("com.google.dexmaker:dexmaker-mockito:1.2")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test:rules:1.0.2")
    androidTestImplementation("com.android.support.test.uiautomator:uiautomator-v18:2.1.3")
    androidTestImplementation("androidx.annotation:annotation:1+")

    api("com.airbnb.android:lottie:3.3.0")

    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.slice:slice-builders:1.0.0")
}


