package com.android.server;

import dalvik.system.AnnotatedStackTraceElement;
import dalvik.system.VMStack;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class WatchdogDiagnostics {
    public static boolean printAnnotatedStack(Thread thread, PrintWriter printWriter) {
        AnnotatedStackTraceElement[] annotatedThreadStackTrace =
                VMStack.getAnnotatedThreadStackTrace(thread);
        if (annotatedThreadStackTrace == null) {
            return false;
        }
        printWriter.println("!@ " + thread.getName() + " annotated stack trace:");
        int length = annotatedThreadStackTrace.length;
        for (int i = 0; i < length; i++) {
            AnnotatedStackTraceElement annotatedStackTraceElement = annotatedThreadStackTrace[i];
            printWriter.println("!@    at " + annotatedStackTraceElement.getStackTraceElement());
            if (annotatedStackTraceElement.getBlockedOn() != null) {
                Object blockedOn = annotatedStackTraceElement.getBlockedOn();
                printWriter.println(
                        "!@    "
                                .concat(
                                        String.format(
                                                "- waiting to lock <0x%08x> (a %s)",
                                                Integer.valueOf(System.identityHashCode(blockedOn)),
                                                blockedOn.getClass().getName())));
            }
            if (annotatedStackTraceElement.getHeldLocks() != null) {
                for (Object obj : annotatedStackTraceElement.getHeldLocks()) {
                    printWriter.println(
                            "!@    "
                                    .concat(
                                            String.format(
                                                    "- locked <0x%08x> (a %s)",
                                                    Integer.valueOf(System.identityHashCode(obj)),
                                                    obj.getClass().getName())));
                }
            }
        }
        return true;
    }
}
