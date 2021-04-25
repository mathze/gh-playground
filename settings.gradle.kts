pluginManagement {
  val kotlinVersion = "1.4.32"
  repositories {
    gradlePluginPortal()
  }

  plugins {
    // realization
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    // quality
    id("io.gitlab.arturbosch.detekt") version "1.16.0"

    // documentation
    id("org.jetbrains.dokka") version "1.4.32"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
  }
}
rootProject.name = "gh-playground"