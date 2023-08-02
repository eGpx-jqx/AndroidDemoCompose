pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("http://maven.aliyun.com/nexus/content/groups/public/")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("http://maven.aliyun.com/nexus/content/repositories/jcenter")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("http://maven.aliyun.com/nexus/content/repositories/jcenter")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
            isAllowInsecureProtocol = true
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "AndroidDemo"
include(":app")
 