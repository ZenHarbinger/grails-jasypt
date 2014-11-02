import java.security.Security

import org.bouncycastle.jce.provider.BouncyCastleProvider

class JasyptEncryptionGrailsPlugin {
    static {
        // adds the BouncyCastle provider to Java so we don't need to manually modify our java install
        // be sure that you've installed the Java Cryptography Extension (JCE) on the Sun website
        // so that you have "unlimited" (rather than "strong", which isn't really strong) encryption
        // if you're on OSX, this should be there by default.  On other platforms, you'll need to
        // update the jars in your $JAVA_HOME/lib/security with the updated JCE jars
        Security.addProvider new BouncyCastleProvider()
    }

    def version = "1.3.1"
    def grailsVersion = "2.4 > *"
    def title = "Jasypt Encryption"
    def description = 'Integration with Jasypt, allows easy encryption of information including Hibernate/GORM integration'
    def license = "APACHE"
    def developers = [
            [name: "Ted Naleid", email: 'contact@naleid.com'],
            [name: "Jon Palmer"],
            [name: "Dan Tanner", email: 'dan@dantanner.com'],
    ]
    def documentation = "http://grails.org/plugin/jasypt-encryption"
    def issueManagement = [system: "GITHUB", url: "https://github.com/dtanner/grails-jasypt/issues"]
    def scm = [url: "https://github.com/dtanner/grails-jasypt"]
}
