package com.ferdi145.valuable_tests

import java.io.File
import java.nio.file.Path

class FileSumRepository(tempDir: Path) : SumRepository {
    private val filePath: String = "${tempDir}/sum-persistance.txt"

    override fun saveSum(value: Int) {
        File(this.filePath).printWriter().use { out ->
            out.println(value.toString())
        }
    }

    override fun fetchSum(): Int =
        File(this.filePath).let { file ->
            if (!file.exists()) {
                0
            } else {
                file
                    .readText()
                    .trim()
                    .toInt()
            }
        }

}
