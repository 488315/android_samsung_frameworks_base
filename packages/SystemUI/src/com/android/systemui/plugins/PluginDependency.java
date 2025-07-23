package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(version = 1)
/* loaded from: classes2.dex */
public class PluginDependency {
    public static final int VERSION = 1;
    static DependencyProvider sProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    abstract class DependencyProvider {
        public abstract <T> T get(Plugin plugin, Class<T> cls);
    }

    public static <T> T get(Plugin plugin, Class<T> cls) {
        return (T) sProvider.get(plugin, cls);
    }
}
