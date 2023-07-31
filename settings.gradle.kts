rootProject.name = "sdk-demo"

// loads all modules into composite build without hard coding their names
// https://docs.gradle.org/current/samples/sample_composite_builds_hierarchical_multirepo.html#running_multirepo_app_with_dependencies_from_included_builds
file("modules").listFiles()
    ?.filter { !it.name.startsWith(".") }
    ?.forEach { moduleBuild: File ->
      includeBuild(moduleBuild)
    }

dependencyResolutionManagement {

  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

  repositories {
    mavenLocal()
    mavenCentral()
    /*
    maven {
      url = uri("https://repo.google.com/repository/internal")
      mavenContent {
        releasesOnly()
      }
    }
    maven {
      url = uri("https://repo.google.com/repository/snapshots")
      mavenContent {
        snapshotsOnly()
      }
    }*/
  }

  versionCatalogs {
    create("libs") {
      from("com.google:version-catalog:DEV-SNAPSHOT")
    }
  }
}