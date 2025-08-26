package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.unfold.UnfoldBackgroundController;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideUnfoldBackgroundControllerFactory implements Provider {
    public final Provider contextProvider;

    public WMShellModule_ProvideUnfoldBackgroundControllerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static UnfoldBackgroundController provideUnfoldBackgroundController(Context context) {
        return new UnfoldBackgroundController(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new UnfoldBackgroundController((Context) this.contextProvider.get());
    }
}
