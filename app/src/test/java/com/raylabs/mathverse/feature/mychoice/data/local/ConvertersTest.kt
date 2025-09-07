package com.raylabs.mathverse.feature.mychoice.data.local

import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {
    private val c = Converters()

    @Test
    fun `enum to string and back`() {
        val s = c.fromType(ProConType.PRO)
        val e = c.toType(s)
        assertEquals(ProConType.PRO, e)
    }
}