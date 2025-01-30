package com.android.p038wm.shell.dagger;

import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideSplitScreenFactory implements Provider {
    public final Provider splitScreenControllerProvider;

    public WMShellBaseModule_ProvideSplitScreenFactory(Provider provider) {
        this.splitScreenControllerProvider = provider;
    }

    public static Optional provideSplitScreen(Optional optional) {
        Optional map = optional.map(new WMShellBaseModule$$ExternalSyntheticLambda0(3));
        Preconditions.checkNotNullFromProvides(map);
        return map;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSplitScreen((Optional) this.splitScreenControllerProvider.get());
    }
}
