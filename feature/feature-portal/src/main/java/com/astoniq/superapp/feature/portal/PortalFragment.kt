package com.astoniq.superapp.feature.portal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.getcapacitor.*
import com.getcapacitor.WebViewListener;
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

class PortalFragment : Fragment {

    private val PORTAL_NAME = "PORTAL_NAME"
    private val duration: Long = 500

    private var fadeView: View? = null;

    var portal: Portal? = null;
    var onBridgeAvailable: ((bridge: Bridge) -> Unit)? = null

    private var bridge: Bridge? = null;
    private var keepRunning = true
    private var config: CapConfig? = null;
    private val webViewListeners: MutableList<WebViewListener> = ArrayList()

    constructor()

    constructor(portal: Portal?) {
        this.portal = portal
    }

    constructor(portal: Portal?, onBridgeAvailable: ((bridge: Bridge) -> Unit)) {
        this.portal = portal
        this.onBridgeAvailable = onBridgeAvailable
    }

    /**
     * Extends the Android Fragment `onCreateView` lifecycle event.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_portal, container, false)
    }

    fun addWebViewListener(webViewListener: WebViewListener) {
        webViewListeners.add(webViewListener)
    }

    /**
     * Extends the Android Fragment `onViewCreated` lifecycle event.
     * At this point in the lifecycle the fragment will attempt to load the Portal content.
     * This is when the fragment will load any provided config or plugins.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load(savedInstanceState)

        if (getBridge() != null) {
            fadeView = View(requireActivity())
            fadeView?.id = View.generateViewId()
            fadeView?.layoutParams = getBridge()?.webView?.layoutParams
            fadeView?.setBackgroundResource(androidx.appcompat.R.color.material_blue_grey_900)
            getBridge()?.webView?.addView(fadeView, 0)
            fadeView?.bringToFront()
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        bridge?.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PORTAL_NAME, portal?.name)
    }

    /**
     * Extends the Android Fragment 'onDestroy' lifecycle event.
     * At this point in the lifecycle the fragment will attempt to clean up the [Bridge] and
     * unsubscribe any attached Portals message subscriptions.
     */
    override fun onDestroy() {
        super.onDestroy()
        if (bridge != null) {
            bridge?.onDestroy()
            bridge?.onDetachedFromWindow()
        }
    }

    /**
     * Extends the Android Fragment 'onResume' lifecycle event.
     */
    override fun onResume() {
        super.onResume()
        bridge?.app?.fireStatusChange(true)
        bridge?.onResume()
        Logger.debug("App resumed")
    }

    /**
     * Extends the Android Fragment 'onPause' lifecycle event.
     */
    override fun onPause() {
        super.onPause()
        bridge?.onPause()
        Logger.debug("App paused")
    }

    fun getBridge(): Bridge? {
        return bridge
    }

    private fun load(savedInstanceState: Bundle?) {
        if (bridge == null) {
            Logger.debug("Loading bridge with portal")

            val existingPortalName = savedInstanceState?.getString(PORTAL_NAME, null)
            if (existingPortalName != null && portal == null) {
                portal = PortalManager.getPortal(existingPortalName)
            }

            if (portal != null) {
                val startDir: String = portal?.startDir!!
                var configToUse: CapConfig? = null
                if (config != null) {
                    configToUse = config
                }

                var bridgeBuilder = Bridge.Builder(this)
                    .setInstanceState(savedInstanceState)
                    .addWebViewListener(object : WebViewListener() {
                        override fun onPageLoaded(webView: WebView?) {
                            fadeView?.animate()
                                ?.alpha(0f)
                                ?.setDuration(duration)
                                ?.setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    fadeView?.visibility = View.GONE
                                }
                            })
                        }
                    })
                    .addWebViewListeners(webViewListeners)

                if (config == null) {
                    try {
                        val configFile =
                            requireContext().assets.open("$startDir/capacitor.config.json")
                        configToUse = CapConfig.loadFromAssets(requireContext(), startDir)
                    } catch (_: Exception) {
                    }
                }

                bridgeBuilder.setServerPath(ServerPath(ServerPath.PathType.ASSET_PATH, startDir));

                portal?.assetMaps?.let {
                    if (it.isNotEmpty()) {
                        bridgeBuilder = bridgeBuilder.setRouteProcessor(
                            PortalsRouteProcessor(
                                requireContext(),
                                it
                            )
                        )
                    }
                }

                if (configToUse == null) {
                    configToUse =
                        CapConfig.Builder(requireContext()).setInitialFocus(false).create()
                }

                bridgeBuilder = bridgeBuilder.setConfig(configToUse)
                bridge = bridgeBuilder.create()

                keepRunning = bridge?.shouldKeepRunning()!!

                onBridgeAvailable?.let { onBridgeAvailable ->
                    bridge?.let { bridge ->
                        onBridgeAvailable(
                            bridge
                        )
                    }
                }

            }
        }
    }
}

