package com.android.server.wm;

import android.provider.Settings;
import android.util.Slog;

import com.samsung.android.multiwindow.SmartPopupViewUtil;
import com.samsung.android.rune.CoreRune;

import java.util.List;

public final class SmartPopupViewServiceBinder extends FreeformContainerServiceBinder {
    public final boolean isSmartPopupViewOn() {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        try {
            int currentUserId = activityTaskManagerService.mAmInternal.getCurrentUserId();
            boolean z =
                    Settings.Secure.getIntForUser(
                                    activityTaskManagerService.mContext.getContentResolver(),
                                    "notification_bubbles",
                                    currentUserId)
                            == 2;
            Slog.i(this.TAG, "isSmartPopupViewOn=" + z + "  userId=" + currentUserId);
            return z;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.android.server.wm.FreeformContainerServiceBinder
    public final boolean okToBind() {
        List packageStrListFromDB;
        return CoreRune.MW_FREEFORM_SMART_POPUP_VIEW
                && super.okToBind()
                && (packageStrListFromDB =
                                SmartPopupViewUtil.getPackageStrListFromDB(this.mAtm.mContext))
                        != null
                && !packageStrListFromDB.isEmpty()
                && isSmartPopupViewOn();
    }

    @Override // com.android.server.wm.FreeformContainerServiceBinder
    public final boolean okToUnbind() {
        List packageStrListFromDB;
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            return (okToBind() ^ true)
                    || (packageStrListFromDB =
                                    SmartPopupViewUtil.getPackageStrListFromDB(this.mAtm.mContext))
                            == null
                    || packageStrListFromDB.isEmpty()
                    || !isSmartPopupViewOn();
        }
        return false;
    }
}
