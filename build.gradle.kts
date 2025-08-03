plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)

    //jacoco
    id("jacoco")
}

group = "com"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    //usei o gerador do ktor
    //https://start.ktor.io/settings
    //instalei o serialization,koin,routing
    //    Routing
    //
    //    Content Negotiation
    //
    //    Kotlinx.serialization
    //
    //    Static Content
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)


    //ORM ==> exposed
    implementation(libs.h2)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.date.time)

    //POSTGRES
    implementation(libs.postgresql)

    //FLYWAY
    implementation(libs.flyway)
    implementation(libs.flyway.postgresql)

    //AUTH
    implementation(libs.auth)
    implementation(libs.auth.jwt)

    //Hashing Irreversible
    implementation(libs.hashing)

    //Swagger Generator
    //https://smiley4.github.io/ktor-openapi-tools/latest/swaggerui/getting_started/#add-dependency
    implementation(libs.ktor.openapi)
    implementation(libs.ktor.openapi.swagger)

    //Cors
    implementation(libs.ktor.server.cors)

    //Validation Request
    implementation(libs.ktor.request.validation)
    implementation(libs.ktor.status.page)

    //test
    testImplementation(libs.kotlin.test)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.content.negotiation)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit)
    testImplementation(libs.mockk.test)
    testImplementation(libs.junit.jupiter.engine.test)
    testImplementation(libs.junit.jupiter.test)
    testImplementation(libs.ktor.server.testing)
}


//task para jacoco
jacoco {
    toolVersion = "0.8.12"
}


tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)


    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }

    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                    "**/dto/**",
                    "**/model/**",
                    "**/exception/**",
                    "**/plugins/**",
                    "**/util/mappers/**",
                    "**/*ApplicationKt.class"
                )
            }
        })
    )
    sourceDirectories.setFrom(files("$projectDir/src/main/kotlin"))

}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.register("jacocoTestCoverage") {
    group = "Verification"
    description = "Generates JaCoCo test coverage reports for the test source set."

    dependsOn(tasks.jacocoTestReport)
}




