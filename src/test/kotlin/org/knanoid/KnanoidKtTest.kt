package org.knanoid

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class KnanoidKtTest {

    @Test
    fun checkSanityWith1MRandomIds() {
        val results = HashSet<String>(1000_000)
        repeat(1000_000) {
            val generated = randomNanoId()
            assertTrue(results.add(generated))
        }
    }

}