plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.bntw"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDir("src/main/backend")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("io.jsonwebtoken:jjwt:0.12.6")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6") // or 'io.jsonwebtoken:jjwt-gson:0.12.6' for gson
    testImplementation("org.springframework.security:spring-security-test")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

task<Exec>("frontendCheck") {
    commandLine("pnpm", "--version")
}

task<Exec>("frontendBuild") {
    commandLine("pnpm", "frontend:build")
}

task<Exec>("frontendWatch"){
    commandLine("pnpm", "frontend:watch")
}

task<Exec>("frontendInstall") {
    commandLine("pnpm", "install")
}

tasks.named("build") {
    dependsOn("frontendBuild")
}

tasks.bootRun {
    args("--spring.profiles.active=local")
}

tasks.register<DefaultTask>("bootRunWithFrontend") {
    group = "application"
    description = "Runs the Spring Boot application with frontend watch mode in local profile"

    doFirst {
        ProcessBuilder("pnpm", "frontend:watch")
            .inheritIO()
            .directory(project.projectDir)
            .start()

        Thread.sleep(200)
    }

    finalizedBy(tasks.named("bootRun"))
}