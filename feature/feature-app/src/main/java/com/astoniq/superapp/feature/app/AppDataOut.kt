package com.astoniq.superapp.feature.app

class AppDataOut(
    val id: String,
    val method: String,
    val success: Boolean,
    val payload: Any?
) {
}