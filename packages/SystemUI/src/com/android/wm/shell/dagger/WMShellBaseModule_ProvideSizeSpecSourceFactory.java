package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.pip.PhoneSizeSpecSource;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
