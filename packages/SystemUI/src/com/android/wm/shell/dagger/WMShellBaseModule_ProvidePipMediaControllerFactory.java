package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.wm.shell.common.pip.PipMediaController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
