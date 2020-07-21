import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm") version "1.3.72"
    id("maven-publish")
    id("com.jfrog.bintray") version "1.8.4"
}

val artifactVersion = "0.2"

group = "org.knanoid"
version = artifactVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = mutableSetOf(PASSED, SKIPPED, FAILED)
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

val pomUrl = "https://github.com/alekseinovikov/knanoid"
val pomScmUrl = "https://github.com/alekseinovikov/knanoid"
val pomIssueUrl = "https://github.com/alekseinovikov/knanoid/issues"
val pomDesc = "https://github.com/alekseinovikov/knanoid"

val gitHubRepo = "https://github.com/alekseinovikov/knanoid.git"
val githubReadme = "README.md"

val pomLicenseName = "GPL3"
val pomLicenseUrl = "https://www.gnu.org/licenses/gpl-3.0.en.html"
val pomLicenseDist = "repo"

val pomDeveloperId = "alekseinovikov"
val pomDeveloperName = "Aleksei Novikov"

val toolDescription = "KNanoid generates unique IDs"

publishing {
    publications {
        create<MavenPublication>("maven-publication") {
            groupId = "org.knanoid"
            artifactId = "knanoid"

            from(components["java"])

            pom.withXml {
                asNode().apply {
                    appendNode("description", toolDescription)
                    appendNode("name", "KNanoid")
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
                        appendNode("url", pomScmUrl)
                    }
                }
            }
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
        vcsUrl = "https://github.com/alekseinovikov/knanoid.git"
    }

    pkg.apply {
        repo = "maven"
        name = "knanoid"
        userOrg = pomDeveloperId
        githubRepo = gitHubRepo
        vcsUrl = pomScmUrl
        description = toolDescription
        setLabels("kotlin", "knanoid")
        setLicenses("GPL-3.0")
        desc = toolDescription
        websiteUrl = pomUrl
        issueTrackerUrl = pomIssueUrl
        githubReleaseNotesFile = githubReadme

        version.apply {
            name = artifactVersion
            desc = pomDesc
            vcsTag = artifactVersion
        }
    }
}