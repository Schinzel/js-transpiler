package io.schinzel.jstranspiler.example.misc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.Instant
import java.time.format.DateTimeFormatterBuilder

class Serialization {

    /**
     * Class to serialize an instant to String with format "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     */
    private class ExampleSerializer
        : InstantSerializer(INSTANCE, false, DateTimeFormatterBuilder().appendInstant(3).toFormatter())

    companion object {

        // add Module which can serialize instant to String with format "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private val serializer: ObjectMapper  = jacksonObjectMapper()
                .registerModule(JavaTimeModule().addSerializer(Instant::class.java, ExampleSerializer()))

        /**
         *
         * @param anObject
         * @return Argument object as JSON string
         */
        fun <T> objectToJsonString(anObject: T): String =
                serializer.writeValueAsString(anObject)


        /**
         * JSON string
         * Example: Serialization.jsonStringToObject({"id":0,"phone":"+15553332222","created":"2019-05-29T12:34:56.789Z"}, MyDBO::class.java)
         * output: MyDBO(id=0, phone="+15553332222", created=Instant.of("2019-05-31T12:34:56.789Z"))
         *
         * @param value string value to make object from
         * @param clazz object Java class
         * @return serialized object
         */
        fun <T> jsonStringToObject(value: String, clazz: Class<T>): T {
            if (value.isEmpty()) {
                throw RuntimeException("Problems when creating object of class ${clazz.simpleName} from json string. Json string was empty")
            }
            return serializer.readValue(value, clazz)
        }
    }
}
