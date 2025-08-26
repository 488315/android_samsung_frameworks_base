package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.pip.PhoneSizeSpecSource;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideSizeSpecSourceFactory implements Provider {
    public final Provider contextProvider;
    public final Provider pipDisplayLayoutStateProvider;

    public WMShellBaseModule_ProvideSizeSpecSourceFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.pipDisplayLayoutStateProvider = provider2;
    }

    public static PhoneSizeSpecSource provideSizeSpecSource(Context context, PipDisplayLayoutState pipDisplayLayoutState) {
        return new PhoneSizeSpecSource(context, pipDisplayLayoutState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PhoneSizeSpecSource((Context) this.contextProvider.get(), (PipDisplayLayoutState) this.pipDisplayLayoutStateProvider.get());
    }
}
