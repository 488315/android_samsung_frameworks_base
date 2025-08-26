package com.android.systemui.settings.brightness.domain.interactor;

import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public interface BrightnessMirrorShowingInteractor {
    StateFlow isShowing();

    void setMirrorShowing(boolean z);
}
