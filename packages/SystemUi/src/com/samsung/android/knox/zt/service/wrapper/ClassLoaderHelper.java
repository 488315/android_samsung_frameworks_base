package com.samsung.android.knox.zt.service.wrapper;

import dalvik.system.PathClassLoader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ClassLoaderHelper {
    public static volatile ClassLoaderHelper sInstance;
    public final ClassLoader mSakClassLoader = new PathClassLoader("/system/framework/samsungkeystoreutils.jar", ClassLoader.getSystemClassLoader());

    private ClassLoaderHelper() {
    }

    public static ClassLoaderHelper getInstance() {
        if (sInstance == null) {
            synchronized (ClassLoaderHelper.class) {
                if (sInstance == null) {
                    sInstance = new ClassLoaderHelper();
                }
            }
        }
        return sInstance;
    }

    public final ClassLoader getSakClassLoader() {
        return this.mSakClassLoader;
    }
}
