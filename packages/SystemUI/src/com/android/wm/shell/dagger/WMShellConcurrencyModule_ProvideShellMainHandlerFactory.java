package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import com.android.systemui.R;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellConcurrencyModule_ProvideShellMainHandlerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainThreadProvider;
    public final Provider sysuiMainHandlerProvider;

    public WMShellConcurrencyModule_ProvideShellMainHandlerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.mainThreadProvider = provider2;
        this.sysuiMainHandlerProvider = provider3;
    }

    public static Handler provideShellMainHandler(Context context, HandlerThread handlerThread, Handler handler) {
        if (context.getResources().getBoolean(R.bool.config_enableShellMainThread)) {
            if (handlerThread == null) {
                handlerThread = WMShellConcurrencyModule.createShellMainThread();
                handlerThread.start();
            }
            if (Build.IS_DEBUGGABLE) {
                handlerThread.getLooper().setTraceTag(32L);
                handlerThread.getLooper().setSlowLogThresholdMs(30L, 30L);
            }
            handler = Handler.createAsync(handlerThread.getLooper());
        }
        handler.getClass();
        return handler;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellMainHandler((Context) this.contextProvider.get(), (HandlerThread) this.mainThreadProvider.get(), (Handler) this.sysuiMainHandlerProvider.get());
    }
}
