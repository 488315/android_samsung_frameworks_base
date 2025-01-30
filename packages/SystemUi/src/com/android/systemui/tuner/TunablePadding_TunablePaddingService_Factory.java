package com.android.systemui.tuner;

import com.android.systemui.tuner.TunablePadding;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TunablePadding_TunablePaddingService_Factory implements Provider {
    public final Provider tunerServiceProvider;

    public TunablePadding_TunablePaddingService_Factory(Provider provider) {
        this.tunerServiceProvider = provider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TunablePadding.TunablePaddingService((TunerService) this.tunerServiceProvider.get());
    }
}
