package com.android.systemui.settings.brightness.domain.interactor;

import com.android.systemui.settings.brightness.data.repository.BrightnessMirrorShowingRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class BrightnessMirrorShowingInteractor {
    public final BrightnessMirrorShowingRepository brightnessMirrorShowingRepository;
    public final ReadonlyStateFlow isShowing;

    public BrightnessMirrorShowingInteractor(BrightnessMirrorShowingRepository brightnessMirrorShowingRepository) {
        this.brightnessMirrorShowingRepository = brightnessMirrorShowingRepository;
        this.isShowing = brightnessMirrorShowingRepository.isShowing;
    }
}
