package com.android.server.am;

import java.util.function.Consumer;

public final /* synthetic */
class AppRestrictionController$RestrictionSettings$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings =
                (AppRestrictionController.RestrictionSettings.PkgSettings) obj;
        int i = 0;
        pkgSettings.mCurrentRestrictionLevel = 0;
        pkgSettings.mLastRestrictionLevel = 0;
        pkgSettings.mLevelChangeTime = 0L;
        pkgSettings.mReason = 256;
        if (pkgSettings.mLastNotificationShownTime == null) {
            return;
        }
        while (true) {
            long[] jArr = pkgSettings.mLastNotificationShownTime;
            if (i >= jArr.length) {
                return;
            }
            jArr[i] = 0;
            i++;
        }
    }
}
