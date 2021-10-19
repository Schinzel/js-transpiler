@file:Suppress("ClassName")

package io.schinzel.jstranspiler.transpiler

import java.lang.annotation.ElementType


/**
 * This annotation is applied to data classes and enums.
 * If a applied to a data class or to and enum and
 * this is in a package that is added as a transpiler source package
 * then it is compiled to JavaScript.
 */
@Target(AnnotationTarget.CLASS)
annotation class JsTranspiler_CompileToJavaScript

/**
 * This annotation is applied to data class properties.
 * If a applied to a property a JavaScript setter is created.
 *
 * Example:
 * Kotlin:
 * @JsTranspiler_CreateSetter val firstName: String,
 * ->
 * JavaScript:
 * setFirstName(firstName)
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class JsTranspiler_CreateSetter
