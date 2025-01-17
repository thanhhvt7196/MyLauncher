pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven(url = "https://jitpack.io")
    }
}
include (":IconLoader")
project(":IconLoader").projectDir =  File(rootDir, "iconloaderlib")

//include ':SharedLibWrapper'
//project(':SharedLibWrapper').projectDir = new File(rootDir, 'SharedLibWrapper')

include (":systemUIPluginCore")