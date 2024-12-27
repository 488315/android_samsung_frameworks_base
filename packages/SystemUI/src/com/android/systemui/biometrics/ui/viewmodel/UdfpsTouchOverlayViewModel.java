package com.android.systemui.biometrics.ui.viewmodel;

import kotlinx.coroutines.flow.Flow;

public interface UdfpsTouchOverlayViewModel {
    Flow getShouldHandleTouches();
}
