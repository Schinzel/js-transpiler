package io.schinzel.jstranspiler.transpiler

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.Instant
import java.time.LocalDate


class JsConstructorInitTest {

    private data class IntegerClass(val value: Int)
    private data class FloatClass(val value: Float)
    private data class DoubleClass(val value: Double)
    private data class StringClass(val value: String)
    private data class BooleanClass(val value: Boolean)
    private data class InstantClass(val value: Instant)
    private data class LocalDateClass(val value: LocalDate)

    @Test
    fun toJavaScript_int_parseInt() {
        assertThat(JsConstructorInit(IntegerClass::value).toJavaScript()).endsWith("this.value = parseInt(json.value);")
    }

    @Test
    fun toJavaScript_floatValueEndWith_parseFloat() {
        assertThat(JsConstructorInit(FloatClass::value).toJavaScript()).endsWith("this.value = parseFloat(json.value);")
    }

    @Test
    fun toJavaScript_double_parseFloat() {
        assertThat(JsConstructorInit(DoubleClass::value).toJavaScript()).endsWith("this.value = parseFloat(json.value);")
    }

    @Test
    fun toJavaScript_string_assignValue() {
        assertThat(JsConstructorInit(StringClass::value).toJavaScript()).endsWith("this.value = json.value;")
    }

    @Test
    fun toJavaScript_boolean_assignValue() {
        assertThat(JsConstructorInit(BooleanClass::value).toJavaScript()).endsWith("this.value = json.value;")
    }

    @Test
    fun toJavaScript_instant_newDate() {
        assertThat(JsConstructorInit(InstantClass::value).toJavaScript()).endsWith("this.value = new Date(json.value);")
    }

    @Test
    fun toJavaScript_localDate_newDate() {
        assertThat(JsConstructorInit(LocalDateClass::value).toJavaScript()).endsWith("this.value = json.value;")
    }
}