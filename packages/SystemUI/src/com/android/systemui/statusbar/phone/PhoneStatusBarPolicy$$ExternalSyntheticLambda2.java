package com.android.systemui.statusbar.phone;

import android.app.ActivityTaskManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.RemoteException;
import android.util.Log;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

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
                    final int userStatusBarIconResId = 0;
                    if (userInfo == null || !(userInfo.isUserTypeAppSeparation() || userInfo.isSecureFolder())) {
                        if (phoneStatusBarPolicy.mUserManager.isProfile(lastResumedActivityUserId)) {
                            userStatusBarIconResId = phoneStatusBarPolicy.mUserManager.getUserStatusBarIconResId(lastResumedActivityUserId);
                        }
                    } else if (userInfo.isSecureFolder()) {
                        userStatusBarIconResId = R.drawable.ic_notification_badge;
                    }
                    phoneStatusBarPolicy.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda3
                        /* JADX WARN: Removed duplicated region for block: B:12:0x0030  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            boolean z2;
                            String profileAccessibilityString;
                            PhoneStatusBarPolicy phoneStatusBarPolicy2 = phoneStatusBarPolicy;
                            int i2 = userStatusBarIconResId;
                            int i3 = lastResumedActivityUserId;
                            String str = phoneStatusBarPolicy2.mSlotManagedProfile;
                            StatusBarIconController statusBarIconController = phoneStatusBarPolicy2.mIconController;
                            if (i2 != 0) {
                                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) phoneStatusBarPolicy2.mKeyguardStateController;
                                if (!keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mOccluded) {
                                    try {
                                        profileAccessibilityString = phoneStatusBarPolicy2.mUserManager.getProfileAccessibilityString(i3);
                                    } catch (Resources.NotFoundException unused) {
                                        ClockEventController$$ExternalSyntheticOutline0.m(i3, "Accessibility string not found for userId:", "PhoneStatusBarPolicy");
                                        profileAccessibilityString = "";
                                    }
                                    ((StatusBarIconControllerImpl) statusBarIconController).setIcon(profileAccessibilityString, str, i2);
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                            }
                            if (phoneStatusBarPolicy2.mProfileIconVisible != z2) {
                                ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, z2);
                                phoneStatusBarPolicy2.mProfileIconVisible = z2;
                            }
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
