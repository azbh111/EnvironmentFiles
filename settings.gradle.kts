rootProject.name = "EnvironmentFiles"
// 解决windows上gradle8无法需要toolchain的问题
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.4.0")
}