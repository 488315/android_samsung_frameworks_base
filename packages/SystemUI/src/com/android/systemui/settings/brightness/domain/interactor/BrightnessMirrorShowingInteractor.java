package com.android.systemui.settings.brightness.domain.interactor;

import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface BrightnessMirrorShowingInteractor {
    StateFlow isShowing();

    void setMirrorShowing(boolean z);
}
