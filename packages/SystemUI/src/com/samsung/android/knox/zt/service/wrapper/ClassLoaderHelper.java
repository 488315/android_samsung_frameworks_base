package com.samsung.android.knox.zt.service.wrapper;

import dalvik.system.PathClassLoader;

/* loaded from: classes4.dex */
public class ClassLoaderHelper {
    public static volatile ClassLoaderHelper sInstance;
    public final ClassLoader mSakClassLoader = new PathClassLoader("/system/framework/samsungkeystoreutils.jar", ClassLoader.getSystemClassLoader());

    private ClassLoaderHelper() {
    }

    public static ClassLoaderHelper getInstance() {
        if (sInstance == null) {
            synchronized (ClassLoaderHelper.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new ClassLoaderHelper();
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public ClassLoader getSakClassLoader() {
        return this.mSakClassLoader;
    }
}
