package io.schinzel.jstranspiler.transpiler

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KotlinEnum_toJavaScript_Test{
    @Suppress("unused")
    private enum class TestSpecies(val lifeSpan: Int, val alignment: String) {
        CAT(16, "Chaotic Evil"), DOG(13, "Neutral Good")
    }

    @Test
    fun `toJavaScript TestSpices CorrectJavaScript`(){
        val javaScript = KotlinEnum(TestSpecies::class.java.kotlin).toJavaScript()
        val correctJavaScript = """
            /**
             * @typedef {{name: string, lifeSpan: number, alignment: string}} TestSpecies
             */
            export const TestSpecies = Object.freeze({
                CAT: {name: 'CAT', lifeSpan: 16, alignment: 'Chaotic Evil'},
                DOG: {name: 'DOG', lifeSpan: 13, alignment: 'Neutral Good'}
            });
            
            
        """.trimIndent()
        assertThat(javaScript).isEqualTo(correctJavaScript)
    }
}