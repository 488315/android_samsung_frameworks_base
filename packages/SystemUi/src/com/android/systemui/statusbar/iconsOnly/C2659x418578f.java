package com.android.systemui.statusbar.iconsOnly;

import com.android.systemui.statusbar.LockscreenNotificationInfo;
import java.util.Comparator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2659x418578f implements Comparator {
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
