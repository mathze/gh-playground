pluginManagement {
  val kotlinVersion = "1.6.20"
  repositories {
    gradlePluginPortal()
  }

  plugins {
    // realization
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"

    // quality
    id("io.gitlab.arturbosch.detekt") version "1.20.0"

    // documentation
    id("org.jetbrains.dokka") version "1.7.10"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
  }
}

rootProject.name = "gh-playground"
