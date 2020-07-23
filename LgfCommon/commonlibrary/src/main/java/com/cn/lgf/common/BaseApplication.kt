package com.cn.lgf.common

import android.app.Application
import android.content.Context

/**
 *
 * @ProjectName:    LgfCommon
 * @Package:        com.cn.lgf.common
 * @ClassName:      MyApp
 * @Description:     java类作用描述
 * @Author:         作者名
 * @CreateDate:     2020/7/13 12:52 下午
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/7/13 12:52 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class BaseApplication() : Application() {
    companion object {
        private var sInstance: BaseApplication? = null
        fun getInstance(): BaseApplication? {
            return sInstance;
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }
}