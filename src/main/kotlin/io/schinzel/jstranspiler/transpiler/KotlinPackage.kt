package io.schinzel.jstranspiler.transpiler

import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Purpose of this class is to construct the JavaScript code for all the data classes and enums in
 * a Kotlin package
 */
internal class KotlinPackage(packageName: String) {
    //Generate a list of kotlin classes and enums from the argument list of package names
    private val listOfClassesAndEnums: List<KClass<out Any>> = Reflections(packageName)
            .getTypesAnnotatedWith(JsTranspiler_CompileToJavaScript::class.java)
            .map { it.kotlin }

    val numberOfClassesAndEnums: Int = listOfClassesAndEnums.size


    /**
     * @return JavaScript for classes and enums in the constructor argument list of package names
     */
    fun toJavaScript(): String {

        // Compile JavaScript for classes
        val jsForClasses: String = listOfClassesAndEnums
                // Remove all enums
                .filter { kclass -> !kclass.isSubclassOf(Enum::class) }
                // Convert to list of kotlin classes
                .map { kclass -> KotlinClass(kclass) }
                // Compile kotlin classes to JavaScript
                .compileToJs()

        // Compile JavaScript for enums
        val jsForEnums: String = listOfClassesAndEnums
                // Only enums in list
                .filter { kclass -> kclass.isSubclassOf(Enum::class) }
                // Convert to list of kotlin enums
                .map { kclass -> KotlinEnum(kclass) }
                // Compile kotlin enums to JavaScript
                .compileToJs()

        // Return the JavaScript for classes and enum
        return jsForClasses + jsForEnums
    }


}




