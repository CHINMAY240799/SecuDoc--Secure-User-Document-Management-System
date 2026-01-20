plugins {
	java
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("eclipse-wtp")
}

//java {
//    toolchain {
//        languageVersion.set(JavaLanguageVersion.of(17))
//    }
//}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

group = "com.secudoc"
version = "0.0.1-SNAPSHOT"
description = "Authentication Service"


configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

//dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("org.springframework.boot:spring-boot-starter-validation")
//	implementation("org.springframework.boot:spring-boot-starter-webmvc")
//	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
//	compileOnly("org.projectlombok:lombok")
//	runtimeOnly("org.postgresql:postgresql")
//	annotationProcessor("org.projectlombok:lombok")
//	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
//	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
//	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
//	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//}

dependencies {

    // Core Spring Boot starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // Database driver
    runtimeOnly("org.postgresql:postgresql")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Testing (IMPORTANT)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
