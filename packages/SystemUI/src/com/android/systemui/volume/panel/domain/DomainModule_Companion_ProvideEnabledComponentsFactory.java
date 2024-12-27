package com.android.systemui.volume.panel.domain;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collection;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;

public final class DomainModule_Companion_ProvideEnabledComponentsFactory implements Provider {
    public static Collection provideEnabledComponents() {
        DomainModule.Companion.getClass();
        Set of = SetsKt__SetsKt.setOf("anc", "spatial_audio", "captioning", "volume_sliders", "media_output", "bottom_bar");
        Preconditions.checkNotNullFromProvides(of);
        return of;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideEnabledComponents();
    }
}
