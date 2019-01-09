package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.annotation.JsTranspilerSetter
import io.schinzel.jstranspiler.transpiler.method.JsGetter
import io.schinzel.jstranspiler.transpiler.method.JsSetter
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * Purpose of this class is to construct the JavaScript code for a Kotlin data class
 */
internal class KotlinClass(myClass: KClass<out Any>) : IToJavaScript {
    //The name of this class
    private val dataClassName: String = myClass.simpleName
            ?: throw RuntimeException("Problems getting class name from class")

    //JavaScript code for setting up properties in the constructor
    private val constructorInitsJsCode: String = myClass
            //Get all properties for class
            .memberProperties
            //Create list of JavaScript constructor
            .map { property ->
                JsConstructorInit(property)
            }
            //Compile list to string with all code for constructor
            .compileToJs()

    //JavaScript code for property getters
    private val gettersJsCode: String = myClass
            //Get all properties for class
            .memberProperties
            //Create list of JavaScript getters
            .map { property ->
                JsGetter(property)
            }
            //Compile list of getters to a single string
            .compileToJs()

    //JavaScript code for property setters
    private val settersJsCode: String = myClass
            //Get all properties for class
            .memberProperties
            //Filter out those that are not annotated as JavaScript setters
            .filter { property -> property.annotations.any { it is JsTranspilerSetter } }
            //Create list of JavaScript setters
            .map { property ->
                JsSetter(dataClassName, property)
            }
            .compileToJs()

    /**
     * @return This class as JavaScript code
     */
    override fun toJavaScript(): String = """
            |export class $dataClassName {
            |    constructor(json) {
            |        if (json) {
            |$constructorInitsJsCode
            |        }
            |    }
            |
            |$gettersJsCode
            |$settersJsCode
            |}
            |
            |""".trimMargin()

}