package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = LegacyNotificationIconAreaControllerImpl.$r8$clinit;
                return notificationEntry.mIcons.mShelfIcon;
            case 1:
                int i2 = LegacyNotificationIconAreaControllerImpl.$r8$clinit;
                return notificationEntry.mIcons.mStatusBarIcon;
            default:
                int i3 = LegacyNotificationIconAreaControllerImpl.$r8$clinit;
                return notificationEntry.mIcons.mAodIcon;
        }
    }
}
