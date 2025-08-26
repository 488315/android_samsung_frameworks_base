package com.android.systemui.shade;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareModule_ProvideMutableShadePositionRepositoryFactory implements Provider {
    public final Provider implProvider;

    public ShadeDisplayAwareModule_ProvideMutableShadePositionRepositoryFactory(Provider provider) {
        this.implProvider = provider;
    }

    public static void provideMutableShadePositionRepository(ShadeDisplaysRepositoryImpl shadeDisplaysRepositoryImpl) {
        ShadeDisplayAwareModule.INSTANCE.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        shadeDisplaysRepositoryImpl.getClass();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        ShadeDisplaysRepositoryImpl shadeDisplaysRepositoryImpl = (ShadeDisplaysRepositoryImpl) this.implProvider.get();
        provideMutableShadePositionRepository(shadeDisplaysRepositoryImpl);
        return shadeDisplaysRepositoryImpl;
    }
}
