package com.android.wm.shell.dagger;

import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideUnfoldTransitionHandlerFactory implements Provider {
    public final Provider handlerProvider;
    public final Provider progressProvider;

    public WMShellBaseModule_ProvideUnfoldTransitionHandlerFactory(Provider provider, Provider provider2) {
        this.progressProvider = provider;
        this.handlerProvider = provider2;
    }

    public static Optional provideUnfoldTransitionHandler(Lazy lazy, Optional optional) {
        Optional empty = (!optional.isPresent() || optional.get() == ShellUnfoldProgressProvider.NO_PROVIDER) ? Optional.empty() : (Optional) lazy.get();
        Preconditions.checkNotNullFromProvides(empty);
        return empty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideUnfoldTransitionHandler(DoubleCheck.lazy(this.handlerProvider), (Optional) this.progressProvider.get());
    }
}
