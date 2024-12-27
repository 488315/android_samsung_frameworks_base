package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.CoreStartable;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
