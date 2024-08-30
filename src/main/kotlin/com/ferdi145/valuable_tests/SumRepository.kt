package com.ferdi145.valuable_tests

interface SumRepository {
    fun saveSum(value: Int)
    fun fetchSum(): Int
}