package com.android.systemui.dagger;

import com.android.systemui.navigationbar.bandaid.BandAidPackFactory;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvidesBandAidPackFactoryFactory implements Provider {
    public static BandAidPackFactory providesBandAidPackFactory() {
        return new BandAidPackFactory();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BandAidPackFactory();
    }
}
