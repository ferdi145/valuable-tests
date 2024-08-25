package com.ferdi145.valuable_tests

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Test {

    /*
    change calculator.add to -> this.sum = newSum + 2
     */
    @Test
    fun `adding 1 and 2 equals 3`() {
        val result: Int = Adder().add(1, 2)

        assertThat(result).isEqualTo(3)
    }

    /*
    refactoring: inline adder.add
     */
    @Test
    fun `adding 1 and 2 equals 3 (sociable test)`() {
        val adder = Adder()
        val sut = Calculator(adder)

        sut.add(1)
        sut.add(2)

        assertThat(sut.result()).isEqualTo(3)
    }

    /*
    refactoring: inline adder.add
     */
    @Test
    fun `adding 1 and 2 equals 3 (isolated test with stub)`() {
        val adder: Adder = mockk()
        every { adder.add(0, 1) } returns 1
        every { adder.add(1, 2) } returns 3
        val sut = Calculator(adder)

        sut.add(1)
        sut.add(2)

        assertThat(sut.result()).isEqualTo(3)
    }

    /*
    refactoring: inline adder.add
     */
    @Test
    fun `adding 1 and 2 equals 3 (isolated test with mock, behaviour verification)`() {
        val adder: Adder = mockk()
        every { adder.add(0, 1) } returns 1
        every { adder.add(1, 2) } returns 3
        val sut = Calculator(adder)

        sut.add(1)
        sut.add(2)

        verify(exactly = 1) { adder.add(0, 1) }
        verify(exactly = 1) { adder.add(1, 2) }
    }
    
    /*
    refactoring: inline adder.add
     */
    @Test
    fun `adding 1 and 2 equals 3 (isolated test with mock, behaviour verification, output verification)`() {
        val adder: Adder = mockk()
        every { adder.add(0, 1) } returns 1
        every { adder.add(1, 2) } returns 3
        val sut = Calculator(adder)

        sut.add(1)
        sut.add(2)

        verify(exactly = 2) { adder.add(any(), any()) }
        assertThat(sut.result()).isEqualTo(3)
    }

    /*
    change adder.add to -> return 0 + number2
     */
    @Test
    fun `adding 1 and 2 equals 3 result of adder`() {
        val adder = Adder()
        val sut = Calculator(adder)

        sut.add(1)
        sut.add(2)

        assertThat(sut.result()).isEqualTo(adder.add(1, 2))
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // new implementation: Calculator now prints result directly, has 'printer' collaborator for this
//    @Test
//    fun `adding 1 and 2 equals prints 3 (sociable test with self written test double)`() {
//        val adder = Adder()
//        val printer = FakePrinter()
//        val sut = CalculatorWithPrinter(adder, printer)
//        sut.add(1)
//        sut.add(2)
//        
//        sut.printResult()
//
//        assertThat(printer.receivedPrintJobs()).isEqualTo(listOf("Result: 3"))
//    }
//
//    @Test
//    fun `adding 1 and 2 equals prints 3 (sociable test with mockk mock)`() {
//        val adder = Adder()
//        val printer: Printer = mockk(relaxed = true)
//        val sut = CalculatorWithPrinter(adder, printer)
//        sut.add(1)
//        sut.add(2)
//
//        sut.printResult()
//
//        verify(exactly = 1) { printer.print("Result: 3") }
//    }
}
