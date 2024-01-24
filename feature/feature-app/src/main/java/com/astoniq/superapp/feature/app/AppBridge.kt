package com.astoniq.superapp.feature.app

import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONException

class AppBridge(
    val objectMapper: ObjectMapper,
    private val executor: AppExecutor
) {

    val generators = mutableMapOf<String, (AppData) -> Unit>()

    inline fun <reified T> add(type: String, crossinline callback: (AppPromise, T) -> Unit) {
        val generator: (AppData) -> Unit = {
            val promise = AppPromise(it.id, it.type, this)
            val payload: T = objectMapper.readValue(it.payload, T::class.java)
            callback(promise, payload)
        }
        generators[type] = generator
    }

    inline fun add(type: String, crossinline callback: (AppPromise) -> Unit) {
        val generator: (AppData) -> Unit = {
            val promise = AppPromise(it.id, it.type, this)
            callback(promise)
        }
        generators[type] = generator
    }

    inline fun add(type: String, crossinline callback: () -> Unit) {
        val generator: (AppData) -> Unit = {
            callback()
        }
        generators[type] = generator
    }

    fun send(id: String, type: String, payload: Any?) {

        val jsonObject = objectMapper
            .writeValueAsString(AppDataOut(id, type, payload))

        executor.execute("send($jsonObject)")
    }

    fun receive(payload: String) {
        val data: AppData

        try {
            data = objectMapper.readValue(payload, AppData::class.java)
        } catch (exception: JSONException) {
            return
        }

        val generator = generators[data.type]

        if (generator != null) {
            generator(data)
        }
    }
}
