package com.android.internal.os;

import android.os.Build;
import android.util.ArrayMap;
import dalvik.system.PathClassLoader;

/* loaded from: classes5.dex */
public final class SystemServerClassLoaderFactory {
    private static final ArrayMap<String, PathClassLoader> sLoadedPaths = new ArrayMap<>();

    static PathClassLoader createClassLoader(String str, ClassLoader classLoader) {
        ArrayMap<String, PathClassLoader> arrayMap = sLoadedPaths;
        if (arrayMap.containsKey(str)) {
            throw new IllegalStateException("A ClassLoader for " + str + " already exists");
        }
        PathClassLoader pathClassLoader = (PathClassLoader) ClassLoaderFactory.createClassLoader(str, null, null, classLoader, Build.VERSION.SDK_INT, true, null);
        arrayMap.put(str, pathClassLoader);
        return pathClassLoader;
    }

    public static PathClassLoader getOrCreateClassLoader(String str, ClassLoader classLoader, boolean z) {
        PathClassLoader pathClassLoader = sLoadedPaths.get(str);
        if (pathClassLoader != null) {
            return pathClassLoader;
        }
        if (!allowClassLoaderCreation(str, z)) {
            throw new RuntimeException("Creating a ClassLoader from " + str + " is not allowed. Please make sure that the jar is listed in `PRODUCT_APEX_STANDALONE_SYSTEM_SERVER_JARS` in the Makefile and added as a `standalone_contents` of a `systemserverclasspath_fragment` in `Android.bp`.");
        }
        return createClassLoader(str, classLoader);
    }

    private static boolean allowClassLoaderCreation(String str, boolean z) {
        return !str.startsWith("/apex/") || z || ZygoteInit.shouldProfileSystemServer();
    }
}
