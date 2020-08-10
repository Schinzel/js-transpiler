package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.transpiler.util.JavaClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinClass_JavaClass_Test {

    @Test
    fun settersJsCode_PropertyHasAnnotationCreateSetter_PropertyHasSetter() {
        val settersJsCode = KotlinClass(JavaClass::class.java).settersJsCode
        assertThat(settersJsCode).contains("myInt")
    }

    @Test
    fun settersJsCode_PropertyHasNotAnnotationCreateSetter_PropertyHasSetter() {
        val settersJsCode = KotlinClass(JavaClass::class.java).settersJsCode
        assertThat(settersJsCode).doesNotContain("myString")
    }

    @Test
    fun gettersJsCode_PropertyIsIgnored_PropertyIsNotAGetter() {
        val getterJsCode = KotlinClass(JavaClass::class.java).gettersJsCode
        assertThat(getterJsCode).doesNotContain("mySecondString")
    }

    @Test
    fun constructorInitsJsCode_PropertyIsIgnored_PropertyIsNotInConstructor() {
        val constructorJsCode = KotlinClass(JavaClass::class.java).constructorInitsJsCode
        assertThat(constructorJsCode).doesNotContain("mySecondString")
    }

    @Test
    fun constructorInitsJsCode_PropertyHasNoAnnotation_PropertyIsInConstructor() {
        val constructorJsCode = KotlinClass(JavaClass::class.java).constructorInitsJsCode
        assertThat(constructorJsCode).contains("myString")
    }

    @Test
    fun gettersJsCode_PropertyHasNoAnnotation_PropertyHasAGetter() {
        val getterJsCode = KotlinClass(JavaClass::class.java).gettersJsCode
        assertThat(getterJsCode).contains("myString")
    }


}