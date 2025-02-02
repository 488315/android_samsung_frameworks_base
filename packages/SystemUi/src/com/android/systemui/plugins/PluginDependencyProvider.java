package com.android.systemui.plugins;

import android.util.ArrayMap;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.PluginDependency;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PluginDependencyProvider extends PluginDependency.DependencyProvider {
    private final ArrayMap<Class<?>, Object> mDependencies = new ArrayMap<>();
    private final Lazy mManagerLazy;

    public PluginDependencyProvider(Lazy lazy) {
        this.mManagerLazy = lazy;
        PluginDependency.sProvider = this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void allowPluginDependency(Class<T> cls) {
        allowPluginDependency(cls, Dependency.get(cls));
    }

    @Override // com.android.systemui.plugins.PluginDependency.DependencyProvider
    public <T> T get(Plugin plugin, Class<T> cls) {
        T t;
        if (!((PluginManager) this.mManagerLazy.get()).dependsOn(plugin, cls)) {
            throw new IllegalArgumentException(plugin.getClass() + " does not depend on " + cls);
        }
        synchronized (this.mDependencies) {
            if (!this.mDependencies.containsKey(cls)) {
                throw new IllegalArgumentException("Unknown dependency " + cls);
            }
            t = (T) this.mDependencies.get(cls);
        }
        return t;
    }

    public <T> void allowPluginDependency(Class<T> cls, T t) {
        synchronized (this.mDependencies) {
            this.mDependencies.put(cls, t);
        }
    }
}
