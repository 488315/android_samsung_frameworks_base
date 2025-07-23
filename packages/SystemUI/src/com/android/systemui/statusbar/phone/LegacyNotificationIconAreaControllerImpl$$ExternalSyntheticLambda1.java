package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
