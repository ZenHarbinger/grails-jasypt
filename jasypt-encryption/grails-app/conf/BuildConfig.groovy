grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
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
        runtime 'org.jasypt:jasypt:1.9.2'
        runtime 'org.jasypt:jasypt-hibernate3:1.9.2'
        runtime 'org.bouncycastle:bcprov-jdk16:1.46'
    }

    plugins {
        build ':release:3.0.1', ':rest-client-builder:1.0.3', {
            export = false
        }
        runtime ":hibernate:3.6.10.13"
    }
}
