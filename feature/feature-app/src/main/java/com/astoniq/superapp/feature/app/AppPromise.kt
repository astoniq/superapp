package com.astoniq.superapp.feature.app

class AppPromise(
    private val id: String,
    private val type: String,
    private val bridge: AppBridge,
) {

    fun resolve(payload: Any? = null) {
        bridge.send(id, type, payload)
    }
}