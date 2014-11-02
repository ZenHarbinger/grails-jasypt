package com.bloomhealthco.jasypt

import groovy.transform.CompileStatic

@CompileStatic
class GormEncryptedBooleanType extends JasyptConfiguredUserType<org.jasypt.hibernate4.type.EncryptedBooleanAsStringType> {
}
