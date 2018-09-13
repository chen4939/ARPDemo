@file:JvmName("Extends")

package com.bftv.arpinfo

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Handler
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *
 * @author Junpu
 * @time 2018/8/27 16:01
 */

fun postDelay(r: () -> Unit, delayMillis: Long) = Handler().postDelayed(r, delayMillis)
fun postDelay(r: Runnable, delayMillis: Long) = Handler().postDelayed(r, delayMillis)

fun Context.getInteger(id: Int): Int = resources.getInteger(id)
fun Fragment.getInteger(id: Int): Int = resources.getInteger(id)

fun Context.launch(cls: Class<*>) {
    val intent = Intent(this, cls)
    startActivity(intent)
}

fun Fragment.launch(cls: Class<*>) {
    activity?.launch(cls)
}

fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE
fun View.isGone(): Boolean = visibility == View.GONE
fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

fun View.setLayoutSize(width: Int, height: Int) {
    layoutParams.width = width
    layoutParams.height = height
}

fun View.setLayoutSize(size: Int) {
    layoutParams.width = size
    layoutParams.height = size
}

fun Fragment.isPortrait(): Boolean = activity?.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

operator fun ViewGroup.get(position: Int): View = getChildAt(position)

fun Context.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
        LayoutInflater.from(this).inflate(resource, root, attachToRoot)

fun <T> List<T>?.isNullorEmpty(): Boolean = this == null || isEmpty()
fun <T> List<T>?.isNotNullorEmpty(): Boolean = !isNullorEmpty()

fun <K, V> HashMap<K, V>.append(key: K, value: V): HashMap<K, V> {
    put(key, value)
    return this
}

fun <K, V> MutableMap<K, V>.append(key: K, value: V): MutableMap<K, V> {
    put(key, value)
    return this
}

fun <K, V> HashMap<K, V>.appendAll(map: Map<K, V>): HashMap<K, V> {
    putAll(map)
    return this
}

fun <K, V> HashMap<K, V>.appendNotNull(key: K, value: V?): HashMap<K, V> {
    value?.let { put(key, value) }
    return this
}

fun Context.color(@ColorRes id: Int): Int {
    return if (VERSION.SDK_INT >= VERSION_CODES.M) {
        resources.getColor(id, null)
    } else {
        resources.getColor(id)
    }
}

/**
 * 获取View在屏幕中的位置坐标
 */
fun View.location(): Rect {
    val rect = Rect()
    getGlobalVisibleRect(rect)
    return rect
}

/**
 * 设置SwipeRefreshLayout立即刷新，并执行指定刷新动作
 */
fun SwipeRefreshLayout.doRefresh(d: () -> Unit) {
    isRefreshing = true
    d.invoke()
}

fun CharSequence?.isNotNullOrBlank(): Boolean = !isNullOrBlank()
fun CharSequence?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()
