package com.android.systemui.plugins.keyguardstatusview;

import android.os.Bundle;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginLockStarStateCallback {
    @VersionCheck(version = 1017)
    default Bundle onUiInfoRequested() {
        return new Bundle();
    }

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION)
    default void onClockChanged(Bundle bundle) {
    }

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION)
    default void onFaceWidgetChanged(Bundle bundle) {
    }

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION)
    default void onLockStarEnabled(boolean z) {
    }

    @VersionCheck(version = 1017)
    default void onMusicChanged(Bundle bundle) {
    }

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION)
    default void onViewModeChanged(int i) {
    }
}
