package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
                statusBarKeyguardViewManager.updateLastCoverClosed();
                break;
            default:
                statusBarKeyguardViewManager.reset(true);
                break;
        }
    }

    public /* synthetic */ StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(StatusBarKeyguardViewManager statusBarKeyguardViewManager, boolean z) {
        this.$r8$classId = 2;
        this.f$0 = statusBarKeyguardViewManager;
    }
}
