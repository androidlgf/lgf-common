package com.cn.lgf.common.aspect.log.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

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
@Target({TYPE, METHOD, CONSTRUCTOR})
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
@Retention(CLASS)
/**
 * AnnotationRetention.SOURCE：不存储在编译后的 Class 文件。
 * AnnotationRetention.BINARY：存储在编译后的 Class 文件，但是反射不可见。
 * AnnotationRetention.RUNTIME：存储在编译后的 Class 文件，反射可见。
 */
public @interface DebugLog {
}
