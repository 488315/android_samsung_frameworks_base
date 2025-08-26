package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.bubbles.BubbleEducationController;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideBubbleEducationProviderFactory implements Provider {
    public final Provider contextProvider;

    public WMShellModule_ProvideBubbleEducationProviderFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static BubbleEducationController provideBubbleEducationProvider(Context context) {
        return new BubbleEducationController(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BubbleEducationController((Context) this.contextProvider.get());
    }
}
