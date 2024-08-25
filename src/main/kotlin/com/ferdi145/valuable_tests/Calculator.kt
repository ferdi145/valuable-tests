package com.ferdi145.valuable_tests

class Calculator(private val adder: Adder) {
    private var sum: Int = 0

    fun add(valueToBeAdded: Int) {
        val newSum = adder.add(sum, valueToBeAdded)
        this.sum = newSum
    }

    fun result(): Int {
        return this.sum
    }

}
