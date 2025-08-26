package com.samsung.systemui.splugins;

import com.samsung.systemui.splugins.annotations.ProvidesInterface;

@ProvidesInterface(version = 1)
/* loaded from: classes4.dex */
public class SPluginDependency {
    public static final int VERSION = 1;
    static DependencyProvider sProvider;

    abstract class DependencyProvider {
        public abstract <T> T get(SPlugin sPlugin, Class<T> cls);
    }

    public static <T> T get(SPlugin sPlugin, Class<T> cls) {
        return (T) sProvider.get(sPlugin, cls);
    }
}
