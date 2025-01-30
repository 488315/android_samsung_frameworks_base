package com.android.p038wm.shell.dagger;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                handlerThread = new HandlerThread("wmshell.main", -4);
                if (CoreRune.SYSPERF_VI_BOOST) {
                    new Handler().postDelayed(new WMShellConcurrencyModule$1(handlerThread), 10000L);
                }
                handlerThread.start();
            }
            if (Build.IS_DEBUGGABLE) {
                handlerThread.getLooper().setTraceTag(32L);
                handlerThread.getLooper().setSlowLogThresholdMs(30L, 30L);
            }
            handler = Handler.createAsync(handlerThread.getLooper());
        }
        Preconditions.checkNotNullFromProvides(handler);
        return handler;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellMainHandler((Context) this.contextProvider.get(), (HandlerThread) this.mainThreadProvider.get(), (Handler) this.sysuiMainHandlerProvider.get());
    }
}
