import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm") version "1.3.72"
    id("maven-publish")
    id("com.jfrog.bintray") version "1.8.4"
}

group = "org.knanoid"
version = "0.1"

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

publishing {
    publications {
        create<MavenPublication>("maven-publication") {
            groupId = "org.knanoid"
            artifactId = "knanoid"

            from(components["java"])

            pom.withXml {
                asNode().apply {
                    appendNode("description", "KNanoid generates unique IDs")
                    appendNode("name", "KNanoid")
                    appendNode("url", "https://github.com/alekseinovikov/knanoid")
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
}