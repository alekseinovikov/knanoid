package org.knanoid

import java.security.SecureRandom
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.ln

private val generator = SecureRandom()
private val alphabet = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

private const val defaultSize = 21

private fun randomNanoId(random: Random, chars: CharArray, size: Int): String {
    if (chars.isEmpty() || chars.size >= 256) throw IllegalArgumentException("chars must be in range (0..256)")
    if (size <= 0) throw IllegalArgumentException("size must be more than zero")

    val mask = (2 shl (floor(ln((chars.size - 1).toDouble()) / ln(2.0))).toInt()) - 1
    val step = ceil(1.6 * mask * size / chars.size).toInt()

    val resultStringBuilder = StringBuilder()

    while (true) {
        val bytes = ByteArray(step)
        random.nextBytes(bytes)

        for (i in 0 until step) {
            val index = bytes[i].toInt() and mask
            if (index < chars.size) {
                resultStringBuilder.append(chars[index])

                if (size == resultStringBuilder.length) {
                    return resultStringBuilder.toString()
                }
            }
        }

    }
}

fun randomNanoId() = randomNanoId(generator, alphabet, defaultSize)