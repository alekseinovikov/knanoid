package org.knanoid

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.abs


internal class KnanoidKtTest {

    @Test
    fun checkSanityWith1MRandomIds() {
        val results = HashSet<String>(1000_000)
        repeat(1000_000) {
            val generated = randomNanoId()
            assertTrue(results.add(generated))
        }
    }

    @Test
    fun checkWrongSizeException() {
        assertThrows(IllegalArgumentException::class.java) {
            randomNanoId(0)
        }

        assertThrows(IllegalArgumentException::class.java) {
            randomNanoId(-1)
        }
    }

    @Test
    fun checkAlphabetMustBeBetween0and256Exception() {
        assertThrows(IllegalArgumentException::class.java) {
            randomNanoId(alphabet = "".toCharArray())
        }

        assertThrows(IllegalArgumentException::class.java) {
            val bigAlphabet = generateSequence { 'A' }.take(257).toList().toCharArray()
            randomNanoId(alphabet = bigAlphabet)
        }
    }

    @Test
    fun checkWellDistributedProperty() {
        val charAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        val charCounts: MutableMap<String, Long> = HashMap()

        repeat(100_000) {
            val id = randomNanoId(20, charAlphabet)

            for (element in id) {
                val value = element.toString()
                val charCount = charCounts[value]
                if (charCount == null) {
                    charCounts[value] = 1L
                } else {
                    charCounts[value] = charCount + 1
                }
            }
        }

        for (charCount in charCounts.values) {
            val distribution: Double = (charCount * charAlphabet.size / (charCount * 20)).toDouble()
            assertTrue(almostEqual(distribution, 1.0, 0.05) || almostEqual(distribution, 0.05, 0.05))
        }
    }

    private fun almostEqual(a: Double, b: Double, eps: Double) = abs(a - b) < eps

}