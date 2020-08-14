package com.cn.lgf.common.crash.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cn.lgf.common.crash.entity.CrashEntity;

import java.util.List;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.crash.dao
 * @ClassName: CrashDao
 * @Description: 定义查询接口，需要的查询的方法都需要提前定义
 * @Author: 作者名
 * @CreateDate: 2020/8/13 5:06 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/13 5:06 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 * @dao 标记接口为数据访问对象，将会自动生成其实现类来查询数据
 * @Query 查询接口
 * @Insert 插入接口
 * @Update更新接口
 * @Delete删除接口
 */
@Dao
public interface CrashDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCrash(CrashEntity... crashEntities);

    @Query("SELECT * FROM crash_info ORDER BY timestamp DESC")
    List<CrashEntity> queryAllCrashEntities();
}
