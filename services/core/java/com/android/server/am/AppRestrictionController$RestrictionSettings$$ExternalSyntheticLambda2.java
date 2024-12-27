package com.android.server.am;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
