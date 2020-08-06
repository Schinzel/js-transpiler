package io.schinzel.jstranspiler.transpiler

import io.schinzel.basic_utils_kotlin.println
import io.schinzel.basic_utils_kotlin.printlnWithPrefix
import io.schinzel.jstranspiler.example.java.JavaClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinClassJavaTest {

    private class TestClass(val age: Int, val name: String)

    @Test
    fun apa(){

        KotlinClass(JavaClass.StringClass::class).constructorInitsJsCode.printlnWithPrefix("Java")
        //KotlinClass(TestClass::class).constructorInitsJsCode.printlnWithPrefix("Kotlin")
        assertThat(4).isEqualTo(4)

    }
}