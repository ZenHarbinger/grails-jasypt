grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"
grails.project.work.dir = "target/work"

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {

    inherits("global") {
    }

    log "warn"

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
    }

    plugins {
        runtime ":hibernate4:4.3.5.5"
        build ":tomcat:7.0.55"
        test ":build-test-data:2.2.1"
    }
}

grails.plugin.location.jasypt = "../jasypt-encryption"
