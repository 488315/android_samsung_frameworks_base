package com.android.wm.shell.dagger;

import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideUnfoldControllerFactory implements Provider {
    public final Provider fullscreenUnfoldControllerProvider;
    public final Provider progressProvider;

    public WMShellBaseModule_ProvideUnfoldControllerFactory(Provider provider, Provider provider2) {
        this.fullscreenUnfoldControllerProvider = provider;
        this.progressProvider = provider2;
    }

    public static Optional provideUnfoldController(Lazy lazy, Optional optional) {
        Optional empty = (!optional.isPresent() || optional.get() == ShellUnfoldProgressProvider.NO_PROVIDER) ? Optional.empty() : (Optional) lazy.get();
        Preconditions.checkNotNullFromProvides(empty);
        return empty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideUnfoldController(DoubleCheck.lazy(this.fullscreenUnfoldControllerProvider), (Optional) this.progressProvider.get());
    }
}
