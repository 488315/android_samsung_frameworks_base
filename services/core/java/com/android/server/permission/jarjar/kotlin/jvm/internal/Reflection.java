package com.android.server.permission.jarjar.kotlin.jvm.internal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Reflection {
    public static final ReflectionFactory factory;

    static {
        ReflectionFactory reflectionFactory = null;
        try {
            reflectionFactory =
                    (ReflectionFactory)
                            Class.forName(
                                            "com.android.server.permission.jarjar.kotlin.reflect.jvm.internal.ReflectionFactoryImpl")
                                    .newInstance();
        } catch (ClassCastException
                | ClassNotFoundException
                | IllegalAccessException
                | InstantiationException unused) {
        }
        if (reflectionFactory == null) {
            reflectionFactory = new ReflectionFactory();
        }
        factory = reflectionFactory;
    }
}
