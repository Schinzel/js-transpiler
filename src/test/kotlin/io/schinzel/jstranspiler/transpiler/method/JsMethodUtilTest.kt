package io.schinzel.jstranspiler.transpiler.method

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class JsMethodUtilTest {

    //------------------------------------------------------------------------
    // getMethodName
    //------------------------------------------------------------------------

    @Test
    fun `getMethodName _ get lastName _ getLastName`() {
        val actual = JsMethodUtil.getMethodName("get", "lastName")
        val expected = "getLastName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMethodName _ get last_name _ getLastName`() {
        val actual = JsMethodUtil.getMethodName("get", "last_name")
        val expected = "getLastName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMethodName _ get name _ getName`() {
        val actual = JsMethodUtil.getMethodName("get", "name")
        val expected = "getName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMethodName _ get petsAverageAge _ getPetsAverageAge`() {
        val actual = JsMethodUtil.getMethodName("get", "petsAverageAge")
        val expected = "getPetsAverageAge"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getMethodName _ get pets_average_age _ getPetsAverageAge`() {
        val actual = JsMethodUtil.getMethodName("get", "pets_average_age")
        val expected = "getPetsAverageAge"
        assertThat(actual).isEqualTo(expected)
    }


    //------------------------------------------------------------------------
    // getMethodName
    //------------------------------------------------------------------------

    @Test
    fun `getJsPropertyName _ lastName _ lastName`() {
        val actual = JsMethodUtil.getJsPropertyName("lastName")
        val expected = "lastName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getJsPropertyName _ last_name _ lastName`() {
        val actual = JsMethodUtil.getJsPropertyName("last_name")
        val expected = "lastName"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getJsPropertyName _ name _ name`() {
        val actual = JsMethodUtil.getJsPropertyName("name")
        val expected = "name"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getJsPropertyName _ petsAverageAge _ petsAverageAge`() {
        val actual = JsMethodUtil.getJsPropertyName("petsAverageAge")
        val expected = "petsAverageAge"
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getJsPropertyName _ pets_average_age _ petsAverageAge`() {
        val actual = JsMethodUtil.getJsPropertyName("pets_average_age")
        val expected = "petsAverageAge"
        assertThat(actual).isEqualTo(expected)
    }

}