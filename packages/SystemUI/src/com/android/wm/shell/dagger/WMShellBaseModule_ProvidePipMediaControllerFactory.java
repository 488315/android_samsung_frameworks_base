package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.wm.shell.common.pip.PipMediaController;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvidePipMediaControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider mainHandlerProvider;

    public WMShellBaseModule_ProvidePipMediaControllerFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
    }

    public static PipMediaController providePipMediaController(Context context, Handler handler) {
        return new PipMediaController(context, handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipMediaController((Context) this.contextProvider.get(), (Handler) this.mainHandlerProvider.get());
    }
}
