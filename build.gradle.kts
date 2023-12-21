import java.util.Properties

plugins {
  kotlin("jvm")
  kotlin("plugin.spring")

  id("io.spring.dependency-management")
  id("org.springframework.boot")

  jacoco
  id("io.gitlab.arturbosch.detekt")

  id("org.jetbrains.dokka")
  id("org.asciidoctor.jvm.convert")

  `maven-publish`
}

repositories {
  mavenCentral()
}

group = "de.qualersoft"

dependencyManagement {
  imports {
    mavenBom("org.junit:junit-bom:5.10.1")
  }

  dependencies {
    dependency("org.springframework.boot:spring-boot-starter-web:3.2.1") {
      exclude("org.springframework.boot:spring-boot-starter-tomcat")
    }
  }
}

dependencies {
  listOf("spring-boot-starter-web", "spring-boot-starter-jetty", "spring-boot-starter-actuator").forEach {
    implementation(group = "org.springframework.boot", name = it)
  }

  testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
  testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test-junit5")
  testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine")

  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-rules-libraries", version = detekt.toolVersion)
}

configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
  allRules = true
  config.from(files("$rootDir/detekt.yml"))
  source.from(files("src/main/kotlin"))
}
configurations.detekt {
  resolutionStrategy.eachDependency {
    if (requested.group == "org.jetbrains.kotlin") {
      useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
    }
  }
}


jacoco {
  toolVersion = "0.8.11"
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
  reports {
    html.required = true
    xml.required = true
    sarif.required = true
    txt.required = false
  }
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

tasks.withType<JacocoReport> {
  reports {
    xml.required = true
    html.required = true
    csv.required = false
  }
}

tasks.dokkaHtml {
  outputDirectory = layout.buildDirectory.dir("docs/kdoc")
}

javaToolchains {
  compilerFor {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  compilerOptions {
    freeCompilerArgs.add("-Xjsr305=strict")
    jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
  }
}

publishing {
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/mathze/gh-playground")
      credentials {
        username = project.findProperty("publish.gh.mathze.gpr.usr") as String? ?: System.getenv("USERNAME")
        password = project.findProperty("publish.gh.mathze.gpr.key") as String? ?: System.getenv("TOKEN")
      }
    }
  }
  publications {
    register<MavenPublication>("gpr") {
      artifact(tasks.bootJar)
    }
  }
}

tasks.register("updateVersion") {
  description = """ONLY FOR CI/CD purposes!
    |
    |This task is meant to be used by CI/CD to generate new release versions.
    |Prerequists: a `gradle.properties` next to this build-script must exist.
    |   version must follow semver-schema (<number>.<number.<number>*)
    |Usage:
    |  > ./gradlew updateVersion -PnewVersion="the new version"
  """.trimMargin()

  doLast {
    var newVersion = project.findProperty("newVersion") as String?
      ?: throw IllegalArgumentException(
        "No `newVersion` specified!" +
            " Usage: ./gradlew updateVersion -PnewVersion=<version>"
      )

    if (newVersion.contains("snapshot", true)) {
      val props = Properties()
      props.load(getGradlePropsFile().inputStream())
      val currVersion = (props["version"] as String?)!!.split('.').toMutableList()
      val next = currVersion.last()
        .replace(Regex("[^\\d]+"), "").toInt() + 1
      currVersion[currVersion.lastIndex] = "$next-SNAPSHOT"
      newVersion = currVersion.joinToString(".")
    }

    persistVersion(newVersion)
  }
}

fun getGradlePropsFile(): File {
  val propsFile = file("./gradle.properties")
  if (!propsFile.exists()) {
    val msg = "This task requires version to be stored in gradle.properties file, which does not exist!"
    throw UnsupportedOperationException(msg)
  }
  return propsFile
}

fun persistVersion(newVersion: String) {
  val propsFile = getGradlePropsFile()
  val props = Properties()
  props.load(propsFile.inputStream())
  props.setProperty("version", newVersion)
  props.store(propsFile.outputStream(), null)
}
