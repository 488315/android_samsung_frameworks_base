package com.samsung.systemui.splugins;

import android.util.ArrayMap;
import com.android.systemui.Dependency;
import com.samsung.systemui.splugins.SPluginDependency;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SPluginDependencyProvider extends SPluginDependency.DependencyProvider {
    private final ArrayMap<Class<?>, Object> mDependencies = new ArrayMap<>();
    private final SPluginManager mManager;

    public SPluginDependencyProvider(SPluginManager sPluginManager) {
        this.mManager = sPluginManager;
        SPluginDependency.sProvider = this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void allowPluginDependency(Class<T> cls) {
        allowPluginDependency(cls, Dependency.get(cls));
    }

    @Override // com.samsung.systemui.splugins.SPluginDependency.DependencyProvider
    public <T> T get(SPlugin sPlugin, Class<T> cls) {
        T t;
        if (!this.mManager.dependsOn(sPlugin, cls)) {
            throw new IllegalArgumentException(sPlugin.getClass() + " does not depend on " + cls);
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
