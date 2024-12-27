package com.android.systemui.statusbar.iconsOnly;

import com.android.systemui.statusbar.LockscreenNotificationInfo;
import java.util.Comparator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
