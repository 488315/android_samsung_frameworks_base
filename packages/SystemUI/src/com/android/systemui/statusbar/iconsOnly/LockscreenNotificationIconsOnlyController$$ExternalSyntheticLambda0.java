package com.android.systemui.statusbar.iconsOnly;

import com.android.systemui.statusbar.LockscreenNotificationInfo;
import java.util.Comparator;

public final /* synthetic */ class LockscreenNotificationIconsOnlyController$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        long j = ((LockscreenNotificationInfo) obj).mSbn.getNotification().when;
        long j2 = ((LockscreenNotificationInfo) obj2).mSbn.getNotification().when;
        if (j < j2) {
            return 1;
        }
        return j > j2 ? -1 : 0;
    }
}
