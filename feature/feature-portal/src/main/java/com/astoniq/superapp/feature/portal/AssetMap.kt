package com.astoniq.superapp.feature.portal

class AssetMap(
    val name: String,
    val virtualPath: String,
    val path: String
) {

    fun getAssetPath(): String {
        return path;
    }
}