package com.android.systemui.settings.brightness.domain.interactor;

import com.android.systemui.settings.brightness.data.repository.BrightnessMirrorShowingRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
