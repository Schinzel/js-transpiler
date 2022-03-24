package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.transpiler.util.JavaClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.reflect.full.memberProperties

class JsConstructorInit_jsCodeCast_JavaClass_Test {

    @Test
    fun stringProperty() {
        val property = JavaClass::class.memberProperties.elementAt(0)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myString;")
    }

    @Test
    fun intProperty() {
        val property = JavaClass::class.memberProperties.elementAt(1)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("parseInt(json.myInt);")
    }

    @Test
    fun integerProperty() {
        val property = JavaClass::class.memberProperties.elementAt(2)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("parseInt(json.myInteger);")
    }

    @Test
    fun primitiveBooleanProperty() {
        val property = JavaClass::class.memberProperties.elementAt(3)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myPrimitiveBoolean;")
    }

    @Test
    fun booleanProperty() {
        val property = JavaClass::class.memberProperties.elementAt(4)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myBoolean;")
    }


    @Test
    fun instantProperty() {
        val property = JavaClass::class.memberProperties.elementAt(5)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("new Date(json.myInstant);")
    }

    @Test
    fun classProperty() {
        val property = JavaClass::class.memberProperties.elementAt(6)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("new MyOtherJavaClass(json.myClass);")
    }

    @Test
    fun stringListProperty() {
        val property = JavaClass::class.memberProperties.elementAt(7)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myStringList;")
    }

    @Test
    fun javaClassListProperty() {
        val property = JavaClass::class.memberProperties.elementAt(8)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myMyOtherJavaClassList.map(x => new MyOtherJavaClass(x));")
    }

    @Test
    fun javaEnumProperty() {
        val property = JavaClass::class.memberProperties.elementAt(9)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myJavaEnum;")
    }

    @Test
    fun javaEnumListProperty() {
        val property = JavaClass::class.memberProperties.elementAt(10)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myJavaEnumList.map(x => JavaEnum[x].name);")
    }

    @Test
    fun localDateProperty() {
        val property = JavaClass::class.memberProperties.elementAt(13)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myLocalDate;")
    }
}