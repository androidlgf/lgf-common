package com.cn.lgf.common.crash;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.cn.lgf.common.crash.dao.CrashDao;
import com.cn.lgf.common.crash.entity.CrashEntity;
import com.cn.lgf.common.crash.entity.DateConverter;
import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.crash
 * @ClassName: CrashDataBase
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/8/13 5:44 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/13 5:44 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 * @Database标注为一个RoomDatabase 里面有多少张表，entities里面也必须都填写
 * @TypeConverters 其它类型转基本类型
 */
@Database(entities = {CrashEntity.class}, version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class CrashDataBase extends RoomDatabase {
    private static CrashDataBase instance;

    public static CrashDataBase getInstance(Application pApplication) {
        if (null == instance) {
            instance = create(pApplication);
        }
        return instance;
    }

    private static CrashDataBase create(Application pApplication) {

        return Room.databaseBuilder(pApplication, CrashDataBase.class, "db_crash")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract CrashDao crashDao();
}
