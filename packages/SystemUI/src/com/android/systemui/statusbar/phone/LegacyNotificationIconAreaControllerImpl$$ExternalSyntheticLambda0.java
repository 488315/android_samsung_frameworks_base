package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.StatusBarIconView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LegacyNotificationIconAreaControllerImpl f$0;
    public final /* synthetic */ StatusBarIconView f$1;

    public /* synthetic */ LegacyNotificationIconAreaControllerImpl$$ExternalSyntheticLambda0(LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl, StatusBarIconView statusBarIconView, int i) {
        this.$r8$classId = i;
        this.f$0 = legacyNotificationIconAreaControllerImpl;
        this.f$1 = statusBarIconView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl = this.f$0;
                legacyNotificationIconAreaControllerImpl.updateTintForIcon(this.f$1, legacyNotificationIconAreaControllerImpl.mIconTint);
                break;
            default:
                LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl2 = this.f$0;
                legacyNotificationIconAreaControllerImpl2.updateTintForIcon(this.f$1, legacyNotificationIconAreaControllerImpl2.mKeyguardNotifIconTint);
                break;
        }
    }
}
