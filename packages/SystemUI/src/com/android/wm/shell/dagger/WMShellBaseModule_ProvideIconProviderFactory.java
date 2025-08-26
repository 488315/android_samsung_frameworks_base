package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.launcher3.icons.IconProvider;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideIconProviderFactory implements Provider {
    public final Provider contextProvider;

    public WMShellBaseModule_ProvideIconProviderFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static IconProvider provideIconProvider(Context context) {
        return new IconProvider(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new IconProvider((Context) this.contextProvider.get());
    }
}
