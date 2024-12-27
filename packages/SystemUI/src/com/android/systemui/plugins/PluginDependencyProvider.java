package com.android.systemui.plugins;

import android.util.ArrayMap;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.PluginDependency;
import dagger.Lazy;

public class PluginDependencyProvider extends PluginDependency.DependencyProvider {
    private final ArrayMap<Class<?>, Object> mDependencies = new ArrayMap<>();
    private final Lazy mManagerLazy;

    public PluginDependencyProvider(Lazy lazy) {
        this.mManagerLazy = lazy;
        PluginDependency.sProvider = this;
    }

    public <T> void allowPluginDependency(Class<T> cls, T t) {
        synchronized (this.mDependencies) {
            this.mDependencies.put(cls, t);
        }
    }

    @Override // com.android.systemui.plugins.PluginDependency.DependencyProvider
    public <T> T get(Plugin plugin, Class<T> cls) {
        T t;
        if (!((PluginManager) this.mManagerLazy.get()).dependsOn(plugin, cls)) {
            throw new IllegalArgumentException(plugin.getClass() + " does not depend on " + cls);
        }
        synchronized (this.mDependencies) {
            try {
                if (!this.mDependencies.containsKey(cls)) {
                    throw new IllegalArgumentException("Unknown dependency " + cls);
                }
                t = (T) this.mDependencies.get(cls);
            } catch (Throwable th) {
                throw th;
            }
        }
        return t;
    }

    public <T> void allowPluginDependency(Class<T> cls) {
        allowPluginDependency(cls, Dependency.sDependency.getDependencyInner(cls));
    }
}
