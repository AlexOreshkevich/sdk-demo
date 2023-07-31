plugins {
  `java-platform`
  `maven-publish`
}

group = "com.google"
version = libs.versions.sdk.get()

dependencies {
  constraints {

    val sdkVersion = libs.versions.sdk.get()

    api("com.google:library-demo-a:$sdkVersion")
  }
}

publishing {
  publications {
    create<MavenPublication>("myPlatform") {
      from(components["javaPlatform"])
    }
  }
  /*
  repositories {
    maven {
      name = "swsRepository"
      url = uri("https://repo.google.com/repository/snapshots")
      credentials(PasswordCredentials::class)
    }
  }*/
}

tasks.clean {
  gradle.includedBuilds.forEach { build ->
    dependsOn(build.task(":lib:clean"))
  }
}

tasks.register("cleanTest") {
  gradle.includedBuilds.forEach { build ->
    dependsOn(build.task(":lib:cleanTest"))
  }
}

tasks.build {
  gradle.includedBuilds.forEach { build ->
    dependsOn(build.task(":lib:build"))
  }
}

tasks.register("publishModules") {
  gradle.includedBuilds.forEach { build ->
    dependsOn(build.task(":lib:publish"))
  }
}

tasks.register("publishModulesToMavenLocal") {
  gradle.includedBuilds.forEach { build ->
    dependsOn(build.task(":lib:publishToMavenLocal"))
  }
}