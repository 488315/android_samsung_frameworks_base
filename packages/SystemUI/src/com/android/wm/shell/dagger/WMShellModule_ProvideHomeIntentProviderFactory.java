package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.HomeIntentProvider;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideHomeIntentProviderFactory implements Provider {
    public final Provider contextProvider;

    public WMShellModule_ProvideHomeIntentProviderFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static HomeIntentProvider provideHomeIntentProvider(Context context) {
        return new HomeIntentProvider(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HomeIntentProvider((Context) this.contextProvider.get());
    }
}
