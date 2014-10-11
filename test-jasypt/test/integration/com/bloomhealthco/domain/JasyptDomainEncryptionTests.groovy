package com.bloomhealthco.domain

import grails.test.GrailsUnitTestCase
import groovy.sql.Sql

class JasyptDomainEncryptionTests extends GrailsUnitTestCase {
    def dataSource
    def sessionFactory
    def grailsApplication

    def CORRELATION_ID = "ABC123"

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testStringStringEncryption() {
        testPropertyAsStringEncryption('firstName', 'FIRST_NAME', 'foo')
    }

    void testDateStringEncryption() {
        testPropertyAsStringEncryption('birthDate', 'BIRTH_DATE', new Date(1970, 2, 3))
    }

    void testDoubleStringEncryption() {
        testPropertyAsStringEncryption('latitude', 'LATITUDE', 85.0d)
    }

    void testBooleanStringEncryption() {
        testPropertyAsStringEncryption('hasInsurance', 'HAS_INSURANCE', true)
    }

    void testFloatStringEncryption() {
        testPropertyAsStringEncryption('cashBalance', 'CASH_BALANCE', 123.45f)
    }

    void testShortStringEncryption() {
        testPropertyAsStringEncryption('weight', 'WEIGHT', 160)
    }

    void testIntegerStringEncryption() {
        testPropertyAsStringEncryption('height', 'HEIGHT', 74)
    }

    void testLongStringEncryption() {
        testPropertyAsStringEncryption('patientId', 'PATIENT_ID', 1234567890)
    }

    void testByteStringEncryption() {
        testPropertyAsStringEncryption('biteMe', 'BITE_ME', 2)
    }

    void testSaltingEncryptsSameValueDifferentlyEachTime() {
        def originalPatient = new Patient(firstName: "foo", lastName: "foo", correlationId: CORRELATION_ID)
		originalPatient.save(failOnError: "true")

        withPatientForCorrelationId(CORRELATION_ID) { patient, rawPatient ->
            assertEquals "foo", patient.firstName
            assertEquals "foo", patient.lastName
            assertTrue "foo" != rawPatient.FIRST_NAME
            assertTrue "foo" != rawPatient.LAST_NAME
            assertTrue rawPatient.FIRST_NAME != rawPatient.LAST_NAME
        }
    }

    void testEncryptionWithLongNamesFit() {
        def LONG_NAME_256 = "ABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOPABCDEFGHIJKLMNOP"

        (1..256).each { val ->
            def firstName = LONG_NAME_256.substring(0, val)
            Patient.build(firstName: firstName, correlationId: val)
            
            withPatientForCorrelationId(val) { patient, rawPatient ->
                assertNotNull patient
                assertEquals firstName, patient.firstName
                // Bouncy Castle AES block encryption encrypts 256 character string in 384 characters
                assertTrue rawPatient.FIRST_NAME.size() <= 384
            }
        }
    }

    void testPropertyAsStringEncryption(property, rawProperty, value) {
        def originalPatient = new Patient(correlationId: CORRELATION_ID)
        originalPatient."$property" = value
        originalPatient.save(failOnError: "true")

        withPatientForCorrelationId(CORRELATION_ID) { patient, rawPatient ->
            assertEquals value, patient."$property"
            def rawPropertyValue = rawPatient."$rawProperty"
            assertTrue value.toString() != rawPropertyValue
            assertTrue rawPropertyValue.endsWith("=")
        }
    }

    def withPatientForCorrelationId(correlationId, closure) {
        def patient = Patient.findByCorrelationId(correlationId)
        assertNotNull patient
        retrieveRawPatientFromDatabase(correlationId) { rawPatient ->
            assertNotNull rawPatient
            closure(patient, rawPatient)
        }
    }

    def retrieveRawPatientFromDatabase(correlationId, closure) {
        new Sql(dataSource).with { db ->
            try {
                def result = db.firstRow("SELECT * FROM patient where correlation_id = $correlationId")
                closure(result)
            } finally {
                db.close()
            }
        }
    }
}
