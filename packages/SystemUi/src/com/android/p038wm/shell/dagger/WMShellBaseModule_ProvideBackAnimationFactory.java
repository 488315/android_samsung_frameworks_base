package com.android.p038wm.shell.dagger;

import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideBackAnimationFactory implements Provider {
    public final Provider backAnimationControllerProvider;

    public WMShellBaseModule_ProvideBackAnimationFactory(Provider provider) {
        this.backAnimationControllerProvider = provider;
    }

    public static Optional provideBackAnimation(Optional optional) {
        Optional map = optional.map(new WMShellBaseModule$$ExternalSyntheticLambda0(8));
        Preconditions.checkNotNullFromProvides(map);
        return map;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideBackAnimation((Optional) this.backAnimationControllerProvider.get());
    }
}
