package com.android.systemui.settings.brightness.domain.interactor;

import com.android.systemui.settings.brightness.data.repository.BrightnessMirrorShowingRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class BrightnessMirrorShowingInteractorPassThrough implements BrightnessMirrorShowingInteractor {
    public final BrightnessMirrorShowingRepository brightnessMirrorShowingRepository;
    public final ReadonlyStateFlow isShowing;

    public BrightnessMirrorShowingInteractorPassThrough(BrightnessMirrorShowingRepository brightnessMirrorShowingRepository) {
        this.brightnessMirrorShowingRepository = brightnessMirrorShowingRepository;
        this.isShowing = brightnessMirrorShowingRepository.isShowing;
    }

    @Override // com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor
    public final StateFlow isShowing() {
        return this.isShowing;
    }

    @Override // com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor
    public final void setMirrorShowing(boolean z) {
        this.brightnessMirrorShowingRepository._isShowing.updateState(null, Boolean.valueOf(z));
    }
}
