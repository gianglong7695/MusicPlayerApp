package app.dg.giang.dgplayer.utils;

import android.util.Log;


public class Logs {
    public static boolean DEBUG = true;

    public Logs() {
    }

    public static void d(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.d(stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void w(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.w(stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void i(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.i(stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void e(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.e(stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), message);
        }

    }
}
