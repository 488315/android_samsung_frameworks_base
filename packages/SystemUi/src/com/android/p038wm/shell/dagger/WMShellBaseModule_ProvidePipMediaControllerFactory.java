package com.android.p038wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.p038wm.shell.pip.PipMediaController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
