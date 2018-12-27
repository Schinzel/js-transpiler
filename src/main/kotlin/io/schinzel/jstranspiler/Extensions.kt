package io.schinzel.jstranspiler

/**
 * Give all objects the function println so that println can be chained
 * Example "monkey".println()
 * @return This for chaining
 */
fun <R> R.println(): R = this.apply { println(this) }

fun <R> R.print(): R = this.apply { print(this) }

/**
 * Useful for debugging collections with map and filter operations
 */
fun <T> T.log(): T {
    println(this)
    return this
}