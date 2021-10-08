import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("org.jlleitschuh.gradle.ktlint-idea").version("10.2.0")
}

group = "tech.alexib"
version = "1.0.0"

repositories {
    mavenCentral()
}
val kotestVersion = "4.6.3"
dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

ktlint {
    version.set("0.42.1")
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
    outputToConsole.set(true)
    outputColorName.set("BLUE")
    ignoreFailures.set(false)

    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    additionalEditorconfigFile.set(file("${project.projectDir}/.editorConfig"))
}
tasks {
    ktlintFormat {
        doLast {
            delete("src/main/java")
            delete("src/test/java")
        }
    }
}
