package io.schinzel.jstranspiler

/**
 * Give all objects the function println so that println can be chained
 * Example "monkey".println()
 * @return This for chaining
 */
fun <R> R.println(): R = this.apply { println(this) }

fun <R> R.printlnWithPrefix(prefix: String): R = this.apply { println("$prefix: $this") }
