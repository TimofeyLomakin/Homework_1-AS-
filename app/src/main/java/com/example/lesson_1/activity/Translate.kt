package com.example.lesson_1.activity

fun translate(count: Long): String {
    if ((count / 1_000_000).toInt() != 0) {
        return ((count / 100_000).toDouble() / 10).toString() + "M"
    } else if ((count / 1_000).toInt() != 0) {
        return ((count / 100).toDouble() / 10).toString() + "K"
    }
    return count.toString()
}