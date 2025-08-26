package com.android.systemui.shade;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.data.repository.MutableShadeDisplaysRepository;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareModule_ProvideShadePositionRepositoryFactory implements Provider {
    public final Provider implProvider;

    public ShadeDisplayAwareModule_ProvideShadePositionRepositoryFactory(Provider provider) {
        this.implProvider = provider;
    }

    public static void provideShadePositionRepository(MutableShadeDisplaysRepository mutableShadeDisplaysRepository) {
        ShadeDisplayAwareModule.INSTANCE.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        mutableShadeDisplaysRepository.getClass();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        MutableShadeDisplaysRepository mutableShadeDisplaysRepository = (MutableShadeDisplaysRepository) this.implProvider.get();
        provideShadePositionRepository(mutableShadeDisplaysRepository);
        return mutableShadeDisplaysRepository;
    }
}
