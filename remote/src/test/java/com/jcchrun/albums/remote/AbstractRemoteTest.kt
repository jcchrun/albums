package com.jcchrun.albums.remote

import java.io.BufferedReader
import java.io.InputStreamReader

abstract class AbstractRemoteTest {

    protected fun readFile(filePath: String): String {
        val bufferedReader =
            BufferedReader(InputStreamReader(javaClass.classLoader?.getResourceAsStream(filePath)))
        val stringBuilder = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }
        return stringBuilder.toString()
    }
}