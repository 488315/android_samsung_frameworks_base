package com.android.server.permission.jarjar.kotlin.jvm.internal;

import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.permission.jarjar.kotlin.UninitializedPropertyAccessException;

import java.util.Arrays;

public abstract class Intrinsics {
    public static boolean areEqual(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public static void checkNotNull(Object obj) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException();
        sanitizeStackTrace(nullPointerException);
        throw nullPointerException;
    }

    public static void checkNotNull(String str, Object obj) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException(str);
        sanitizeStackTrace(nullPointerException);
        throw nullPointerException;
    }

    public static void checkNotNullExpressionValue(String str, Object obj) {
        if (obj != null) {
            return;
        }
        NullPointerException nullPointerException =
                new NullPointerException(str.concat(" must not be null"));
        sanitizeStackTrace(nullPointerException);
        throw nullPointerException;
    }

    public static void checkNotNullParameter(String str, Object obj) {
        if (obj == null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String name = Intrinsics.class.getName();
            int i = 0;
            while (!stackTrace[i].getClassName().equals(name)) {
                i++;
            }
            while (stackTrace[i].getClassName().equals(name)) {
                i++;
            }
            StackTraceElement stackTraceElement = stackTrace[i];
            StringBuilder m =
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "Parameter specified as non-null is null: method ",
                            stackTraceElement.getClassName(),
                            ".",
                            stackTraceElement.getMethodName(),
                            ", parameter ");
            m.append(str);
            NullPointerException nullPointerException = new NullPointerException(m.toString());
            sanitizeStackTrace(nullPointerException);
            throw nullPointerException;
        }
    }

    public static void sanitizeStackTrace(Throwable th) {
        String name = Intrinsics.class.getName();
        StackTraceElement[] stackTrace = th.getStackTrace();
        int length = stackTrace.length;
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            if (name.equals(stackTrace[i2].getClassName())) {
                i = i2;
            }
        }
        th.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i + 1, length));
    }

    public static void throwUninitializedPropertyAccessException(String str) {
        UninitializedPropertyAccessException uninitializedPropertyAccessException =
                new UninitializedPropertyAccessException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "lateinit property ", str, " has not been initialized"));
        sanitizeStackTrace(uninitializedPropertyAccessException);
        throw uninitializedPropertyAccessException;
    }
}
