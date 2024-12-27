package com.android.server.permission.jarjar.kotlin.internal.jdk7;

public abstract class JDK7PlatformImplementations$ReflectSdkVersion {
    public static final Integer sdkVersion;

    static {
        Integer num;
        Object obj;
        Integer num2 = null;
        try {
            obj = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Throwable unused) {
        }
        if (obj instanceof Integer) {
            num = (Integer) obj;
            if (num != null && num.intValue() > 0) {
                num2 = num;
            }
            sdkVersion = num2;
        }
        num = null;
        if (num != null) {
            num2 = num;
        }
        sdkVersion = num2;
    }
}
