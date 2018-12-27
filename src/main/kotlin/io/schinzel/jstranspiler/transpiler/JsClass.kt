package io.schinzel.jstranspiler.transpiler

import io.schinzel.jstranspiler.annotation.JsTranspilerSetter
import io.schinzel.jstranspiler.transpiler.method.JsGetter
import io.schinzel.jstranspiler.transpiler.method.JsSetter
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * Purpose of this class is to construct the JavaScript code for a Kotlin data class
 */
internal class JsClass(myClass: KClass<out Any>) : IToJavaScript {
    private val dataClassName: String = myClass.simpleName
            ?: throw RuntimeException("Problems getting class name from class")

    private val constructorInits = myClass
            .memberProperties
            .map { property ->
                JsConstructorInit(property)
            }
            .compileToJs()

    private val getters = myClass
            .memberProperties
            .map { property ->
                JsGetter(property)
            }
            .compileToJs()

    private val setters = myClass
            .memberProperties
            //Filter out those that are not annotated as JavaScript setters
            .filter { property -> property.annotations.any { it is JsTranspilerSetter } }
            .map { property ->
                JsSetter(dataClassName, property)
            }
            .compileToJs()

    override fun toJavaScript(): String = """
            |export class $dataClassName {
            |    constructor(json) {
            |        if (json) {
            |$constructorInits
            |        }
            |    }
            |
            |$getters
            |$setters
            |}
            |
            |""".trimMargin()

}