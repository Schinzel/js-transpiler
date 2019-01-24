package io.schinzel.jstranspiler.example.misc

import com.atexpose.Expose
import io.schinzel.jstranspiler.example.dataclasses.dir2.Person

/**
 * This class holds the methods accessible through web server.
 *
 */
class API(var person: Person) {


    @Expose
    fun getPerson(): String {
        return Serialization.objectToJsonString(person)
    }


    @Expose(
            arguments = ["String"],
            requiredArgumentCount = 1
    )
    fun setPerson(personAsJson: String): String {
        println("Server received the following json: $personAsJson")
        person = Serialization.jsonStringToObject(personAsJson, Person::class.java)
        println("Server created the following instance: $person")
        return "Person received"
    }

}
