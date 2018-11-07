package app.dg.giang.dgplayer.utils;

import android.util.Log;


public class Logs {
    public static boolean DEBUG = true;
    public static String START_TAG = "";

    public Logs() {
    }

    public static void d(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.d(START_TAG + stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void w(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.w(START_TAG + stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void i(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.i(START_TAG + stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void e(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.e(START_TAG + stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), message);
        }

    }


    public static void e(Exception e) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.e(START_TAG + stackTraceElement.getFileName() + "/" + stackTraceElement.getMethodName() +
                    "/" + stackTraceElement.getLineNumber(), e.toString());
        }

    }
}
