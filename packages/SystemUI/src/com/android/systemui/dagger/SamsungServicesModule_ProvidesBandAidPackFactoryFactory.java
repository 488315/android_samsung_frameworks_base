package com.android.systemui.dagger;

import com.android.systemui.navigationbar.bandaid.BandAidPackFactory;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvidesBandAidPackFactoryFactory implements Provider {
    public static BandAidPackFactory providesBandAidPackFactory() {
        return new BandAidPackFactory();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BandAidPackFactory();
    }
}
