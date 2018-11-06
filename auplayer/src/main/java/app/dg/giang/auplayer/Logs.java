package app.dg.giang.auplayer;

import android.util.Log;

public class Logs {
    public static boolean DEBUG = true;

    public Logs() {
    }

    public static void d(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.d(stackTraceElement.getFileName() + " in " + stackTraceElement.getMethodName() +
                    " at line: " + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void w(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.w(stackTraceElement.getFileName() + " in " + stackTraceElement.getMethodName() +
                    " at line: " + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void i(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.i(stackTraceElement.getFileName() + " in " + stackTraceElement.getMethodName() +
                    " at line: " + stackTraceElement.getLineNumber(), message);
        }

    }

    public static void e(String message) {
        StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
        if (DEBUG) {
            Log.e(stackTraceElement.getFileName() + " in " + stackTraceElement.getMethodName() +
                    " at line: " + stackTraceElement.getLineNumber(), message);
        }

    }
}
