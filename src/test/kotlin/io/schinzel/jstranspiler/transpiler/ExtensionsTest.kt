package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.transpiler.util.JavaClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.reflect.full.memberProperties

class ExtensionsTest {

    @Test
    fun isEnum_JavaEnum_true(){
        val property = JavaClass::class.memberProperties.elementAt(9)
        assertThat(property.isEnum()).isTrue()
    }

    @Test
    fun isEnum_JavaString_false(){
        val property = JavaClass::class.memberProperties.elementAt(0)
        assertThat(property.isEnum()).isFalse()
    }

    @Test
    fun isEnum_JavaEnumList_false(){
        val property = JavaClass::class.memberProperties.elementAt(10)
        assertThat(property.isEnum()).isFalse()
    }

    @Test
    fun isList_JavaEnum_false(){
        val property = JavaClass::class.memberProperties.elementAt(9)
        assertThat(property.isList()).isFalse()
    }

    @Test
    fun isList_JavaStringList_true(){
        val property = JavaClass::class.memberProperties.elementAt(7)
        assertThat(property.isList()).isTrue()
    }

    @Test
    fun isList_JavaEnumList_true(){
        val property = JavaClass::class.memberProperties.elementAt(10)
        assertThat(property.isList()).isTrue()
    }

    @Test
    fun isList_JavaString_false(){
        val property = JavaClass::class.memberProperties.elementAt(0)
        assertThat(property.isList()).isFalse()
    }

    @Test
    fun isListOfPrimitiveDataType_JavaString_false(){
        val property = JavaClass::class.memberProperties.elementAt(0)
        assertThat(property.isListOfPrimitiveDataType()).isFalse()
    }

    @Test
    fun isListOfPrimitiveDataType_JavaStringList_true(){
        val property = JavaClass::class.memberProperties.elementAt(7)
        assertThat(property.isListOfPrimitiveDataType()).isTrue()
    }

    @Test
    fun isListOfPrimitiveDataType_JavaClassList_false(){
        val property = JavaClass::class.memberProperties.elementAt(8)
        assertThat(property.isListOfPrimitiveDataType()).isFalse()
    }

    @Test
    fun isListOfPrimitiveDataType_JavaEnumList_false(){
        val property = JavaClass::class.memberProperties.elementAt(10)
        assertThat(property.isListOfPrimitiveDataType()).isFalse()
    }

    @Test
    fun isListOfPrimitiveDataType_JavaIntegerList_true(){
        val property = JavaClass::class.memberProperties.elementAt(11)
        assertThat(property.isListOfPrimitiveDataType()).isTrue()
    }
}



