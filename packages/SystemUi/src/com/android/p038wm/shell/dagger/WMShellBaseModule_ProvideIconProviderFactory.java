package com.android.p038wm.shell.dagger;

import android.content.Context;
import com.android.launcher3.icons.IconProvider;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
