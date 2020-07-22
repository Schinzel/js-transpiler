package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.transpiler.KotlinEnum.Companion.getEnumValueProperties
import io.schinzel.jstranspiler.transpiler.KotlinEnum.Companion.getListOfPropertyTypes
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinEnum_getEnumValueProperties_Test {

    @Suppress("unused")
    private enum class TestSpecies(val lifeSpan: Int, val alignment: String) {
        CAT(16, "Chaotic Evil"), DOG(13, "Neutral Good")
    }

    private val propertyTypes = getListOfPropertyTypes(TestSpecies::class.java.kotlin)

    @Test
    fun `Size TestSpecies 2`() {
        val enumValueProperties = getEnumValueProperties(TestSpecies::class.java.kotlin, propertyTypes)
        assertThat(enumValueProperties.size).isEqualTo(2)
    }

    @Test
    fun `FirstEnumValueFirstProperty TestSpecies CAT`() {
        val enumValueProperties = getEnumValueProperties(TestSpecies::class.java.kotlin, propertyTypes)
        assertThat(enumValueProperties[0][0].value).isEqualTo("CAT")
    }

    @Test
    fun `FirstEnumValueSecondProperty TestSpecies 16`() {
        val enumValueProperties = getEnumValueProperties(TestSpecies::class.java.kotlin, propertyTypes)
        assertThat(enumValueProperties[0][1].value).isEqualTo("16")
    }

    @Test
    fun `FirstEnumValueThirdProperty TestSpecies Chaotic Evil`() {
        val enumValueProperties = getEnumValueProperties(TestSpecies::class.java.kotlin, propertyTypes)
        assertThat(enumValueProperties[0][2].value).isEqualTo("Chaotic Evil")
    }

}