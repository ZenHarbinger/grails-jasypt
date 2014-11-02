grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        runtime 'org.jasypt:jasypt:1.9.2'
        runtime 'org.jasypt:jasypt-hibernate4:1.9.2'
        runtime 'org.bouncycastle:bcprov-jdk16:1.46'
    }

    plugins {
        build ':release:3.0.1', ':rest-client-builder:2.0.3', {
            export = false
        }

        runtime ':hibernate4:4.3.5.5', {
            export = false
        }
    }
}
