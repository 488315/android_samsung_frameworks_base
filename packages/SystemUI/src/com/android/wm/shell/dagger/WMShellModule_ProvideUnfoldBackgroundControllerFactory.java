package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.unfold.UnfoldBackgroundController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
