package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.StatusBarIconView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
            case 1:
                LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl2 = this.f$0;
                legacyNotificationIconAreaControllerImpl2.updateTintForIcon(this.f$1, legacyNotificationIconAreaControllerImpl2.mKeyguardNotifIconTint);
                break;
            default:
                LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl3 = this.f$0;
                legacyNotificationIconAreaControllerImpl3.updateTintForIcon(this.f$1, legacyNotificationIconAreaControllerImpl3.mShelfIcons.mShelfIconColor);
                break;
        }
    }
}
