package com.samsung.systemui.splugins;

import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(version = 1)
/* loaded from: classes3.dex */
public class SPluginDependency {
    public static final int VERSION = 1;
    static DependencyProvider sProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class DependencyProvider {
        public abstract <T> T get(SPlugin sPlugin, Class<T> cls);
    }

    public static <T> T get(SPlugin sPlugin, Class<T> cls) {
        return (T) sProvider.get(sPlugin, cls);
    }
}
