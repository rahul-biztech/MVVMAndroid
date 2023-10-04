// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("org.sonarqube") version "4.2.1.3168"
}

sonar {
    properties {
        property("sonar.host.url","http://localhost:9000")
        property("sonar.projectName","KotlinCompose")
        property("sonar.projectKey","KotlinCompose")
        property("sonar.login", "admin")
        property("sonar.password", "admin123")
        property("sonar.exclusions", "**/nameoflibrary/**")
        property("sonar.token","sqp_")
    }
}
