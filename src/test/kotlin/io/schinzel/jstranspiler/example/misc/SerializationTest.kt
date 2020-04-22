package io.schinzel.jstranspiler.example.misc

import com.fasterxml.jackson.annotation.JsonIgnore
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.Instant

class SerializationTest {

    private data class MyDBO(
            val id: Int,
            val phone: String,
            val created: Instant,
            @JsonIgnore
            val anyValue: String = "my value")

    @Test
    fun objectToJsonString_parsedToJsonString_correct() {
        val dbo = MyDBO(42, "123456789", Instant.parse("2019-05-29T12:34:56.789Z"))
        assertThat(Serialization.objectToJsonString(dbo)).isEqualTo("""{"id":42,"phone":"123456789","created":"2019-05-29T12:34:56.789Z"}""")
    }

    @Test
    fun jsonToObject_idParsedAsInt_is42() {
        val dbo = Serialization
                .jsonStringToObject(
                        """{"id":42,"phone":"123456789","created":"2019-05-29T12:34:56.789Z"}""",
                        MyDBO::class.java)
        assertThat(dbo.id).isEqualTo(42)
    }

    @Test
    fun jsonToObject_phoneParsedAsString_is123456789() {
        val dbo = Serialization
                .jsonStringToObject(
                        """{"id":42,"phone":"123456789","created":"2019-05-29T12:34:56.789Z"}""",
                        MyDBO::class.java)
        assertThat(dbo.phone).isEqualTo("123456789")
    }

    @Test
    fun objectToJsonString_jsonIgnoreOnPropertyAnyValue_PropertyAnyValueNotPresentInJson(){
        val dbo = MyDBO(42, "123456789", Instant.parse("2019-05-29T12:34:56.789Z"))
        val jsonString = Serialization.objectToJsonString(dbo)
        assertThat(jsonString).doesNotContain("anyValue")
    }
}