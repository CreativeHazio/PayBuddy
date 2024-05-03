package com.timeless.paybuddy.util

interface Mapper<F,T> {
    fun mapFrom(from : F) : T
}