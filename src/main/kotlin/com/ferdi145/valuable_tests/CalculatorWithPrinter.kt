package com.ferdi145.valuable_tests

class CalculatorWithPrinter(
    private val adder: Adder,
    private val printer: Printer,
) {
    private var sum: Int = 0

    fun add(valueToBeAdded: Int) {
        val newSum = adder.add(sum, valueToBeAdded)
        this.sum = newSum
    }

    fun printResult() {
        this.printer.print("Result: $sum")
    }
}
