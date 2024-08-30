package com.ferdi145.valuable_tests

class FakeSumRepository : SumRepository {
    private var _printedValues = 0
    
    override fun saveSum(value: Int) {
        this._printedValues = value
    }

    override fun fetchSum(): Int {
        return this._printedValues
    }
}
