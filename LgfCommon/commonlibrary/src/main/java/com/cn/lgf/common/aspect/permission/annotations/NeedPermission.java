package com.cn.lgf.common.aspect.permission.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.aspect.permission.annotations
 * @ClassName: CheckPermission
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/27 11:47 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/27 11:47 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.aspect.weaving
 * @ClassName: DebugLog
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2020/7/24 5:15 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/24 5:15 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
/**
 * AnnotationTarget.CLASS：类，接口或对象，注解类也包括在内。
 * AnnotationTarget.ANNOTATION_CLASS：只有注解类。
 * AnnotationTarget.TYPE_PARAMETER：Generic type parameter (unsupported yet)通用类型参数（还不支持）。
 * AnnotationTarget.PROPERTY：属性。
 * AnnotationTarget.FIELD：字段，包括属性的支持字段。
 * AnnotationTarget.LOCAL_VARIABLE：局部变量。
 * AnnotationTarget.VALUE_PARAMETER：函数或构造函数的值参数。
 * AnnotationTarget.CONSTRUCTOR：仅构造函数（主函数或者第二函数）。
 * AnnotationTarget.FUNCTION：方法（不包括构造函数）。
 * AnnotationTarget.PROPERTY_GETTER：只有属性的 getter。
 * AnnotationTarget.PROPERTY_SETTER：只有属性的 setter。
 * AnnotationTarget.TYPE：类型使用。
 * AnnotationTarget.EXPRESSION：任何表达式。
 * AnnotationTarget.FILE：文件。
 * AnnotationTarget.TYPEALIAS：@SinceKotlin("1.1") 类型别名，Kotlin1.1已可用。
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * AnnotationRetention.SOURCE：不存储在编译后的 Class 文件。
 * AnnotationRetention.BINARY：存储在编译后的 Class 文件，但是反射不可见。
 * AnnotationRetention.RUNTIME：存储在编译后的 Class 文件，反射可见。
 */
public @interface NeedPermission {
    /**
     * 你所申请的权限列表，例如 {@link android.Manifest.permission#READ_CONTACTS}
     *
     * @return 权限列表
     * @see android.Manifest.permission
     */
    String[] permissions() default "";

    /**
     * 合理性解释内容
     *
     * @return 合理性解释内容
     */
    String rationalMessage() default "";

    /**
     * 合理性解释文本资源ID
     *
     * @return
     */
    int rationalMsgResId() default 0;


    /**
     * 合理性解释按钮文本
     *
     * @return 合理性解释按钮文本
     */
    String rationalButton() default "";

    /**
     * 合理性解释按钮文本资源ID
     *
     * @return
     */
    int rationalBtnResId() default 0;

    /**
     * 权限禁止文本内容
     *
     * @return 权限禁止文本内容
     */
    String deniedMessage() default "";

    /**
     * 权限禁止文本资源ID
     *
     * @return
     */
    int deniedMsgResId() default 0;

    /**
     * 权限禁止按钮文本
     *
     * @return 权限禁止按钮文本
     */
    String deniedButton() default "";

    /**
     * 权限禁止按钮文本资源ID
     *
     * @return
     */
    int deniedBtnResId() default 0;

    /**
     * app设置按钮文本
     *
     * @return
     */
    String settingText() default "";

    /**
     * app设置按钮文本资源ID
     *
     * @return
     */
    int settingResId() default 0;

    /**
     * 是否显示跳转到应用权限设置界面
     *
     * @return 是否显示跳转到应用权限设置界面
     */
    boolean needGotoSetting() default false;

    /**
     * 是否无视权限，程序正常往下走
     *
     * @return 是否无视权限，程序正常往下走
     */
    boolean runIgnorePermission() default false;
}
