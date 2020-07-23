package com.cn.lgf.common.observer

import android.util.Log
import android.util.SparseArray


class ObservableManager private constructor() {
    private val TAG = "ObservableManager"
    private var mObservableSparseArray: SparseArray<Observable>? = null

    init {
        mObservableSparseArray = SparseArray()
    }

    /**
     * @method  getInstance
     * @description 单例获取ObservableManager
     * @date: 2020/7/13 11:20 上午
     * @author: lgf
     */
    companion object {
        private var sInstance: ObservableManager? = null

        @Synchronized
        fun getInstance(): ObservableManager? {
            if (sInstance == null) {
                sInstance = ObservableManager()
            }
            return sInstance
        }
        fun getDefaultObserable(): Observable? {
            return ObservableManager.getInstance()?.getObservable(Observable::class.java)
        }
    }

    fun <T : Observable?> getObservable(t: Class<T>): Observable? {
        var observable: Observable? = mObservableSparseArray!![t.hashCode()]
        if (observable == null) {
            try {
                observable = t.newInstance()
                mObservableSparseArray!!.put(t.hashCode(), observable)
            } catch (e: InstantiationException) {
                Log.e(TAG, e.message)
            } catch (e: IllegalAccessException) {
                Log.e(TAG, e.message)
            }
        }
        // 仅在内部使用一次强制类型转换，外部调用者不再需要强制类型转换
        return observable
    }
}