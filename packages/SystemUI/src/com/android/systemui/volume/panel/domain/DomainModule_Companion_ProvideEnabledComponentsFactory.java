package com.android.systemui.volume.panel.domain;

import dagger.internal.Provider;
import java.util.Collection;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DomainModule_Companion_ProvideEnabledComponentsFactory implements Provider {
    public static Collection provideEnabledComponents() {
        DomainModule.Companion.getClass();
        Set set = ArraysKt___ArraysKt.toSet(new String[]{"anc", "spatial_audio", "captioning", "volume_sliders", "media_output", "bottom_bar"});
        set.getClass();
        return set;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideEnabledComponents();
    }
}
