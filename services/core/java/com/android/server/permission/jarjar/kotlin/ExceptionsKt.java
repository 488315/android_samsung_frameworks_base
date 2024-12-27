package com.android.server.permission.jarjar.kotlin;

import com.android.server.permission.jarjar.kotlin.internal.PlatformImplementations$ReflectThrowable;
import com.android.server.permission.jarjar.kotlin.internal.jdk7.JDK7PlatformImplementations$ReflectSdkVersion;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

import java.lang.reflect.Method;

public abstract class ExceptionsKt {
    public static void addSuppressed(Throwable th, Throwable th2) {
        Intrinsics.checkNotNullParameter("<this>", th);
        if (th != th2) {
            Integer num = JDK7PlatformImplementations$ReflectSdkVersion.sdkVersion;
            if (num == null || num.intValue() >= 19) {
                th.addSuppressed(th2);
                return;
            }
            Method method = PlatformImplementations$ReflectThrowable.addSuppressed;
            if (method != null) {
                method.invoke(th, th2);
            }
        }
    }
}
