package com.android.systemui.blur.ui.viewmodel;

import com.android.systemui.blur.domain.interactor.SecPanelBackgroundCommonInteractor;
import com.android.systemui.blur.domain.interactor.SecPanelBackgroundDisplayInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecPanelBackgroundViewModel {
    public final ReadonlyStateFlow maxAlpha;
    public final StateFlow shouldShow;
    public final SharedFlowImpl updateBackgroundColor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        SecPanelBackgroundViewModel create();
    }

    public SecPanelBackgroundViewModel(SecPanelBackgroundDisplayInteractor secPanelBackgroundDisplayInteractor, SecPanelBackgroundCommonInteractor secPanelBackgroundCommonInteractor) {
        this.maxAlpha = secPanelBackgroundCommonInteractor.maxAlpha;
        this.shouldShow = secPanelBackgroundDisplayInteractor.getShouldShow();
        this.updateBackgroundColor = secPanelBackgroundCommonInteractor.updateBackgroundColor;
    }
}
