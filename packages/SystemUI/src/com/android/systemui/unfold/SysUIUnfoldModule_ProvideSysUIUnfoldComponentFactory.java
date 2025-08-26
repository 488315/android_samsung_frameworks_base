package com.android.systemui.unfold;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import dagger.internal.Provider;
import java.util.Optional;

/* loaded from: classes3.dex */
public final class SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory implements Provider {
    public final Provider bgProvider;
    public final Provider factoryProvider;
    public final SysUIUnfoldModule module;
    public final Provider providerProvider;
    public final Provider rotationProvider;
    public final Provider scopedProvider;

    public SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory(SysUIUnfoldModule sysUIUnfoldModule, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.module = sysUIUnfoldModule;
        this.providerProvider = provider;
        this.rotationProvider = provider2;
        this.scopedProvider = provider3;
        this.bgProvider = provider4;
        this.factoryProvider = provider5;
    }

    public static Optional provideSysUIUnfoldComponent(SysUIUnfoldModule sysUIUnfoldModule, Optional optional, Optional optional2, Optional optional3, Optional optional4, DaggerReferenceGlobalRootComponent.SysUIUnfoldComponentFactory sysUIUnfoldComponentFactory) {
        sysUIUnfoldModule.getClass();
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) optional.orElse(null);
        NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = (NaturalRotationUnfoldProgressProvider) optional2.orElse(null);
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = (ScopedUnfoldTransitionProgressProvider) optional3.orElse(null);
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider2 = (UnfoldTransitionProgressProvider) optional4.orElse(null);
        if (unfoldTransitionProgressProvider == null || naturalRotationUnfoldProgressProvider == null || scopedUnfoldTransitionProgressProvider == null || unfoldTransitionProgressProvider2 == null) {
            Optional optionalEmpty = Optional.empty();
            optionalEmpty.getClass();
            return optionalEmpty;
        }
        Optional optionalOf = Optional.of(sysUIUnfoldComponentFactory.create(unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider, unfoldTransitionProgressProvider2));
        optionalOf.getClass();
        return optionalOf;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSysUIUnfoldComponent(this.module, (Optional) this.providerProvider.get(), (Optional) this.rotationProvider.get(), (Optional) this.scopedProvider.get(), (Optional) this.bgProvider.get(), (DaggerReferenceGlobalRootComponent.SysUIUnfoldComponentFactory) this.factoryProvider.get());
    }
}
