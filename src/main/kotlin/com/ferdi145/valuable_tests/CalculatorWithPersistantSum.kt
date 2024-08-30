package com.ferdi145.valuable_tests

class CalculatorWithPersistantSum(
    private val adder: Adder,
    private val sumRepository: SumRepository,
) {
    fun add(valueToBeAdded: Int): Int {
        val currentSum = sumRepository.fetchSum()
        val newSum = adder.add(currentSum, valueToBeAdded)
        this.sumRepository.saveSum(newSum)
        
        return newSum
    }

}
