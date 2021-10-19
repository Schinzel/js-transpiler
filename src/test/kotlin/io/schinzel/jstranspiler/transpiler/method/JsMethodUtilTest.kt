package io.schinzel.jstranspiler.transpiler.method

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class JsMethodUtilTest {

    //------------------------------------------------------------------------
    // getMethodName
    //------------------------------------------------------------------------

    @Test
    fun `getMethodName | get lastName | getLastName`() {
        val actual = JsMethodUtil.getMethodName("get", "lastName")
        val expected = "getLastName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMethodName | get last_name | getLastName`() {
        val actual = JsMethodUtil.getMethodName("get", "last_name")
        val expected = "getLastName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMethodName | get name | getName`() {
        val actual = JsMethodUtil.getMethodName("get", "name")
        val expected = "getName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMethodName | get petsAverageAge | getPetsAverageAge`() {
        val actual = JsMethodUtil.getMethodName("get", "petsAverageAge")
        val expected = "getPetsAverageAge"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMethodName | get pets_average_age | getPetsAverageAge`() {
        val actual = JsMethodUtil.getMethodName("get", "pets_average_age")
        val expected = "getPetsAverageAge"
        assertThat(actual).isEqualTo(expected)
    }


    //------------------------------------------------------------------------
    // getMethodName
    //------------------------------------------------------------------------

    @Test
    fun `getJsPropertyName | lastName | lastName`() {
        val actual = JsMethodUtil.getJsPropertyName("lastName")
        val expected = "lastName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getJsPropertyName | last_name | lastName`() {
        val actual = JsMethodUtil.getJsPropertyName("last_name")
        val expected = "lastName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getJsPropertyName | name | name`() {
        val actual = JsMethodUtil.getJsPropertyName("name")
        val expected = "name"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getJsPropertyName | petsAverageAge | petsAverageAge`() {
        val actual = JsMethodUtil.getJsPropertyName("petsAverageAge")
        val expected = "petsAverageAge"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getJsPropertyName | pets_average_age | petsAverageAge`() {
        val actual = JsMethodUtil.getJsPropertyName("pets_average_age")
        val expected = "petsAverageAge"
        assertThat(actual).isEqualTo(expected)
    }

}