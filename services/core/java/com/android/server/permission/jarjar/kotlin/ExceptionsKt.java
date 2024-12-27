package com.android.server.permission.jarjar.kotlin;

import com.android.server.permission.jarjar.kotlin.internal.PlatformImplementations$ReflectThrowable;
import com.android.server.permission.jarjar.kotlin.internal.jdk7.JDK7PlatformImplementations$ReflectSdkVersion;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import java.lang.reflect.Method;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
