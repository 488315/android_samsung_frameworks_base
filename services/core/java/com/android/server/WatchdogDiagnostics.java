package com.android.server;

import android.util.Log;
import android.util.LogWriter;
import com.android.server.Watchdog;
import dalvik.system.AnnotatedStackTraceElement;
import dalvik.system.VMStack;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

/* loaded from: classes.dex */
public abstract class WatchdogDiagnostics {
    public static String getBlockedOnString(Object obj) {
        return String.format("- waiting to lock <0x%08x> (a %s)", Integer.valueOf(System.identityHashCode(obj)), obj.getClass().getName());
    }

    public static String getLockedString(Object obj) {
        return String.format("- locked <0x%08x> (a %s)", Integer.valueOf(System.identityHashCode(obj)), obj.getClass().getName());
    }

    public static boolean printAnnotatedStack(Thread thread, PrintWriter printWriter) {
        AnnotatedStackTraceElement[] annotatedThreadStackTrace = VMStack.getAnnotatedThreadStackTrace(thread);
        if (annotatedThreadStackTrace == null) {
            return false;
        }
        printWriter.println("!@ " + thread.getName() + " annotated stack trace:");
        int length = annotatedThreadStackTrace.length;
        for (int i = 0; i < length; i++) {
            AnnotatedStackTraceElement annotatedStackTraceElement = annotatedThreadStackTrace[i];
            printWriter.println("!@    at " + annotatedStackTraceElement.getStackTraceElement());
            if (annotatedStackTraceElement.getBlockedOn() != null) {
                printWriter.println("!@    " + getBlockedOnString(annotatedStackTraceElement.getBlockedOn()));
            }
            if (annotatedStackTraceElement.getHeldLocks() != null) {
                for (Object obj : annotatedStackTraceElement.getHeldLocks()) {
                    printWriter.println("!@    " + getLockedString(obj));
                }
            }
        }
        return true;
    }

    public static void diagnoseCheckers(List list) {
        PrintWriter printWriter = new PrintWriter((Writer) new LogWriter(5, "Watchdog", 4), true);
        Log.printlns(4, 6, "Watchdog", "PLATFORM WATCHDOG RESET", null);
        for (int i = 0; i < list.size(); i++) {
            Thread thread = ((Watchdog.HandlerChecker) list.get(i)).getThread();
            if (!printAnnotatedStack(thread, printWriter)) {
                Log.printlns(4, 6, "Watchdog", thread.getName() + " stack trace:", null);
                StackTraceElement[] stackTrace = thread.getStackTrace();
                int length = stackTrace.length;
                for (int i2 = 0; i2 < length; i2++) {
                    Log.printlns(4, 6, "Watchdog", " at " + stackTrace[i2], null);
                }
            }
        }
    }
}
