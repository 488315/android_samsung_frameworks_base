package com.android.systemui.unfold;

import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldSharedInternalModule_ProvideProgressForwarderFactory implements Provider {
    public final Provider configProvider;
    public final UnfoldSharedInternalModule module;
    public final Provider progressForwarderProvider;

    public UnfoldSharedInternalModule_ProvideProgressForwarderFactory(UnfoldSharedInternalModule unfoldSharedInternalModule, Provider provider, Provider provider2) {
        this.module = unfoldSharedInternalModule;
        this.configProvider = provider;
        this.progressForwarderProvider = provider2;
    }

    public static Optional provideProgressForwarder(UnfoldSharedInternalModule unfoldSharedInternalModule, UnfoldTransitionConfig unfoldTransitionConfig, Provider provider) {
        unfoldSharedInternalModule.getClass();
        return !((Boolean) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).isEnabled$delegate.getValue()).booleanValue() ? Optional.empty() : Optional.of(provider.get());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideProgressForwarder(this.module, (UnfoldTransitionConfig) this.configProvider.get(), this.progressForwarderProvider);
    }
}
