package kotlin.internal;

import kotlin.internal.jdk7.JDK7PlatformImplementations;
import kotlin.internal.jdk8.JDK8PlatformImplementations;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class PlatformImplementationsKt {
    public static final PlatformImplementations IMPLEMENTATIONS;

    static {
        PlatformImplementations platformImplementations;
        try {
            Object newInstance = JDK8PlatformImplementations.class.newInstance();
            try {
                try {
                    platformImplementations = (PlatformImplementations) newInstance;
                } catch (ClassNotFoundException unused) {
                    Object newInstance2 = JDK7PlatformImplementations.class.newInstance();
                    try {
                        try {
                            platformImplementations = (PlatformImplementations) newInstance2;
                        } catch (ClassNotFoundException unused2) {
                            platformImplementations = new PlatformImplementations();
                        }
                    } catch (ClassCastException e) {
                        ClassLoader classLoader = newInstance2.getClass().getClassLoader();
                        ClassLoader classLoader2 = PlatformImplementations.class.getClassLoader();
                        if (Intrinsics.areEqual(classLoader, classLoader2)) {
                            throw e;
                        }
                        throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader + ", base type classloader: " + classLoader2, e);
                    }
                }
            } catch (ClassCastException e2) {
                ClassLoader classLoader3 = newInstance.getClass().getClassLoader();
                ClassLoader classLoader4 = PlatformImplementations.class.getClassLoader();
                if (Intrinsics.areEqual(classLoader3, classLoader4)) {
                    throw e2;
                }
                throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader3 + ", base type classloader: " + classLoader4, e2);
            }
        } catch (ClassNotFoundException unused3) {
            Object newInstance3 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
            try {
                try {
                    platformImplementations = (PlatformImplementations) newInstance3;
                } catch (ClassCastException e3) {
                    ClassLoader classLoader5 = newInstance3.getClass().getClassLoader();
                    ClassLoader classLoader6 = PlatformImplementations.class.getClassLoader();
                    if (Intrinsics.areEqual(classLoader5, classLoader6)) {
                        throw e3;
                    }
                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader5 + ", base type classloader: " + classLoader6, e3);
                }
            } catch (ClassNotFoundException unused4) {
                Object newInstance4 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                try {
                    platformImplementations = (PlatformImplementations) newInstance4;
                } catch (ClassCastException e4) {
                    ClassLoader classLoader7 = newInstance4.getClass().getClassLoader();
                    ClassLoader classLoader8 = PlatformImplementations.class.getClassLoader();
                    if (Intrinsics.areEqual(classLoader7, classLoader8)) {
                        throw e4;
                    }
                    throw new ClassNotFoundException("Instance class was loaded from a different classloader: " + classLoader7 + ", base type classloader: " + classLoader8, e4);
                }
            }
        }
        IMPLEMENTATIONS = platformImplementations;
    }
}
