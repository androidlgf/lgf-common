package com.cn.lgf.common.crash.entity;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.crash.entity
 * @ClassName: DateConverter
 * @Description: Room保存Date转换
 * @Author: 作者名
 * @CreateDate: 2020/8/14 1:40 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/8/14 1:40 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DateConverter {
    @TypeConverter
    public static Date revertDate(String value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(value, new ParsePosition(0));
    }

    @TypeConverter
    public static String converterDate(Date value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(value);
    }
}
