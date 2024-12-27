package com.android.systemui.globalactions.presentation.viewmodel;

import android.content.SharedPreferences;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.util.ScreenCapturePopupController;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.LogWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenCapturePopupActionViewModel implements ActionViewModel {
    public ActionInfo mInfo;
    public final ScreenCapturePopupController mPopupController;

    public ScreenCapturePopupActionViewModel(ScreenCapturePopupController screenCapturePopupController) {
        this.mPopupController = screenCapturePopupController;
    }

    public final ActionInfo getActionInfo() {
        return this.mInfo;
    }

    public final boolean isAvailableShow() {
        ScreenCapturePopupController screenCapturePopupController = this.mPopupController;
        int i = screenCapturePopupController.mPrefrerences.getInt(SystemUIAnalytics.QPNE_KEY_COUNT, 0);
        LogWrapper logWrapper = screenCapturePopupController.mLogWrapper;
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "current count : ", ", diff : ");
        m.append(Long.valueOf(System.currentTimeMillis() - screenCapturePopupController.mPrefrerences.getLong("dismissTime", 0L)));
        logWrapper.logDebug("ScreenCapturePopupController", m.toString());
        if (i >= 1 || System.currentTimeMillis() - screenCapturePopupController.mPrefrerences.getLong("dismissTime", 0L) > 10000) {
            return false;
        }
        SharedPreferences.Editor edit = screenCapturePopupController.mPrefrerences.edit();
        edit.putInt(SystemUIAnalytics.QPNE_KEY_COUNT, screenCapturePopupController.mPrefrerences.getInt(SystemUIAnalytics.QPNE_KEY_COUNT, 0) + 1);
        edit.apply();
        return true;
    }

    public final void setActionInfo(ActionInfo actionInfo) {
        this.mInfo = actionInfo;
    }

    public final void onPress() {
    }
}
