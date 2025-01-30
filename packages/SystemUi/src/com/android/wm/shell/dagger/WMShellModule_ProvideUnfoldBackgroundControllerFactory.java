package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.unfold.UnfoldBackgroundController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
