package com.android.systemui.statusbar.notification.row;

import android.net.Uri;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotificationSettingsController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationSettingsController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ NotificationSettingsController.Listener f$0;
    public final /* synthetic */ Uri f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ NotificationSettingsController$$ExternalSyntheticLambda3(NotificationSettingsController.Listener listener, int i, String str) {
        Uri uri = ExpandableNotificationRowController.BUBBLES_SETTING_URI;
        this.f$0 = listener;
        this.f$1 = uri;
        this.f$2 = i;
        this.f$3 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationSettingsController.Listener listener = this.f$0;
                ExpandableNotificationRowController.AnonymousClass1 anonymousClass1 = (ExpandableNotificationRowController.AnonymousClass1) listener;
                anonymousClass1.onSettingChanged(this.f$2, this.f$1, this.f$3);
                break;
            default:
                NotificationSettingsController.Listener listener2 = this.f$0;
                ExpandableNotificationRowController.AnonymousClass1 anonymousClass12 = (ExpandableNotificationRowController.AnonymousClass1) listener2;
                anonymousClass12.onSettingChanged(this.f$2, this.f$1, this.f$3);
                break;
        }
    }

    public /* synthetic */ NotificationSettingsController$$ExternalSyntheticLambda3(NotificationSettingsController.Listener listener, Uri uri, int i, String str) {
        this.f$0 = listener;
        this.f$1 = uri;
        this.f$2 = i;
        this.f$3 = str;
    }
}
