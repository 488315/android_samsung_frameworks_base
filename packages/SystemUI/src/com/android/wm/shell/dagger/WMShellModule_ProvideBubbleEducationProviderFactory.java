package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.bubbles.BubbleEducationController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
