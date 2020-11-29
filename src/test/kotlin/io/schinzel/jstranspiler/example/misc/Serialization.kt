package io.schinzel.jstranspiler.example.misc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class Serialization {

    companion object {
        private val serializer: ObjectMapper = jacksonObjectMapper()

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