package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;

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
