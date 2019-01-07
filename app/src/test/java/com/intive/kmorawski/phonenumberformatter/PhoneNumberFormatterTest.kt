package com.intive.kmorawski.phonenumberformatter

import org.junit.Assert.assertEquals
import org.junit.Test

class PhoneNumberFormatterTest {
    val formatter = PhoneNumberFormatter()

    fun String.chunkifiedBy(vararg chunkSizes: Int) =
        formatter.formatNumber(this, *chunkSizes)

    fun String.becomes(expected: String) = assertEquals(expected,this)

    @Test
    fun `given a number and chunk sizes - when formatted - digits are grouped into chunks of correct sizes`() {
        "662345712"
            .chunkifiedBy(3, 3, 3)
            .becomes("662 345 712")
    }

    @Test
    fun `given a number and chunk sizes exceeding the length of the number - when formatted - redundant chunks are discarded`() {
        "66234"
            .chunkifiedBy(3, 2, 2, 2)
            .becomes("662 34")
    }

    @Test
    fun `given an empty number with some chunk sizes - when formatted - empty string is returned`() {
        ""
            .chunkifiedBy(3, 2, 2, 2)
            .becomes("")
    }

    @Test
    fun `given a phone number and chunk sizes with zero among them - when formatted - digits are grouped into chunks of correct sizes`() {
        "6678811"
            .chunkifiedBy(3, 0, 2, 2)
            .becomes("667 88 11")
    }

    @Test(expected = NegativeChunkSizeException::class)
    fun `given some phone number and negative chunk size - when formatted - NegativeChunkSizeException is thrown`() {
        val `illegal chunk size` = -2
        "6678811"
            .chunkifiedBy(3, `illegal chunk size`, 2, 2)
    }

    @Test
    fun `given some phone number and no chunk sizes - when formatted - empty string is returned`() {
        "6678811"
            .chunkifiedBy()
            .becomes("")
    }

    @Test
    fun `given phone number containing non-digits and chunk sizes - when formatted - non-digits are disregarded`() {
        " 667-884-114"
            .chunkifiedBy(3, 2, 2, 2)
            .becomes("667 88 41 14")
    }
}