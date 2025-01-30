package com.android.p038wm.shell.dagger;

import android.os.Handler;
import android.os.Looper;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellConcurrencyModule_ProvideMainHandlerFactory implements Provider {
    public static Handler provideMainHandler$1() {
        return new Handler(Looper.getMainLooper());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideMainHandler$1();
    }
}
