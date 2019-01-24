package io.schinzel.jstranspiler.transpiler

/**
 * Purpose of this class is ...
 */
@Target(AnnotationTarget.CLASS)
annotation class JsTranspilerIgnore


/**
 * This annotation is for data class properties.
 * If a applied to a property a JavaScript setter is created.
 *
 * Example:
 * Kotlin:
 * @JsTranspiler_CreateSetter val firstName: String,
 * ->
 * JavaScript:
 * setFirstName(firstName)
 */
@Target(AnnotationTarget.PROPERTY)
annotation class JsTranspiler_CreateSetter