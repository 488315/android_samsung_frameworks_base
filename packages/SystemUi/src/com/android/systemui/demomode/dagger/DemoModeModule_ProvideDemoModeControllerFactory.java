package com.android.systemui.demomode.dagger;

import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.GlobalSettings;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DemoModeModule_ProvideDemoModeControllerFactory implements Provider {
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider globalSettingsProvider;

    public DemoModeModule_ProvideDemoModeControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
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
