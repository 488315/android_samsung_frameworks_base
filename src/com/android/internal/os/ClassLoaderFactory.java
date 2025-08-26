package com.android.internal.os;

import android.os.Trace;
import dalvik.system.DelegateLastClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ClassLoaderFactory {
    private static final String PATH_CLASS_LOADER_NAME = PathClassLoader.class.getName();
    private static final String DEX_CLASS_LOADER_NAME = DexClassLoader.class.getName();
    private static final String DELEGATE_LAST_CLASS_LOADER_NAME = DelegateLastClassLoader.class.getName();

    private static native String createClassloaderNamespace(ClassLoader classLoader, int i, String str, String str2, boolean z, String str3, String str4);

    private ClassLoaderFactory() {
    }

    public static String getPathClassLoaderName() {
        return PATH_CLASS_LOADER_NAME;
    }

    public static boolean isValidClassLoaderName(String str) {
        if (str != null) {
            return isPathClassLoaderName(str) || isDelegateLastClassLoaderName(str);
        }
        return false;
    }

    public static boolean isPathClassLoaderName(String str) {
        return str == null || PATH_CLASS_LOADER_NAME.equals(str) || DEX_CLASS_LOADER_NAME.equals(str);
    }

    public static boolean isDelegateLastClassLoaderName(String str) {
        return DELEGATE_LAST_CLASS_LOADER_NAME.equals(str);
    }

    public static ClassLoader createClassLoader(String str, String str2, ClassLoader classLoader, String str3, List<ClassLoader> list, List<ClassLoader> list2) {
        ClassLoader[] classLoaderArr = list == null ? null : (ClassLoader[]) list.toArray(new ClassLoader[list.size()]);
        ClassLoader[] classLoaderArr2 = list2 != null ? (ClassLoader[]) list2.toArray(new ClassLoader[list2.size()]) : null;
        if (isPathClassLoaderName(str3)) {
            return new PathClassLoader(str, str2, classLoader, classLoaderArr, classLoaderArr2);
        }
        if (isDelegateLastClassLoaderName(str3)) {
            return new DelegateLastClassLoader(str, str2, classLoader, classLoaderArr, classLoaderArr2);
        }
        throw new AssertionError("Invalid classLoaderName: " + str3);
    }

    public static ClassLoader createClassLoader(String str, String str2, String str3, ClassLoader classLoader, int i, boolean z, String str4) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ALL");
        return createClassLoader(str, str2, str3, classLoader, i, z, str4, null, arrayList, null);
    }

    public static ClassLoader createClassLoader(String str, String str2, String str3, ClassLoader classLoader, int i, boolean z, String str4, List<ClassLoader> list, List<String> list2, List<ClassLoader> list3) {
        String strJoin;
        ClassLoader classLoaderCreateClassLoader = createClassLoader(str, str2, classLoader, str4, list, list3);
        if (list2 == null) {
            strJoin = "";
        } else {
            strJoin = String.join(":", list2);
        }
        Trace.traceBegin(64L, "createClassloaderNamespace");
        String strCreateClassloaderNamespace = createClassloaderNamespace(classLoaderCreateClassLoader, i, str2, str3, z, str, strJoin);
        Trace.traceEnd(64L);
        if (strCreateClassloaderNamespace == null) {
            return classLoaderCreateClassLoader;
        }
        throw new UnsatisfiedLinkError("Unable to create namespace for the classloader " + classLoaderCreateClassLoader + ": " + strCreateClassloaderNamespace);
    }
}
