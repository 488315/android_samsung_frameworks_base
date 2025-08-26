package com.google.protobuf;

/* loaded from: classes4.dex */
public final class Android {
    public static final Class MEMORY_CLASS;

    static {
        Class<?> cls;
        try {
            cls = Class.forName("libcore.io.Memory");
        } catch (Throwable unused) {
            cls = null;
        }
        MEMORY_CLASS = cls;
    }

    private Android() {
    }
}
