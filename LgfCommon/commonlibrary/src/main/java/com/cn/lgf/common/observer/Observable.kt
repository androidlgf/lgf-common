package com.cn.lgf.common.observer

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.collection.ArrayMap
import java.lang.ref.WeakReference


/**
 *
 * @ProjectName:    LgfCommon
 * @Package:        com.cn.lgf.common.observer
 * @ClassName:      Observable
 * @Description:     java类作用描述
 * @Author:         作者名
 * @CreateDate:     2020/7/13 12:21 上午
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/7/13 12:21 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class Observable {
    private var observerMap: ArrayMap<String, ArrayList<WeakReference<Observer>>> = ArrayMap()

    /**
     * @method  addObserver
     * @description 添加观察者
     * @date: 2020/7/13 11:17 上午
     * @author: lgf
     * @param  tag action标识
     * @param  Observer 观察者
     * @return
     */
    fun addObserver(tag: String?, observer: Observer?) {
        if (TextUtils.isEmpty(tag)) {
            throw NullPointerException("Observer tag can't be null")
        }
        if (observer == null) {
            throw NullPointerException("observer == null")
        }
        synchronized(Observable::class.java) {
            if (observerMap.containsKey(tag)) {
                val list: ArrayList<WeakReference<Observer>>? = observerMap[tag]
                if (list != null) {
                    var needToAdd = true
                    for (weakReference in list) {
                        if (weakReference.get() != null && weakReference.get() === observer) {
                            needToAdd = false
                            break
                        }
                    }
                    if (needToAdd) {
                        list.add(WeakReference(observer))
                    }
                    observerMap[tag] = list
                    return
                }
            }
            val listObserver: ArrayList<WeakReference<Observer>> = ArrayList()
            listObserver.add(WeakReference(observer))
            observerMap.put(tag, listObserver)
        }
    }

    /**
     * @method  sendToTarget
     * @description 发送消息
     * @date: 2020/7/13 11:14 上午
     * @author: lgf
     * @param   tag 标识符
     * @return
     */
    fun sendToTarget(tag: String?) {
        sendToTarget(tag, null)
    }

    /**
     * @method
     * @description 发送消息
     * @date: 2020/7/13 11:14 上午
     * @author: lgf
     * @param  tag 标识符
     * @return
     */
    fun sendToTarget(tag: String?, bundle: Bundle?) {
        if (!observerMap.containsKey(tag)) {
            return
        }
        val list = observerMap[tag] ?: return
        val cloneList: ArrayList<WeakReference<Observer>> =
            list.clone() as ArrayList<WeakReference<Observer>>
        synchronized(Observable::class.java) {
            try {
                for (observerWeakReference in cloneList) {
                    if (observerWeakReference != null) {
                        observerWeakReference.get()?.notifyChanged(this, tag, bundle)
                    }
                }
            } catch (e: Exception) {
                Log.e(Observable::class.java.name, e.toString())
            }
        }
    }

    /**
     * @method removeObserver
     * @description 删除观察者
     * @date: 2020/7/13 10:43 上午
     * @author: lgf
     * @param tag  action 标识
     * @param observer 观察者
     * @return Boolean 是否删除成功
     */
    fun removeObserver(tag: String?, observer: Observer): Boolean {
        if (!observerMap.containsKey(tag)) {
            return false
        }
        val list: ArrayList<WeakReference<Observer>>? = observerMap[tag]
        if (list == null || list.isEmpty()) {
            return false
        }
        synchronized(Observable::class.java) {
            var removeTag: WeakReference<Observer>? = null
            for (weakReference in list) {
                if (weakReference.get() != null && weakReference.get() === observer) {
                    removeTag = weakReference
                    break
                }
            }
            return removeTag != null && list.remove(removeTag)
        }
    }

    /**
     * @method removeObserverByTag
     * @description 删除观察者
     * @date: 2020/7/13 12:48 上午
     * @author: lgf
     * @param tag  action 标识
     * @return Boolean 是否删除成功
     */
    fun removeObserverByTag(tag: String?): Boolean {
        synchronized(
            Observable::class.java
        ) { return observerMap.containsKey(tag) && observerMap.remove(tag) != null }
    }

    /**
     * @method  removeAllObserver
     * @description 清空注销
     * @date: 2020/7/13 10:46 上午
     * @author: lgf
     * @param
     * @return
     */
    fun removeAllObserver() {
        synchronized(Observable::class.java) { observerMap.clear() }
    }

}