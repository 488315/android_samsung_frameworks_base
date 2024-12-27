package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class KeyguardMediaViewModel {
    public final ReadonlyStateFlow isMediaVisible;

    public KeyguardMediaViewModel(MediaCarouselInteractor mediaCarouselInteractor) {
        ReadonlyStateFlow readonlyStateFlow = mediaCarouselInteractor.hasActiveMediaOrRecommendation;
    }
}
