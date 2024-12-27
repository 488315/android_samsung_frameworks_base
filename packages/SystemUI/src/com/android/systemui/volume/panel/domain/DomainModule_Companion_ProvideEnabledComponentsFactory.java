package com.android.systemui.volume.panel.domain;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collection;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
