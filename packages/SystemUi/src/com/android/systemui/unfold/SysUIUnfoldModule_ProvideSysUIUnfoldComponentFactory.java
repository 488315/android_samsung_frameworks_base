package com.android.systemui.unfold;

import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory implements Provider {
    public final Provider factoryProvider;
    public final SysUIUnfoldModule module;
    public final Provider providerProvider;
    public final Provider rotationProvider;
    public final Provider scopedProvider;

    public SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory(SysUIUnfoldModule sysUIUnfoldModule, Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.module = sysUIUnfoldModule;
        this.providerProvider = provider;
        this.rotationProvider = provider2;
        this.scopedProvider = provider3;
        this.factoryProvider = provider4;
    }

    public static Optional provideSysUIUnfoldComponent(SysUIUnfoldModule sysUIUnfoldModule, Optional optional, Optional optional2, Optional optional3, SysUIUnfoldComponent.Factory factory) {
        sysUIUnfoldModule.getClass();
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) optional.orElse(null);
        NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = (NaturalRotationUnfoldProgressProvider) optional2.orElse(null);
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = (ScopedUnfoldTransitionProgressProvider) optional3.orElse(null);
        return (unfoldTransitionProgressProvider == null || naturalRotationUnfoldProgressProvider == null || scopedUnfoldTransitionProgressProvider == null) ? Optional.empty() : Optional.of(factory.create(unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSysUIUnfoldComponent(this.module, (Optional) this.providerProvider.get(), (Optional) this.rotationProvider.get(), (Optional) this.scopedProvider.get(), (SysUIUnfoldComponent.Factory) this.factoryProvider.get());
    }
}
