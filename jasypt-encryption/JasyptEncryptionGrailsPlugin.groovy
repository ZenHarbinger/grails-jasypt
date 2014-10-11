import java.security.Security
import org.bouncycastle.jce.provider.BouncyCastleProvider

class JasyptEncryptionGrailsPlugin {
    static {
        // adds the BouncyCastle provider to Java so we don't need to manually modify our java install
        // be sure that you've installed the Java Cryptography Extension (JCE) on the Sun website
        // so that you have "unlimited" (rather than "strong", which isn't really strong) encryption
        // if you're on OSX, this should be there by default.  On other platforms, you'll need to
        // update the jars in your $JAVA_HOME/lib/security with the updated JCE jars
        Security.addProvider(new BouncyCastleProvider());
    }

    def version = "1.0.1"

    def grailsVersion = "2.0.0 > *"

    def dependsOn = [:]

    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Ted Naleid"
    def authorEmail = "contact@naleid.com"
    def title = "Jasypt Encryption"
    def description = '''\\
Grails integration with Jasypt, allows easy encryption of information, including Hibernate/GORM integration.
'''

    def developers = [
            [ name: "Ted Naleid" ],
            [ name: "Jon Palmer" ]
    ]

    def documentation = "http://grails.org/plugin/jasypt-encryption"

    def issueManagement = [ system: 'bitbucket', url: 'https://bitbucket.org/tednaleid/grails-jasypt/issues' ]

    def doWithWebDescriptor = { xml ->

    }

    def doWithSpring = {

    }

    def doWithDynamicMethods = { ctx ->

    }

    def doWithApplicationContext = { applicationContext ->

    }

    def onChange = { event ->

    }

    def onConfigChange = { event ->

    }
}
