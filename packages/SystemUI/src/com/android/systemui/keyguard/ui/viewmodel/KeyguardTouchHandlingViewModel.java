package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTouchHandlingInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardTouchHandlingViewModel {
    public final KeyguardTouchHandlingInteractor interactor;
    public final ReadonlyStateFlow isLongPressHandlingEnabled;

    public KeyguardTouchHandlingViewModel(KeyguardTouchHandlingInteractor keyguardTouchHandlingInteractor) {
        this.interactor = keyguardTouchHandlingInteractor;
        this.isLongPressHandlingEnabled = keyguardTouchHandlingInteractor.isLongPressHandlingEnabled;
    }
}
