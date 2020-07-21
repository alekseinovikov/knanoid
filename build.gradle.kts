import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm") version "1.3.72"
    id("idea")
    id("maven-publish")
    id("java")
    id("com.jfrog.bintray") version "1.8.4"
}

val artifactVersion = "0.5"

group = "org.knanoid"
version = artifactVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = mutableSetOf(PASSED, SKIPPED, FAILED)
    }
}

val pomUrl = "https://github.com/alekseinovikov/knanoid"
val gitHubRepo = "https://github.com/alekseinovikov/knanoid.git"
val githubReadme = "README.md"

val pomLicenseName = "GNU GENERAL PUBLIC LICENSE, Version 3.0"
val pomLicenseUrl = "https://www.gnu.org/licenses/gpl-3.0.en.html"
val pomLicenseDist = "repo"

val pomDeveloperId = "alekseinovikov"
val pomDeveloperName = "Aleksei Novikov"

val toolDescription = "KNanoid generates unique IDs"

publishing {
    publications {
        create<MavenPublication>("mavenPublication") {
            groupId = "org.knanoid"
            artifactId = "knanoid"

            pom.withXml {
                asNode().apply {
                    appendNode("description", toolDescription)
                    appendNode("name", "knanoid")
                    appendNode("url", pomUrl)
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", pomLicenseName)
                        appendNode("url", pomLicenseUrl)
                        appendNode("distribution", pomLicenseDist)
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", pomDeveloperId)
                        appendNode("name", pomDeveloperName)
                    }
                    appendNode("scm").apply {
                        appendNode("url", pomUrl)
                    }
                }
            }

            from(components["java"])
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER") ?: System.getProperty("bintrayUser")
    key = System.getenv("BINTRAY_KEY") ?: System.getProperty("bintrayKey")

    setConfigurations("archives")
    setPublications("mavenPublication")

    pkg.apply {
        repo = "utils"
        name = project.name
        setLicenses("GPL-3.0")
        vcsUrl = gitHubRepo
    }
}