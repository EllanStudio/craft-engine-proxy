import net.momirealms.netty

plugins {
    id("craft-engine-proxy.run-velocity")
    id("net.kyori.blossom") version "2.2.0"
}

dependencies {
    implementation(project(":common"))
    netty(project)
    // Platform
    compileOnly("com.velocitypowered:velocity-api:${rootProject.properties["velocity_version"]}")
    compileOnly("com.github.ben-manes.caffeine:caffeine:${rootProject.properties["caffeine_version"]}")
    annotationProcessor("com.velocitypowered:velocity-api:${rootProject.properties["velocity_version"]}")
    // Reflection
    compileOnly(files("${rootProject.rootDir}/libs/jni-internal-lookup-1.9.jar"))
    compileOnly("net.momirealms:sparrow-reflection:${rootProject.properties["sparrow_reflection_version"]}")
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", rootProject.properties["project_version"] as String)
            }
        }
    }
}

tasks {
    shadowJar {
        relocation.applyProxy(this)
        archiveFileName = "${rootProject.name}-velocity-plugin-${rootProject.properties["project_version"]}.jar"
        destinationDirectory.set(file("$rootDir/target"))
    }
}

artifacts {
    implementation(tasks.shadowJar)
}
