package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory implements Provider {
    public final Provider contextProvider;

    public WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PipSurfaceTransactionHelper providePipSurfaceTransactionHelper(Context context) {
        return new PipSurfaceTransactionHelper(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipSurfaceTransactionHelper((Context) this.contextProvider.get());
    }
}
