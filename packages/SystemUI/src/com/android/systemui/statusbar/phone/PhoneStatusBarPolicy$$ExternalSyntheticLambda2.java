package com.android.systemui.statusbar.phone;

import android.app.ActivityTaskManager;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PhoneStatusBarPolicy$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PhoneStatusBarPolicy f$0;

    public /* synthetic */ PhoneStatusBarPolicy$$ExternalSyntheticLambda2(PhoneStatusBarPolicy phoneStatusBarPolicy, int i) {
        this.$r8$classId = i;
        this.f$0 = phoneStatusBarPolicy;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final PhoneStatusBarPolicy phoneStatusBarPolicy = this.f$0;
        switch (i) {
            case 0:
                boolean z = PhoneStatusBarPolicy.DEBUG;
                phoneStatusBarPolicy.getClass();
                try {
                    final int lastResumedActivityUserId = ActivityTaskManager.getService().getLastResumedActivityUserId();
                    UserInfo userInfo = phoneStatusBarPolicy.mUserManager.getUserInfo(lastResumedActivityUserId);
                    final int i2 = 0;
                    if (userInfo == null || !(userInfo.isUserTypeAppSeparation() || userInfo.isSecureFolder())) {
                        if (phoneStatusBarPolicy.mUserManager.isProfile(lastResumedActivityUserId)) {
                            i2 = phoneStatusBarPolicy.mUserManager.getUserStatusBarIconResId(lastResumedActivityUserId);
                        }
                    } else if (userInfo.isSecureFolder()) {
                        i2 = R.drawable.ic_notification_badge;
                    }
                    phoneStatusBarPolicy.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda3
                        /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
                        /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                r6 = this;
                                com.android.systemui.statusbar.phone.PhoneStatusBarPolicy r0 = com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.this
                                int r1 = r2
                                int r6 = r3
                                java.lang.String r2 = r0.mSlotManagedProfile
                                com.android.systemui.statusbar.phone.ui.StatusBarIconController r3 = r0.mIconController
                                if (r1 == 0) goto L30
                                com.android.systemui.statusbar.policy.KeyguardStateController r4 = r0.mKeyguardStateController
                                com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r4 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r4
                                boolean r5 = r4.mShowing
                                if (r5 == 0) goto L18
                                boolean r4 = r4.mOccluded
                                if (r4 == 0) goto L30
                            L18:
                                android.os.UserManager r4 = r0.mUserManager     // Catch: android.content.res.Resources.NotFoundException -> L1f
                                java.lang.String r6 = r4.getProfileAccessibilityString(r6)     // Catch: android.content.res.Resources.NotFoundException -> L1f
                                goto L28
                            L1f:
                                java.lang.String r4 = "Accessibility string not found for userId:"
                                java.lang.String r5 = "PhoneStatusBarPolicy"
                                com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0.m(r6, r4, r5)
                                java.lang.String r6 = ""
                            L28:
                                r4 = r3
                                com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r4 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r4
                                r4.setIcon(r6, r2, r1)
                                r6 = 1
                                goto L31
                            L30:
                                r6 = 0
                            L31:
                                boolean r1 = r0.mProfileIconVisible
                                if (r1 == r6) goto L3c
                                com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r3 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r3
                                r3.setIconVisibility(r2, r6)
                                r0.mProfileIconVisible = r6
                            L3c:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda3.run():void");
                        }
                    });
                    break;
                } catch (RemoteException e) {
                    Log.w("PhoneStatusBarPolicy", "updateProfileIcon: ", e);
                    return;
                }
                break;
            default:
                boolean z2 = PhoneStatusBarPolicy.DEBUG;
                phoneStatusBarPolicy.updateVolumeZen();
                break;
        }
    }
}
