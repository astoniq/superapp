package com.astoniq.superapp.feature.portal

import java.lang.IllegalStateException

object PortalManager {

    @JvmStatic
    private val portals: MutableMap<String, Portal> = mutableMapOf()

    @JvmStatic
    fun addPortal(portal: Portal) {
        portals[portal.name] = portal
    }

    @JvmStatic
    fun getPortal(name: String): Portal {
        return portals[name] ?: throw IllegalStateException("Portal with portalId $name not found in PortalManager")
    }

    @JvmStatic
    fun removePortal(name: String): Portal? {
        return portals.remove(name)
    }

    @JvmStatic
    fun size(): Int {
        return portals.size
    }

    fun newPortal(name: String): PortalBuilder {
        return PortalBuilder(name)
    }


}