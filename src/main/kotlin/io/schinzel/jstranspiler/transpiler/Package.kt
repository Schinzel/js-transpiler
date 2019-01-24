package io.schinzel.jstranspiler.transpiler

import com.google.common.reflect.ClassPath
import kotlin.reflect.full.isSubclassOf

/**
 * Purpose of this class is to construct the JavaScript code for all the data classes and enums in
 * a Kotlin package
 */
internal class Package(private val packageName: String) : IToJavaScript {
    override fun toJavaScript(): String {
        val listOfClassesAndEnums = getKotlinClassesAndEnums(packageName)
        val jsForClasses: String = listOfClassesAndEnums
                //No enums in list
                .filter { kclass -> !kclass.isSubclassOf(Enum::class) }
                //Covert to list of KotlinClass
                .map { kclass -> KotlinClass(kclass) }
                .compileToJs()
        val jsForEnums: String = listOfClassesAndEnums
                //Only enums in list
                .filter { kclass -> kclass.isSubclassOf(Enum::class) }
                //Covert to list of JsEnums
                .map { kclass -> KotlinEnum(kclass) }
                .compileToJs()
        return jsForClasses + jsForEnums
    }


    companion object {
        @Suppress("UnstableApiUsage")
        private fun getKotlinClassesAndEnums(packageName: String) = ClassPath
                .from(getClassLoader())
                //Get set of Guava ClassInfo
                .getTopLevelClasses(packageName)
                //Map to list of kclasses
                .map { classInfo -> classInfo.load().kotlin }
                //Only keep classes or enums that are annotated with JsTranspiler_CompileToJavaScript
                .filter { kClass -> kClass.annotations.any { it is JsTranspiler_CompileToJavaScript } }

        private fun getClassLoader() = Thread
                .currentThread()
                .contextClassLoader ?: throw RuntimeException("")

    }
}




