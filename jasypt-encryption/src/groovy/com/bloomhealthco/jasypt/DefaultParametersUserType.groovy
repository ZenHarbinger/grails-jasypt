package com.bloomhealthco.jasypt

import java.sql.PreparedStatement
import java.sql.ResultSet
import org.hibernate.usertype.ParameterizedType
import org.hibernate.usertype.UserType

/**
 * A wrapper class that supports providing default parameters to UserTypes
 * implement the getDefaultParameters to provide the configuration you want
 *
 * We use composition so that we can add this behavior to otherwise declared final UserTypes.
 * We'd love to use a Mixin but Hibernate's reflection utils don't play nice with Groovys ryntime mixins
 * @param < T > the existing Parameterized UserType we want to add the default parmaters to
 */
abstract class DefaultParametersUserType<T extends UserType & ParameterizedType> implements UserType, ParameterizedType {

    // Nifty little reflection trick to figure out the actual class the subclass provides
    // UserTypes have to have a default parameterless constructor so we're safe to call it here
    private final T innerType = this.class.genericSuperclass.actualTypeArguments[0].newInstance()

    protected T getInnerType() {
        innerType
    }

    int[] sqlTypes() {
        innerType.sqlTypes()
    }

    Class returnedClass() {
        innerType.returnedClass()
    }

    boolean equals(final Object x, final Object y) {
        innerType.equals(x, y)
    }

    int hashCode(final Object x) {
        innerType.hashCode(x)
    }

    Object nullSafeGet(final ResultSet resultSet, final String[] names, final Object owner) {
        innerType.nullSafeGet(resultSet, names, owner)
    }

    void nullSafeSet(final PreparedStatement preparedStatement, Object value, int index) {
        innerType.nullSafeSet(preparedStatement, value, index)
    }

    Object deepCopy(final Object value) {
        innerType.deepCopy(value)
    }

    boolean isMutable() {
        innerType.isMutable()
    }

    Serializable disassemble(final Object value) {
        innerType.disassemble(value)
    }

    Object assemble(final Serializable cached, final Object owner) {
        innerType.assemble(cached, owner)
    }

    Object replace(Object original, Object target, Object owner) {
        innerType.replace(original, target, owner)
    }

    void setParameterValues(final Properties properties) {
        def params = defaultParameters + (properties ?: [:]) as Properties
        innerType.setParameterValues(params)
    }

    abstract Map getDefaultParameters()
}
