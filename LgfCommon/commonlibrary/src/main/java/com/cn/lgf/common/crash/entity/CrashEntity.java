package com.cn.lgf.common.crash.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.crash.entity
 * @ClassName: CrashEntity
 * @Description: Crash数据
 * @Author: 作者名
 * @CreateDate: 2020/8/13 4:55 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/13 4:55 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 * @Entity 实体类的标注，将会在数据库映射成对应的表结构，tableName不写，将会是实体类默认
 * @PrimaryKey 主键
 * @ColumnInfo 字段属性对应列的关系 没有该注解时，属性名将一一对应字段名
 * @Ignore 属性值将不会序列化为字段，与@java.beans.Transient意义一致
 */
@Entity(tableName = "crash_info")
public class CrashEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    //Crash错误信息
    public String crashInfo;
    //设备相关信息
    public String deviceInfo;
    //奔溃时间戳
    public long timestamp;
    //其它信息 预留
    public String otherInfo;
    //创建时间
    public Date createTime;
}
