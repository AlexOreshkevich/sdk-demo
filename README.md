# sdk-demo
Demo of SDK for managing versioning and releases of corporate libraries

### How to clone
```shell
git clone --recursive git@github.com:AlexOreshkevich/sdk-demo.git
```

### How to build
```shell
./gradlew build
```

### How to add new submodules
Create `dev` branch in your new library candidate first of all.

```shell
git submodule add git@github.com:AlexOreshkevich/library-demo-a.git modules/library-demo-a
git commit -am "Added new submodule dm-core-api"
git push
```

### How to remove submodule
#### Remove the submodule entry from .git/config
```
git submodule deinit -f path/to/submodule
```
#### Remove the submodule directory from the superproject's .git/modules directory
```
rm -rf .git/modules/path/to/submodule
```
#### Remove the entry in .gitmodules and remove the submodule directory located at path/to/submodule
```
git rm -f path/to/submodule
```

### How to update submodules
```
git submodule update --init --remote --merge
```

### How to merge all submodules with `master` branch
```shell
git submodule foreach 'git pull origin master'
```

### How to use version catalog
Add version catalog to your `settings.gradle.kts` file using Kotlin DSL:
```kotlin
dependencyResolutionManagement {

  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

  repositories {
    mavenLocal()
    mavenCentral()
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
    }
  }

  versionCatalogs {
    create("libs") {
      from("com.google:version-catalog:DEV-SNAPSHOT")
    }
  }
}
```

Replace `version` property in your `build.gradle.kts` under `publishing` section:
```kotlin
publishing {
  publications {
    create<MavenPublication>("googleRepository") {
      ...
      version = libs.versions.sdk.get()
      ...
    }
  }
}
```
In addition, you can use library versions from catalog as well as SDK version itself:
```kotlin
dependencies {
    api(libs.javax.inject)
}
```

### How to use SDK for automatic version management
Publish SDK to your Maven local or corporate repository first:
```shell
./gradlew publishToMavenLocal
```
or
```shell
./gradlew publish
```
After that in your library in `build.gradle.kts` you would be able to consume just published platform:
```kotlin
version = libs.versions.sdk.get()

dependencies {
  api(platform("com.google:sdk-demo:${libs.versions.sdk.get()}"))
}
```

### How to release to local maven repo
```shell
./gradlew publishModulesToMavenLocal
```

### How to release to production
```shell
./gradlew publishModules
```