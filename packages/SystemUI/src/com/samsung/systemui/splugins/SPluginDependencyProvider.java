package com.samsung.systemui.splugins;

import android.util.ArrayMap;
import com.android.systemui.Dependency;
import com.samsung.systemui.splugins.SPluginDependency;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SPluginDependencyProvider extends SPluginDependency.DependencyProvider {
    private final ArrayMap<Class<?>, Object> mDependencies = new ArrayMap<>();
    private final SPluginManager mManager;

    public SPluginDependencyProvider(SPluginManager sPluginManager) {
        this.mManager = sPluginManager;
        SPluginDependency.sProvider = this;
    }

    public <T> void allowPluginDependency(Class<T> cls, T t) {
        synchronized (this.mDependencies) {
            this.mDependencies.put(cls, t);
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginDependency.DependencyProvider
    public <T> T get(SPlugin sPlugin, Class<T> cls) {
        T t;
        if (!this.mManager.dependsOn(sPlugin, cls)) {
            throw new IllegalArgumentException(sPlugin.getClass() + " does not depend on " + cls);
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

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void allowPluginDependency(Class<T> cls) {
        allowPluginDependency(cls, Dependency.sDependency.getDependencyInner(cls));
    }
}
