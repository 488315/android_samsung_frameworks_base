package com.android.systemui.classifier;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class FalsingManagerProxy_Factory implements Provider {
    public final Provider brightLineFalsingManagerProvider;
    public final Provider deviceConfigProvider;
    public final Provider dumpManagerProvider;
    public final Provider executorProvider;
    public final Provider pluginManagerProvider;

    public FalsingManagerProxy_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.pluginManagerProvider = provider;
        this.executorProvider = provider2;
        this.deviceConfigProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.brightLineFalsingManagerProvider = provider5;
    }

    public static FalsingManagerProxy newInstance(PluginManager pluginManager, Executor executor, DeviceConfigProxy deviceConfigProxy, DumpManager dumpManager, Provider provider) {
        return new FalsingManagerProxy(pluginManager, executor, deviceConfigProxy, dumpManager, provider);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FalsingManagerProxy((PluginManager) this.pluginManagerProvider.get(), (Executor) this.executorProvider.get(), (DeviceConfigProxy) this.deviceConfigProvider.get(), (DumpManager) this.dumpManagerProvider.get(), this.brightLineFalsingManagerProvider);
    }
}
