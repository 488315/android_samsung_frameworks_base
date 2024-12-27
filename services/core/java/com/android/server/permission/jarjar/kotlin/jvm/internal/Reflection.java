package com.android.server.permission.jarjar.kotlin.jvm.internal;

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
