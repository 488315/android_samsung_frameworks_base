package com.android.systemui.demomode.dagger;

import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DemoModeModule_ProvideDemoModeControllerFactory implements Provider {
    public final javax.inject.Provider broadcastDispatcherProvider;
    public final javax.inject.Provider contextProvider;
    public final javax.inject.Provider dumpManagerProvider;
    public final javax.inject.Provider globalSettingsProvider;

    public DemoModeModule_ProvideDemoModeControllerFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
        this.globalSettingsProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
    }

    public static DemoModeController provideDemoModeController(Context context, DumpManager dumpManager, GlobalSettings globalSettings, BroadcastDispatcher broadcastDispatcher) {
        DemoModeController demoModeController = new DemoModeController(context, dumpManager, globalSettings, broadcastDispatcher);
        if (demoModeController.initialized) {
            throw new IllegalStateException("Already initialized");
        }
        demoModeController.initialized = true;
        demoModeController.dumpManager.registerNormalDumpable("DemoModeController", demoModeController);
        demoModeController.tracker.startTracking();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.demo");
        BroadcastDispatcher.registerReceiver$default(demoModeController.broadcastDispatcher, demoModeController.broadcastReceiver, intentFilter, null, UserHandle.ALL, 0, "android.permission.DUMP", 20);
        return demoModeController;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDemoModeController((Context) this.contextProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
