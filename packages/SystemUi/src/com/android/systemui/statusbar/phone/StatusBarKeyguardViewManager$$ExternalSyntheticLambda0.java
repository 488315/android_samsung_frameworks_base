package com.android.systemui.statusbar.phone;

import com.android.systemui.shade.NotificationShadeWindowControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatusBarKeyguardViewManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatusBarKeyguardViewManager f$0;

    public /* synthetic */ StatusBarKeyguardViewManager$$ExternalSyntheticLambda0(StatusBarKeyguardViewManager statusBarKeyguardViewManager, int i) {
        this.$r8$classId = i;
        this.f$0 = statusBarKeyguardViewManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.updateKeyguardUnlocking();
                break;
            case 1:
                this.f$0.updateLastKeyguardUnlocking();
                break;
            case 2:
                this.f$0.updateLastCoverClosed();
                break;
            default:
                ((NotificationShadeWindowControllerImpl) this.f$0.mNotificationShadeWindowController).setKeyguardFadingAway(false);
                break;
        }
    }
}
