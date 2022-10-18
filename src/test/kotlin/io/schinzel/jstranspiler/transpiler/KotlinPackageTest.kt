package io.schinzel.jstranspiler.transpiler

import org.junit.Assert.assertEquals
import org.junit.Test

class KotlinPackageTest {

    @Test
    fun `numberOfClassesAndEnums _ multiple packages _ transpiled classes from both packages`() {
        val packageList = listOf(
            "io.schinzel.jstranspiler.transpiler.util.dir1",
            "io.schinzel.jstranspiler.transpiler.util.dir2"
        )
        val kotlinPackage = KotlinPackage(packageList)
        assertEquals(5, kotlinPackage.numberOfClassesAndEnums)
    }
}