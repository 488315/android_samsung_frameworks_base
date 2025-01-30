package com.android.systemui.unfold;

import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.hinge.EmptyHingeAngleProvider;
import com.android.systemui.unfold.updates.hinge.HingeAngleProvider;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldSharedInternalModule_HingeAngleProviderFactory implements Provider {
    public final Provider configProvider;
    public final Provider hingeAngleSensorProvider;
    public final UnfoldSharedInternalModule module;

    public UnfoldSharedInternalModule_HingeAngleProviderFactory(UnfoldSharedInternalModule unfoldSharedInternalModule, Provider provider, Provider provider2) {
        this.module = unfoldSharedInternalModule;
        this.configProvider = provider;
        this.hingeAngleSensorProvider = provider2;
    }

    public static HingeAngleProvider hingeAngleProvider(UnfoldSharedInternalModule unfoldSharedInternalModule, UnfoldTransitionConfig unfoldTransitionConfig, Provider provider) {
        unfoldSharedInternalModule.getClass();
        HingeAngleProvider hingeAngleProvider = ((Boolean) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).isHingeAngleEnabled$delegate.getValue()).booleanValue() ? (HingeAngleProvider) provider.get() : EmptyHingeAngleProvider.INSTANCE;
        Preconditions.checkNotNullFromProvides(hingeAngleProvider);
        return hingeAngleProvider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return hingeAngleProvider(this.module, (UnfoldTransitionConfig) this.configProvider.get(), this.hingeAngleSensorProvider);
    }
}
