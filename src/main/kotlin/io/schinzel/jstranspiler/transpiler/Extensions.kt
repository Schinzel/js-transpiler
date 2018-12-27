package io.schinzel.jstranspiler.transpiler

import kotlin.reflect.KCallable
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

internal fun List<IToJavaScript>.compileToJs(): String =
        this.joinToString(separator = "\n") { it.toJavaScript() }


internal fun String.firstCharToUpperCase(): String =
        this.substring(0, 1).toUpperCase() + this.substring(1)


internal fun <R> KCallable<R>.getKotlinDataType(): KotlinDataType =
        KotlinDataType(this.returnType.toString())


internal fun <R> KCallable<R>.isEnum(): Boolean =
        this.returnType.jvmErasure.isSubclassOf(Enum::class)
