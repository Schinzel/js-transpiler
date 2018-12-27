package io.schinzel.jstranspiler

import io.schinzel.jstranspiler.transpiler.JsConstructorInit
import io.schinzel.jstranspiler.transpiler.KotlinDataType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


/**
 * Created by Schinzel on 2018-12-01
 */
class JsConstructorInitTest {
    @Test
    fun getJavaScript_FirstNameAndString_CorrectString() {
        val constructorInit = JsConstructorInit("firstName", KotlinDataType("String"), false)
                .toJavaScript()
        assertThat(constructorInit).isEqualTo(
                """            /**
             * @private
             */
            this.firstName = new String(json.firstName);""")
    }
}