package io.schinzel.jstranspiler.example.misc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class Serialization {

    companion object {

        private val serializer: ObjectMapper = jacksonObjectMapper()
        //.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);

        /**
         *
         * @param anObject
         * @return Argument object as JSON string
         */
        fun <T> objectToJsonString(anObject: T): String =
                serializer.writeValueAsString(anObject)


        /**
         * JSON string
         * Example: Serialization.jsonStringToObject({"id":0,"getDataTypeName":"Santa","phone":"+15553332222"}, MyDBO::class.java)
         * output: MyDBO(id=0, getDataTypeName="Santa", phone="+15553332222")
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
