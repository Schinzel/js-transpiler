package io.schinzel.jstranspiler.transpiler

import com.fasterxml.jackson.annotation.JsonIgnore
import io.schinzel.jstranspiler.transpiler.method.JsGetter
import io.schinzel.jstranspiler.transpiler.method.JsSetter
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

/**
 * Purpose of this class is to construct the JavaScript code for a Kotlin data class
 */
class KotlinClass(kClass: KClass<out Any>) : IToJavaScript {

    /**
     * This constructor is invoked when constructing this class with a Java-class from Java code.
     * If Java-class is used as an argument from Kotlin code, the main constructor is used.
     *
     * @param clazz A java class
     */
    constructor(clazz: Class<out Any>) : this(clazz.kotlin)

    // The name of this class
    private val dataClassName: String = kClass.simpleName
            ?: throw RuntimeException("Problems getting class name from class")

    private val memberProperties: List<KProperty1<out Any, Any?>> = kClass
            // Get all properties for class
            .memberProperties
            // Remove all properties with JsonIgnore annotation
            .filter { it.javaField?.getAnnotation(JsonIgnore::class.java) == null }

    // JavaScript code for setting up properties in the constructor
    internal val constructorInitsJsCode: String = memberProperties
            // Create list of JavaScript constructor lines
            .map { JsConstructorInit(it) }
            // Compile list to string with all code for constructor
            .compileToJs()

    //JavaScript code for property getters
    internal val gettersJsCode: String = memberProperties
            // Create list of JavaScript getters
            .map { JsGetter(it) }
            // Compile list of getters to a single string
            .compileToJs()

    //JavaScript code for property setters
    internal val settersJsCode: String = memberProperties
            // Remove properties that are not annotated as JavaScript setters
            .filter { property -> property.annotations.any { it is JsTranspiler_CreateSetter } }
            // Create list of JavaScript setters
            .map { JsSetter(it, dataClassName) }
            .compileToJs()

    /**
     * @return This class as JavaScript code
     */
    override fun toJavaScript(): String = """
            |export class $dataClassName extends DataObject {
            |    constructor(json) {
            |        super();
            |        if (json) {
            |$constructorInitsJsCode
            |        }
            |    }
            |
            |$gettersJsCode
            |$settersJsCode
            |
            |}
            |
            |""".trimMargin()

}