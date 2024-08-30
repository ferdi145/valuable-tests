package com.ferdi145.valuable_tests

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class Test {

    /*
    change last line of add() in Calculator to -> this.sum = newSum + 2
     */
    @Test
    fun `adding 1 and 2 equals 3`() {
        val result: Int = Adder().add(1, 2)

        assertThat(result).isEqualTo(3)
    }

    /*
    refactoring: inline adder.add() in Calculator
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
    refactoring: inline adder.add() in Calculator
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
    change adder.add to -> return 0 + number2 -> still green
     */
    @Test
    fun `adding 1 and 2 equals result of adder`() {
        val adder = Adder()
        val sut = Calculator(adder)

        sut.add(1)
        sut.add(2)

        val result = adder.add(1, 2)
        assertThat(sut.result()).isEqualTo(result)
    }


    @Test
    fun `sum is persisted by repository (test with self written test double)`() {
        val adder = Adder()
        val repository = FakeSumRepository()
        val sut = CalculatorWithPersistantSum(adder, repository)

        sut.add(1)
        sut.add(2)

        val sum: Int = repository.fetchSum()
        assertThat(sum).isEqualTo(3)
    }

    @Test
    fun `sum is persisted by repository (test with mockk stub 1)`() {
        val adder = Adder()
        val sumRepository = mockk<SumRepository>()
        every { sumRepository.saveSum(any()) } just Runs
        every { sumRepository.fetchSum() } returns 0 andThen 1 andThen 3
        val sut = CalculatorWithPersistantSum(adder, sumRepository)

        sut.add(1)
        sut.add(2)

        val sum: Int = sumRepository.fetchSum()
        assertThat(sum).isEqualTo(3)
    }

    @Test
    fun `sum is persisted by repository (test with mockk mock)`() {
        val adder = Adder()
        val sumRepository = mockk<SumRepository>()
        every { sumRepository.saveSum(any()) } just Runs
        every { sumRepository.fetchSum() } returns 0 andThen 1
        val sut = CalculatorWithPersistantSum(adder, sumRepository)

        sut.add(1)
        sut.add(2)

        verify(exactly = 1) { sumRepository.saveSum(1) }
        verify(exactly = 1) { sumRepository.saveSum(3) }
    }

    /*
    refactoring: change file location in repo
     */
    @Test
    fun `sum is persisted to file (test with real FileSumRepository)`(@TempDir tempDir: Path) {
        val adder = Adder()
        val repository = FileSumRepository(tempDir)
        val sut = CalculatorWithPersistantSum(adder, repository)

        sut.add(1)
        sut.add(2)


        val result = File("${tempDir}/sum-persistance.txt").let { file ->
            file
                .readText()
                .trim()
                .toInt()
        }
        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `sum is persisted by repository (test with real FileSumRepository)`(@TempDir tempDir: Path) {
        val adder = Adder()
        val repository = FileSumRepository(tempDir)
        val sut = CalculatorWithPersistantSum(adder, repository)

        sut.add(1)
        sut.add(2)

        val sum: Int = repository.fetchSum()
        assertThat(sum).isEqualTo(3)
    }
}
