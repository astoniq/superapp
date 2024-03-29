pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Superapp"
include(":app")
include(":core:core-common")
include(":core:core-domain")
include(":core:core-data")
include(":core:core-model")
include(":feature:feature-splash")
include(":feature:feature-portal")
include(":feature:feature-hub")
include(":feature:feature-app")
include(":feature:feature-login")