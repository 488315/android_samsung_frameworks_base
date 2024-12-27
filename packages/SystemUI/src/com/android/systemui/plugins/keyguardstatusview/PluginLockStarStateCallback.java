package com.android.systemui.plugins.keyguardstatusview;

import android.os.Bundle;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.samsung.android.knox.EnterpriseContainerCallback;

@SupportVersionChecker
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
