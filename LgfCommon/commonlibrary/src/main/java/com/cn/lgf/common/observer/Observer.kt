package com.cn.lgf.common.observer

import android.os.Bundle

/***
 * 观察者
 */
interface Observer {
    fun notifyChanged(observable: Observable?, tag: String?, bundle: Bundle?)
}