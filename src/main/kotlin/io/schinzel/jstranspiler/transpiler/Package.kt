package io.schinzel.jstranspiler.transpiler

import com.google.common.reflect.ClassPath
import io.schinzel.jstranspiler.annotation.JsTranspilerIgnore
import kotlin.reflect.full.isSubclassOf

/**
 * Purpose of this class is to construct the JavaScript code for all the data classes and enums in
 * a Kotlin package
 */
internal class Package(private val packageName: String) : IToJavaScript {
    override fun toJavaScript(): String {
        val listOfClasses = getKotlinClasses(packageName)
        val jsForClasses: String = listOfClasses
                //Make list with no enums
                .filter { kclass -> !kclass.isSubclassOf(Enum::class) }
                //Map to list of JsClasses
                .map { kclass -> JsClass(kclass) }
                .compileToJs()
        val jsForEnums: String = listOfClasses
                //Make list with only enums
                .filter { kclass -> kclass.isSubclassOf(Enum::class) }
                //Map to list of JsEnums
                .map { kclass -> JsEnum(kclass) }
                .compileToJs()
        return jsForClasses + jsForEnums
    }


    companion object {
        @Suppress("UnstableApiUsage")
        private fun getKotlinClasses(packageName: String) = ClassPath
                .from(getClassLoader())
                //Get set of Guava ClassInfo
                .getTopLevelClasses(packageName)
                //Map to list of kclasses
                .map { classInfo -> classInfo.load().kotlin }
                //Remove any classes annotated with instruction to not transpile class
                .filter { kClass -> kClass.annotations.none { it is JsTranspilerIgnore } }

        private fun getClassLoader() = Thread
                .currentThread()
                .contextClassLoader ?: throw RuntimeException("")

    }
}




