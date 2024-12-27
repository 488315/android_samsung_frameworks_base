package com.android.systemui.unfold;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import dagger.internal.Provider;
import java.util.Optional;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory implements Provider {
    public final javax.inject.Provider bgProvider;
    public final javax.inject.Provider factoryProvider;
    public final SysUIUnfoldModule module;
    public final javax.inject.Provider providerProvider;
    public final javax.inject.Provider rotationProvider;
    public final javax.inject.Provider scopedProvider;

    public SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory(SysUIUnfoldModule sysUIUnfoldModule, javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
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
            Optional empty = Optional.empty();
            Intrinsics.checkNotNull(empty);
            return empty;
        }
        Optional of = Optional.of(sysUIUnfoldComponentFactory.create(unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider, unfoldTransitionProgressProvider2));
        Intrinsics.checkNotNull(of);
        return of;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSysUIUnfoldComponent(this.module, (Optional) this.providerProvider.get(), (Optional) this.rotationProvider.get(), (Optional) this.scopedProvider.get(), (Optional) this.bgProvider.get(), (DaggerReferenceGlobalRootComponent.SysUIUnfoldComponentFactory) this.factoryProvider.get());
    }
}
