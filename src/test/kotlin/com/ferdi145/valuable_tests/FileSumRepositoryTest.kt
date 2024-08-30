package com.ferdi145.valuable_tests

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class FileSumRepositoryTest {

    @Test
    fun `persists sum`(@TempDir tempDir: Path) {
        val sut = FileSumRepository(tempDir)
        sut.saveSum(1)
        
        sut.saveSum(2)

        val result = sut.fetchSum()
        assertThat(result).isEqualTo(2)
    }
    
    @Test
    fun `sum is zero when no sum has been saved yet`(@TempDir tempDir: Path) {
        val sut = FileSumRepository(tempDir)

        val result = sut.fetchSum()
        
        assertThat(result).isEqualTo(0)
    }
}