package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.CoreStartable;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

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
