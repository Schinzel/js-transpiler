package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.transpiler.util.JavaClassUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.reflect.full.memberProperties

class JsConstructorInit_jsCodeCast_JavaClass_Test {

    @Test
    fun stringProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(0)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myString;")
    }

    @Test
    fun intProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(1)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("parseInt(json.myInt);")
    }

    @Test
    fun integerProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(2)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("parseInt(json.myInteger);")
    }

    @Test
    fun primitiveBooleanProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(3)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myPrimitiveBoolean;")
    }

    @Test
    fun booleanProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(4)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myBoolean;")
    }


    @Test
    fun instantProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(5)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("new Date(json.myInstant);")
    }

    @Test
    fun classProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(6)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("new MyOtherJavaClass(json.myClass);")
    }

    @Test
    fun stringListProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(7)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myStringList;")
    }


    @Test
    fun myOtherJavaClassListProperty() {
        val property = JavaClassUtil::class.memberProperties.elementAt(8)
        val actual = JsConstructorInit.jsCodeCast(property)
        assertThat(actual).isEqualTo("json.myMyOtherJavaClassList.map(x => new MyOtherJavaClass(x));")
    }

}