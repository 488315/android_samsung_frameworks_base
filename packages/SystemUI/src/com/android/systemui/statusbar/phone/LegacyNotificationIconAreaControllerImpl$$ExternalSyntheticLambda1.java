package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        switch (this.$r8$classId) {
            case 0:
                return notificationEntry.mIcons.mShelfIcon;
            case 1:
                return notificationEntry.mIcons.mStatusBarIcon;
            default:
                return notificationEntry.mIcons.mAodIcon;
        }
    }
}
