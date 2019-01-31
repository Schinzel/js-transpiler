package io.schinzel.jstranspiler.transpiler

import kotlin.reflect.KCallable
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

internal fun List<IToJavaScript>.compileToJs(): String =
        this.joinToString(separator = "\n") { it.toJavaScript() }


internal fun String.firstCharToUpperCase(): String =
        this.substring(0, 1).toUpperCase() + this.substring(1)


internal fun <R> KCallable<R>.getFullClassName(): String =
        this.returnType.toString()


internal fun <R> KCallable<R>.getSimpleClassName(): String =
        this.returnType.toString().substringAfterLast(".")


internal fun <R> KCallable<R>.isEnum(): Boolean =
        this.returnType.jvmErasure.isSubclassOf(Enum::class)


internal fun <R> KCallable<R>.isList(): Boolean =
        this.returnType.jvmErasure.isSubclassOf(List::class)


internal fun <R> KCallable<R>.getListElementsSimpleClassName(): String {
    if (!this.isList()) {
        throw RuntimeException("Tried to get the simple class name of elements of a list. But the data type was not a list. " + this.getFullClassName())
    }
    return this.getFullClassName()
            .substringAfterLast(".")
            .substringBefore(">")
}


internal fun <R> KCallable<R>.getListElementsFullClassName(): String {
    if (!this.isList()) {
        throw RuntimeException("Tried to get the simple class name of elements of a list. But the data type was not a list. " + this.getFullClassName())
    }
    return this.getFullClassName()
            .substringAfterLast("<")
            .substringBefore(">")
}


internal fun <R> KCallable<R>.isListOfPrimitiveDataType(): Boolean =
        this.isList() && this.getFullClassName()
                .substringAfter("<")
                .startsWith("kotlin")


internal fun <R> KCallable<R>.isListOfEnums(): Boolean {
    if (!this.isList()) {
        return false
    }
    return Class
            .forName(this.getListElementsFullClassName())
            .isEnum
}
