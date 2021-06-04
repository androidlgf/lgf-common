package com.cn.lgf.common.http.debug;

import android.util.Log;

/**
 * @ProjectName: LgfCommon
 * @Package: com.cn.lgf.common.utils
 * @ClassName: DebugLog
 * @Description: DebugLog 日志打印 默认线上环境不输出
 * @Author: 作者名
 * @CreateDate: 2020/7/27 4:40 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/7/27 4:40 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class HttpLog {
    //是否输出
    public static boolean needLog = false;
    //开发环境判断  DebugState.isDebug() || DebugState.isPreView()
    public static boolean isDebug = false;
    //Log过滤Tag
    public static volatile String TAG = "HttpLog";
    //Log Max Length
    private static int MAX_LENGTH = 2000;

    /***
     * 自定义Log过滤Tag
     * @param tag
     */
    public static void setTag(String tag) {
        TAG = tag;
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (isDebug || needLog) {
            Log.v(TAG + ":" + tag, buildMessage(msg));
        }
    }

    public static void v(String msg) {
        if (isDebug || needLog) {
            Log.v(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void v(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.v(TAG + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void v(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.v(TAG, buildMessage(msg), throwable);
        }
    }

    public static void e(String msg) {
        if (isDebug || needLog) {
            Log.e(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a DEBGU log message.
     *
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (isDebug || needLog) {
            Log.d(TAG + ":" + tag, buildMessage(msg));
        }
    }

    public static void d(String msg) {
        if (isDebug || needLog) {
            Log.d(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a DEBGU log message and log the exception.
     *
     * @param msg       The message you would like logge,d.
     * @param throwable An exception to log
     */
    public static void d(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.d(TAG + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void d(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.d(TAG, buildMessage(msg), throwable);
        }
    }

    /**
     * Send a INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (isDebug || needLog) {
            int strLength = msg.length();
            int start = 0;
            int end = MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.i(TAG + ":" + tag + i, buildMessage(msg.substring(start, end)));
                    start = end;
                    end = end + MAX_LENGTH;
                } else {
                    Log.i(TAG + ":" + tag + i, buildMessage(msg.substring(start, strLength)));
                    break;
                }
            }
        }
    }

    public static void i(String msg) {
        if (isDebug || needLog) {
            Log.i(TAG + ":", buildMessage(msg));
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void i(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.i(TAG + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void i(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.i(TAG, buildMessage(msg), throwable);
        }
    }

    /**
     * Send a WARN log message.
     *
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (isDebug || needLog) {
            Log.w(TAG + ":" + tag, buildMessage(msg));
        }
    }

    public static void w(String msg) {
        if (isDebug || needLog) {
            Log.w(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void w(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.w(TAG + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void w(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.w(TAG, buildMessage(msg), throwable);
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (isDebug || needLog) {
            Log.e(TAG + ":" + tag, buildMessage(msg));
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.e(TAG + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void e(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.e(TAG, buildMessage(msg), throwable);
        }
    }

    /**
     * 构建消息Log
     *
     * @param msg 想要记录的消息Log.
     * @return Message String
     */
    private static String buildMessage(String msg) {
        StackTraceElement traceElement = new Throwable().fillInStackTrace().getStackTrace()[2];
        return new StringBuilder().append(traceElement.getClassName()).append(".")
                .append(traceElement.getMethodName()).append("()::").append(msg).toString();
    }
}
