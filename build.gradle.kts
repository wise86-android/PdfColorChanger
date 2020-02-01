import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "5.2.0" 
    kotlin("jvm") version "1.3.61"
}

group = "org.wise86."
version = "1.0.0"

application{
    mainClassName = "MainKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.apache.pdfbox", "pdfbox", "2.0.18")

    testCompile("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<ShadowJar>{
    archiveFileName.set("${archiveBaseName.get()}_v${archiveVersion.get()}.${archiveExtension.get()}")
    //generate the jar in the root project dir
    destinationDirectory.set(projectDir)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf("-Xuse-experimental=kotlin.ExperimentalUnsignedTypes")
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs=listOf("-Xuse-experimental=kotlin.ExperimentalUnsignedTypes")
    }
}
