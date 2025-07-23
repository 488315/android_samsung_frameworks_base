package com.android.systemui.dagger;

import com.android.systemui.navigationbar.bandaid.BandAidPackFactory;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
