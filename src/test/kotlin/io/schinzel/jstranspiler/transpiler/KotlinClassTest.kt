package io.schinzel.jstranspiler.transpiler

import com.fasterxml.jackson.annotation.JsonIgnore
import io.schinzel.basic_utils_kotlin.println
import io.schinzel.jstranspiler.transpiler.util.JavaClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinClassTest {

    private data class MyDBO(
            val firstValue: String,
            @JsonIgnore
            val secondValue: String,
            @JsTranspiler_CreateSetter
            val thirdValue: String,
            @JsTranspiler_CreateSetter
            @JsonIgnore
            val fourthValue: String)

    @Test
    fun constructorInitsJsCode_PropertyHasNoAnnotation_PropertyIsInConstructor() {
        val constructorJsCode = KotlinClass(MyDBO::class).constructorInitsJsCode
        assertThat(constructorJsCode).contains("firstValue")
    }

    @Test
    fun constructorInitsJsCode_PropertyIsIgnored_PropertyIsNotInConstructor() {
        val constructorJsCode = KotlinClass(MyDBO::class).constructorInitsJsCode
        assertThat(constructorJsCode).doesNotContain("secondValue")
    }

    @Test
    fun gettersJsCode_PropertyHasNoAnnotation_IsAGetter() {
        val getterJsCode = KotlinClass(MyDBO::class).gettersJsCode
        assertThat(getterJsCode).contains("firstValue")
    }

    @Test
    fun gettersJsCode_PropertyIsIgnored_PropertyIsNotAGetter() {
        val getterJsCode = KotlinClass(MyDBO::class).gettersJsCode
        assertThat(getterJsCode).doesNotContain("secondValue")
    }


    @Test
    fun settersJsCode_PropertyIsIgnored_PropertyIsNotASetter() {
        val settersJsCode = KotlinClass(MyDBO::class).settersJsCode
        assertThat(settersJsCode).doesNotContain("firstValue")
    }

    @Test
    fun settersJsCode_PropertyIsAnnotatedWithSetterAnnotation_PropertyIsASetter() {
        val settersJsCode = KotlinClass(MyDBO::class).settersJsCode
        assertThat(settersJsCode).contains("thirdValue")
    }

    @Test
    fun settersJsCode_PropertyIsAnnotatedWithSetterAnnotationAndJsonIgnore_PropertyIsNotASetter() {
        val settersJsCode = KotlinClass(MyDBO::class).settersJsCode
        assertThat(settersJsCode).doesNotContain("fourthValue")
    }


}