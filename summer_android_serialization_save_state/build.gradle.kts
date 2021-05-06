import com.jfrog.bintray.gradle.BintrayExtension
import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("maven")
    id("maven-publish")
    id("com.jfrog.bintray")
}

android {
    compileSdk = targetVersion

    defaultConfig {
        // bundlizer requires minSdk >= 16
        minSdk = 16
        targetSdk = targetVersion
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    api("androidx.appcompat:appcompat:$appCompatVersion")

    api("com.github.adevone.summer:summer:$summerVersion")
    api("com.github.adevone.summer:summer-androidx:$summerVersion")
    api("com.github.adevone.summer:summer-serialization-strategy:$summerVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("dev.ahmedmourad.bundlizer:bundlizer-core:0.3.0")
}

val sourceJar by tasks.registering(Jar::class) {
    from(android.sourceSets.getByName("main").java.srcDirs)
    archiveClassifier.set("sources")
}

group = summerGroup
version = summerVersion

publishing {
    publications {
        create<MavenPublication>("summerAndroidXSaveState") {
            groupId = project.group.toString()
            artifactId = project.name
            version = summerVersion
            artifact(tasks.getByName("sourceJar"))
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
        }
    }
}

val propsFile = File(rootProject.rootDir, "bintray.properties")
if (propsFile.exists()) {
    bintray {
        val bintrayProps = Properties().apply {
            load(propsFile.inputStream())
        }
        user = bintrayProps.getProperty("USERNAME")
        key = bintrayProps.getProperty("API_KEY")
        pkg(closureOf<BintrayExtension.PackageConfig> {
            repo = "summer"
            name = project.name
            userOrg = "summermpp"
            setLicenses("MIT")
            vcsUrl = "https://github.com/adevone/summer"
        })
        setPublications("summerAndroidXSaveState")
    }
}