package com.android.server.core.jarjar.kotlin.jvm.internal;

import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class Intrinsics {
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
            StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("Parameter specified as non-null is null: method ", stackTraceElement.getClassName(), ".", stackTraceElement.getMethodName(), ", parameter ");
            m.append(str);
            NullPointerException nullPointerException = new NullPointerException(m.toString());
            String name2 = Intrinsics.class.getName();
            StackTraceElement[] stackTrace2 = nullPointerException.getStackTrace();
            int length = stackTrace2.length;
            int i2 = -1;
            for (int i3 = 0; i3 < length; i3++) {
                if (name2.equals(stackTrace2[i3].getClassName())) {
                    i2 = i3;
                }
            }
            nullPointerException.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace2, i2 + 1, length));
            throw nullPointerException;
        }
    }
}
