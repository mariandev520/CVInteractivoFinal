package com.example.myapplication

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CallApi {

    fun makeHttpRequest(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        val inputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        inputStream.close()
        return response.toString()
    }

    fun fetchDataFromApi(): JSONObject {
        val url = "https://randomuser.me/api/"
        val response = makeHttpRequest(url)
        return JSONObject(response)
    }

    fun changeValue(booleano: Boolean): Pair<Boolean, String> {
        val newBoolean = !booleano
        val newCv = if (newBoolean) "https://randomuser.me/api/" else ""
        return Pair(newBoolean, newCv)
    }
}