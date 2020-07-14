package io.schinzel.jstranspiler.example

import com.atexpose.AtExpose
import com.atexpose.dispatcherfactories.CliFactory
import com.atexpose.dispatcherfactories.WebServerBuilder
import io.schinzel.jstranspiler.example.dataclasses.dir2.Person
import io.schinzel.jstranspiler.example.misc.API
import io.schinzel.jstranspiler.example.misc.PersonGenerator

/**
 * The purpose of this file is to start a web server so that user can run a sample that
 * demonstrate the capabilities of this project
 */
fun main() {
    generateJavaScriptClasses()
    val person: Person = PersonGenerator.generatePerson()
    WebServer(person).start()
    println("Web server started on port 5555")
}

class WebServer(person: Person) {
    val api = API(person)

    fun start(): WebServer {
        AtExpose.create()
                //Expose an instance of class Web
                .expose(api)
                //Start web server
                .start(WebServerBuilder.create()
                        .webServerDir("mysite")
                        .cacheFilesInRAM(false)
                        .build())
                //Start command line interface
                .start(CliFactory.create())
        return this
    }
}


