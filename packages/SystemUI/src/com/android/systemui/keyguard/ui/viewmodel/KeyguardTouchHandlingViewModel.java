package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTouchHandlingInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class KeyguardTouchHandlingViewModel {
    public final KeyguardTouchHandlingInteractor interactor;
    public final ReadonlyStateFlow isLongPressHandlingEnabled;

    public KeyguardTouchHandlingViewModel(KeyguardTouchHandlingInteractor keyguardTouchHandlingInteractor) {
        this.interactor = keyguardTouchHandlingInteractor;
        this.isLongPressHandlingEnabled = keyguardTouchHandlingInteractor.isLongPressHandlingEnabled;
    }
}
