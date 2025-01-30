package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.PipDisplayLayoutState;
import com.android.p038wm.shell.pip.phone.PipSizeSpecHandler;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePipBoundsStateFactory implements Provider {
    public final Provider contextProvider;
    public final Provider pipDisplayLayoutStateProvider;
    public final Provider pipSizeSpecHandlerProvider;

    public WMShellModule_ProvidePipBoundsStateFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.pipSizeSpecHandlerProvider = provider2;
        this.pipDisplayLayoutStateProvider = provider3;
    }

    public static PipBoundsState providePipBoundsState(Context context, PipSizeSpecHandler pipSizeSpecHandler, PipDisplayLayoutState pipDisplayLayoutState) {
        return new PipBoundsState(context, pipSizeSpecHandler, pipDisplayLayoutState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipBoundsState((Context) this.contextProvider.get(), (PipSizeSpecHandler) this.pipSizeSpecHandlerProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get());
    }
}
