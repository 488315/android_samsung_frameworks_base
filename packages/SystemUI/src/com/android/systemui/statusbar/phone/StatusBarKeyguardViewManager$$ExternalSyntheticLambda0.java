package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class StatusBarKeyguardViewManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatusBarKeyguardViewManager f$0;

    public /* synthetic */ StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(StatusBarKeyguardViewManager statusBarKeyguardViewManager, int i) {
        this.$r8$classId = i;
        this.f$0 = statusBarKeyguardViewManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.f$0;
        switch (i) {
            case 0:
                ((NotificationShadeWindowControllerImpl) statusBarKeyguardViewManager.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                break;
            case 1:
                statusBarKeyguardViewManager.updateKeyguardUnlocking();
                break;
            default:
                statusBarKeyguardViewManager.updateLastKeyguardUnlocking();
                break;
        }
    }
}
