package com.astoniq.superapp.feature.app

class AppPromise(
    private val id: String,
    private val method: String,
    private val bridge: AppBridge,
) {

    fun resolve(payload: Any? = null) {
        bridge.send(id, method, true, payload)
    }
}