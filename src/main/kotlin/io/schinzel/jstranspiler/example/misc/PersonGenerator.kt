package io.schinzel.jstranspiler.example.misc

import io.schinzel.jstranspiler.example.dataclasses.dir1.Address
import io.schinzel.jstranspiler.example.dataclasses.dir1.Pet
import io.schinzel.jstranspiler.example.dataclasses.dir1.Species
import io.schinzel.jstranspiler.example.dataclasses.dir2.Person

/**
 * Purpose of this file is generate a Person object with data
 */
class PersonGenerator {
    companion object {
        fun generatePerson(): Person {
            val address = Address("Funky street 25", "12345", "Malmö", "Sweden")
            val nicknames = listOf("Svenne", "Henke")
            val luckyNumbers = listOf(5, 25)
            val pet1 = Pet("Rufus", Species.DOG)
            val pet2 = Pet("Tiger", Species.CAT)
            val pets = listOf(pet1, pet2)
            return Person("Henrik", "Svensson", 25,
                    250000, 1.89f, true, address,
                    nicknames, luckyNumbers, pets)
        }
    }
}