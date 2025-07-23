package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.CoreStartable;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DeviceEntryFaceAuthInteractor extends CoreStartable {
    boolean canFaceAuthRun();

    Flow getAuthenticationStatus();

    StateFlow isAuthenticated();

    Flow isBypassEnabled();

    boolean isFaceAuthEnabledAndEnrolled();

    boolean isFaceAuthStrong();

    StateFlow isLockedOut();

    boolean isRunning();

    void onAccessibilityAction();

    void onDeviceLifted();

    void onNotificationPanelClicked();

    void onPrimaryBouncerUserInput();

    void onUdfpsSensorTouched();

    void onWalletLaunched();
}
