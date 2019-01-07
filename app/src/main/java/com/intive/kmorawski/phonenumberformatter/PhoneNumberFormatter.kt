package com.intive.kmorawski.phonenumberformatter

import java.util.*

class PhoneNumberFormatter {
    fun formatNumber(unformattedNumber: String, vararg chunkSizes: Int): String {
        if (chunkSizes.any { it < 0 }) {
            throw NegativeChunkSizeException(chunkSizes.toList())
        }
        val unformattedDigits = unformattedNumber.filter(Char::isDigit)
        return with(Scanner(unformattedDigits)) {
            chunkSizes
                .map {
                    findInLine(".{$it}")
                }
                .filterNot(String::isNullOrEmpty)
                .joinToString(separator = " ")
        }
    }
}

class NegativeChunkSizeException(illegalChunkSizes: Collection<Int>)
    : IllegalArgumentException("Chunk sizes can't be negative numbers. Illegal arguments: $illegalChunkSizes")