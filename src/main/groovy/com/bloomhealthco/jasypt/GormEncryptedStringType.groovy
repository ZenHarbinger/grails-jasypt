package com.bloomhealthco.jasypt

import groovy.transform.CompileStatic

@CompileStatic
class GormEncryptedStringType extends JasyptConfiguredUserType<org.jasypt.hibernate4.type.EncryptedStringType> {
}
