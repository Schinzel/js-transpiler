package io.schinzel.jstranspiler.transpiler

import com.google.common.reflect.ClassPath
import kotlin.reflect.full.isSubclassOf

/**
 * Purpose of this class is to construct the JavaScript code for all the data classes and enums in
 * a Kotlin package
 */
internal class KotlinPackage(private val packageName: String) : IToJavaScript {
    /**
     * @return JavaScript for classes and enums in the constructor argument list of package names
     *
     */
    override fun toJavaScript(): String {
        //Generate a list of kotlin classes and enums from the argument list of package names
        val listOfClassesAndEnums = getKotlinClassesAndEnums(packageName)
        val jsForClasses: String = listOfClassesAndEnums
                //Remove all enums
                .filter { kclass -> !kclass.isSubclassOf(Enum::class) }
                //Convert to list of kotlin classes
                .map { kclass -> KotlinClass(kclass) }
                //Compile kotlin classes to JavaScript
                .compileToJs()
        val jsForEnums: String = listOfClassesAndEnums
                //Only enums in list
                .filter { kclass -> kclass.isSubclassOf(Enum::class) }
                //Convert to list of kotlin enums
                .map { kclass -> KotlinEnum(kclass) }
                //Compile kotlin enums to JavaScript
                .compileToJs()
        //Return the JavaScript for classes and enum
        return jsForClasses + jsForEnums
    }


    companion object {
        @Suppress("UnstableApiUsage")
        private fun getKotlinClassesAndEnums(packageName: String) = ClassPath
                .from(getClassLoader())
                //Get a set of Guava ClassInfo
                .getTopLevelClasses(packageName)
                //Map to list of kclasses
                .map { classInfo -> classInfo.load().kotlin }
                //Only keep classes or enums that are annotated with JsTranspiler_CompileToJavaScript
                .filter { kClass -> kClass.annotations.any { it is JsTranspiler_CompileToJavaScript } }


        /**
         * @return A class loader
         */
        private fun getClassLoader(): ClassLoader = Thread
                .currentThread()
                .contextClassLoader ?: throw RuntimeException("")

    }
}




