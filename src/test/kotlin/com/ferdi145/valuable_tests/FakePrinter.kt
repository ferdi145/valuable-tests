package com.ferdi145.valuable_tests

class FakePrinter : Printer {
    private val _printedValues = mutableListOf<String>()
    
    override fun print(value: String) {
        this._printedValues.add(value)
    }

    fun receivedPrintJobs(): Any {
        return _printedValues.toList()
    }
}
