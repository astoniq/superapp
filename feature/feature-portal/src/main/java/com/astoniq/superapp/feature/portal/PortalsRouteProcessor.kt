package com.astoniq.superapp.feature.portal

import android.content.Context
import com.getcapacitor.ProcessedRoute
import com.getcapacitor.RouteProcessor

class PortalsRouteProcessor(
    val context: Context,
    private val assetMaps: LinkedHashMap<String, AssetMap>
) : RouteProcessor {

    override fun process(basePath: String?, path: String?): ProcessedRoute {
        val processedRoute = ProcessedRoute()

        assertLoop@ for ((mapName, assetMap) in assetMaps) {
            if (path != null) {
                if (path.startsWith(assetMap.virtualPath)) {
                    val assetMapObj = assetMaps[mapName]
                    if (assetMapObj != null) {
                        var trimmedPath =
                            path.replace(assetMap.virtualPath, assetMapObj.getAssetPath())
                        if (trimmedPath.startsWith("/")) {
                            trimmedPath = trimmedPath.drop(1)
                        }

                        processedRoute.path = trimmedPath
                        processedRoute.isAsset = true
                        processedRoute.isIgnoreAssetPath = true
                        break@assertLoop
                    }
                }
            }
        }

        if (processedRoute.path == null || processedRoute.path.isEmpty()) {
            processedRoute.path = "$basePath$path"
            processedRoute.isAsset = true
        }

        return processedRoute
    }
}