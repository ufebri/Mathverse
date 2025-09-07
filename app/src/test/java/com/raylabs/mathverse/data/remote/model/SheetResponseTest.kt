package com.raylabs.mathverse.data.remote.model

import org.junit.Assert.assertEquals
import org.junit.Test

class SheetResponseTest {

    @Test
    fun `test data class fields`() {
        val values = listOf(
            listOf("Name", "Score"),
            listOf("Alice", "90"),
            listOf("Bob", "80")
        )

        val response = SheetResponse(
            range = "Sheet1!A1:B3",
            majorDimension = "ROWS",
            values = values
        )

        // Verifikasi semua field
        assertEquals("Sheet1!A1:B3", response.range)
        assertEquals("ROWS", response.majorDimension)
        assertEquals(values, response.values)
    }

    @Test
    fun `copy creates equal but separate instance`() {
        val response1 = SheetResponse("A1:B2", "ROWS", emptyList())
        val response2 = response1.copy(range = "A1:B3")

        assertEquals("A1:B3", response2.range)
        assertEquals("ROWS", response2.majorDimension)
        assertEquals(emptyList<List<String>>(), response2.values)
    }
}