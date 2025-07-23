package com.android.systemui.plugins.keyguardstatusview;

import android.os.Bundle;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
