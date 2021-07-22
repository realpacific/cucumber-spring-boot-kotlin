import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.5.2"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.5.20"
  kotlin("plugin.spring") version "1.5.20"
  id("org.jetbrains.kotlin.plugin.jpa") version "1.5.21"

}

group = "com.raywenderlich"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  developmentOnly("org.springframework.boot:spring-boot-devtools")

  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  runtimeOnly("com.h2database:h2")

  testImplementation("io.rest-assured:rest-assured:4.4.0")

  testImplementation("org.springframework.boot:spring-boot-starter-test")

  runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("javax.validation:validation-api:2.0.1.Final")
  implementation("org.hibernate:hibernate-validator:7.0.1.Final")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}