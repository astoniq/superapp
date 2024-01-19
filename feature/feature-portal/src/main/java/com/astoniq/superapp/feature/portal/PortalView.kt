package com.astoniq.superapp.feature.portal

import android.animation.LayoutTransition
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.getcapacitor.Bridge
import kotlin.jvm.Throws

class PortalView : FrameLayout {

    private var portalFragment: PortalFragment? = null
    private var onBridgeAvailable: ((bridge: Bridge) -> Unit)? = null

    private var mDisappearingFragmentChildren: ArrayList<View>? = null
    private var mTransitioningFragmentViews: ArrayList<View>? = null
    private var mDrawDisappearingViewsFirst = true

    var portal: Portal? = null
    var portalId: String? = null
    var viewId: String? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, portal: Portal) : this(
        context,
        portal,
        portal.name + "_view",
        null
    )

    constructor(
        context: Context,
        portal: Portal,
        viewId: String,
        onBridgeAvailable: ((bridge: Bridge) -> Unit)?
    ) : super(context) {
        this.onBridgeAvailable = onBridgeAvailable
        this.portal = portal
        this.portalId = portal.name
        this.viewId = viewId
        this.id = View.generateViewId()
        loadPortal(context, null)
    }


    @Throws(Exception::class)
    fun loadPortal(context: Context, attrs: AttributeSet?) {
        if (context is Activity) {
            val fm = (context as AppCompatActivity).supportFragmentManager
            loadPortal(fm, attrs)
        }
    }

    @Throws(Exception::class)
    fun loadPortal(fm: FragmentManager, attrs: AttributeSet?) {
        val id = id

        if (portal == null && PortalManager.size() == 0) {
            throw Exception("Portals has not been setup with any Portals")
        }

        if (portalId == null) {
            throw IllegalStateException("Portal view mush have a defined portalId")
        }

        portalId?.let {
            val portal = portal ?: PortalManager.getPortal(it)

            if (id <= 0) {
                throw IllegalStateException("Portals mush have an android:id defined")
            }

            val existingFragment = fm.findFragmentById(id)

            var fmTransition: FragmentTransaction = fm.beginTransaction()

            if (existingFragment != null) {
                fmTransition.remove(existingFragment)
                fmTransition.commit()
                fmTransition = fm.beginTransaction()
            }

            portalFragment = fm.fragmentFactory.instantiate(
                context.classLoader,
                portal.portalFragmentType.name
            ) as PortalFragment

            portalFragment?.portal = portal
            portalFragment?.onBridgeAvailable = this.onBridgeAvailable
            attrs?.let { attributeSet ->
                portalFragment?.onInflate(context, attributeSet, null)
            }

            val handler = Handler()
            val runnable = Runnable {
                fmTransition.setReorderingAllowed(true)
                    .replace(id, portalFragment!!)
                    .commitNowAllowingStateLoss()
            }

            handler.post(runnable)
        }

    }


    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            // Give child views fresh insets.
            child.dispatchApplyWindowInsets(WindowInsets(insets))
        }
        return insets
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (mDrawDisappearingViewsFirst && mDisappearingFragmentChildren != null) {
            for (i in mDisappearingFragmentChildren!!.indices) {
                super.drawChild(canvas, mDisappearingFragmentChildren!![i], drawingTime)
            }
        }
        super.dispatchDraw(canvas)
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        if (mDrawDisappearingViewsFirst && (mDisappearingFragmentChildren != null
                    ) && (mDisappearingFragmentChildren!!.size > 0)
        ) {
            // If the child is disappearing, we have already drawn it so skip.
            if (mDisappearingFragmentChildren!!.contains(child)) {
                return false
            }
        }
        return super.drawChild(canvas, child, drawingTime)
    }

    override fun startViewTransition(view: View) {
        if (view.parent === this) {
            if (mTransitioningFragmentViews == null) {
                mTransitioningFragmentViews = ArrayList()
            }
            mTransitioningFragmentViews!!.add(view)
        }
        super.startViewTransition(view)
    }

    override fun endViewTransition(view: View) {
        if (mTransitioningFragmentViews != null) {
            mTransitioningFragmentViews!!.remove(view)
            if ((mDisappearingFragmentChildren != null
                        && mDisappearingFragmentChildren!!.remove(view))
            ) {
                mDrawDisappearingViewsFirst = true
            }
        }
        super.endViewTransition(view)
    }

    fun setDrawDisappearingViewsLast(drawDisappearingViewsFirst: Boolean) {
        mDrawDisappearingViewsFirst = drawDisappearingViewsFirst
    }

    override fun removeViewAt(index: Int) {
        val view = getChildAt(index)
        addDisappearingFragmentView(view)
        super.removeViewAt(index)
    }

    override fun removeViewInLayout(view: View) {
        addDisappearingFragmentView(view)
        super.removeViewInLayout(view)
    }

    override fun removeView(view: View) {
        addDisappearingFragmentView(view)
        super.removeView(view)
    }

    override fun removeViews(start: Int, count: Int) {
        for (i in start until start + count) {
            val view = getChildAt(i)
            addDisappearingFragmentView(view)
        }
        super.removeViews(start, count)
    }

    override fun removeViewsInLayout(start: Int, count: Int) {
        for (i in start until start + count) {
            val view = getChildAt(i)
            addDisappearingFragmentView(view)
        }
        super.removeViewsInLayout(start, count)
    }

    override fun removeAllViewsInLayout() {
        for (i in childCount - 1 downTo 0) {
            val view = getChildAt(i)
            addDisappearingFragmentView(view)
        }
        super.removeAllViewsInLayout()
    }

    override fun removeDetachedView(child: View, animate: Boolean) {
        if (animate) {
            addDisappearingFragmentView(child)
        }
        super.removeDetachedView(child, animate)
    }

    private fun addDisappearingFragmentView(v: View) {
        if (mTransitioningFragmentViews != null && mTransitioningFragmentViews!!.contains(v)) {
            if (mDisappearingFragmentChildren == null) {
                mDisappearingFragmentChildren = ArrayList()
            }
            mDisappearingFragmentChildren!!.add(v)
        }
    }

}