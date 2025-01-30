package com.android.systemui.globalactions.presentation.viewmodel;

import android.content.SharedPreferences;
import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.systemui.globalactions.util.ScreenCapturePopupController;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        SharedPreferences sharedPreferences = screenCapturePopupController.mPrefrerences;
        int i = sharedPreferences.getInt("count", 0);
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("current count : ", i, ", diff : ");
        long currentTimeMillis = System.currentTimeMillis();
        SharedPreferences sharedPreferences2 = screenCapturePopupController.mPrefrerences;
        m1m.append(Long.valueOf(currentTimeMillis - sharedPreferences2.getLong("dismissTime", 0L)));
        screenCapturePopupController.mLogWrapper.logDebug("ScreenCapturePopupController", m1m.toString());
        if (i >= 1 || Long.valueOf(System.currentTimeMillis() - sharedPreferences2.getLong("dismissTime", 0L)).longValue() > 10000) {
            return false;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("count", sharedPreferences.getInt("count", 0) + 1);
        edit.apply();
        return true;
    }

    public final void setActionInfo(ActionInfo actionInfo) {
        this.mInfo = actionInfo;
    }

    public final void onPress() {
    }
}
