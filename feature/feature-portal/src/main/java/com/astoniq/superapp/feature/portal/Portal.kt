package com.astoniq.superapp.feature.portal

import com.getcapacitor.Plugin

class Portal(val name: String) {

    internal val plugins = ArrayList<Plugin>()

    internal val assetMaps = LinkedHashMap<String, AssetMap>()

    var portalFragmentType: Class<out PortalFragment?> = PortalFragment::class.java

    var startDir: String = ""
        get() = field.ifEmpty { name }

}

class PortalBuilder(val name: String) {

}