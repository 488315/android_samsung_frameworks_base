package com.android.systemui.statusbar.phone;

import android.app.ActivityTaskManager;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class PhoneStatusBarPolicy$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PhoneStatusBarPolicy f$0;

    public /* synthetic */ PhoneStatusBarPolicy$$ExternalSyntheticLambda0(PhoneStatusBarPolicy phoneStatusBarPolicy, int i) {
        this.$r8$classId = i;
        this.f$0 = phoneStatusBarPolicy;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final int i = 0;
        int i2 = this.$r8$classId;
        final PhoneStatusBarPolicy phoneStatusBarPolicy = this.f$0;
        switch (i2) {
            case 0:
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotScreenRecord, true);
                break;
            case 1:
                boolean z = PhoneStatusBarPolicy.DEBUG;
                phoneStatusBarPolicy.updateVolumeZen();
                break;
            case 2:
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotScreenRecord, false);
                break;
            case 3:
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotScreenRecord, false);
                break;
            case 4:
                ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconAccessibilityLiveRegion(0, phoneStatusBarPolicy.mSlotScreenRecord);
                break;
            default:
                boolean z2 = PhoneStatusBarPolicy.DEBUG;
                phoneStatusBarPolicy.getClass();
                try {
                    final int lastResumedActivityUserId = ActivityTaskManager.getService().getLastResumedActivityUserId();
                    UserInfo userInfo = phoneStatusBarPolicy.mUserManager.getUserInfo(lastResumedActivityUserId);
                    if (userInfo == null || (!userInfo.isUserTypeAppSeparation() && !userInfo.isSecureFolder())) {
                        i = phoneStatusBarPolicy.mUserManager.getUserStatusBarIconResId(lastResumedActivityUserId);
                        phoneStatusBarPolicy.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda7
                            /* JADX WARN: Removed duplicated region for block: B:16:0x0053  */
                            /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
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
                                    if (r1 == 0) goto L4e
                                    com.android.systemui.statusbar.policy.KeyguardStateController r4 = r0.mKeyguardStateController
                                    com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r4 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r4
                                    boolean r5 = r4.mShowing
                                    if (r5 == 0) goto L18
                                    boolean r4 = r4.mOccluded
                                    if (r4 == 0) goto L4e
                                L18:
                                    boolean r4 = com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile()
                                    if (r4 == 0) goto L35
                                    boolean r4 = android.multiuser.Flags.enablePrivateSpaceFeatures()
                                    if (r4 == 0) goto L35
                                    android.os.UserManager r4 = r0.mUserManager     // Catch: android.content.res.Resources.NotFoundException -> L2b
                                    java.lang.String r6 = r4.getProfileAccessibilityString(r6)     // Catch: android.content.res.Resources.NotFoundException -> L2b
                                    goto L46
                                L2b:
                                    java.lang.String r4 = "Accessibility string not found for userId:"
                                    java.lang.String r5 = "PhoneStatusBarPolicy"
                                    com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(r6, r4, r5)
                                    java.lang.String r6 = ""
                                    goto L46
                                L35:
                                    android.app.admin.DevicePolicyManager r6 = r0.mDevicePolicyManager
                                    android.app.admin.DevicePolicyResourcesManager r6 = r6.getResources()
                                    com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda5 r4 = new com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda5
                                    r4.<init>(r0)
                                    java.lang.String r5 = "SystemUi.STATUS_BAR_WORK_ICON_ACCESSIBILITY"
                                    java.lang.String r6 = r6.getString(r5, r4)
                                L46:
                                    r4 = r3
                                    com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r4 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r4
                                    r4.setIcon(r6, r2, r1)
                                    r6 = 1
                                    goto L4f
                                L4e:
                                    r6 = 0
                                L4f:
                                    boolean r1 = r0.mProfileIconVisible
                                    if (r1 == r6) goto L5a
                                    com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r3 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r3
                                    r3.setIconVisibility(r2, r6)
                                    r0.mProfileIconVisible = r6
                                L5a:
                                    return
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda7.run():void");
                            }
                        });
                        break;
                    }
                    if (userInfo.isSecureFolder()) {
                        i = R.drawable.ic_notification_badge;
                    }
                    phoneStatusBarPolicy.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            /*
                                this = this;
                                com.android.systemui.statusbar.phone.PhoneStatusBarPolicy r0 = com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.this
                                int r1 = r2
                                int r6 = r3
                                java.lang.String r2 = r0.mSlotManagedProfile
                                com.android.systemui.statusbar.phone.ui.StatusBarIconController r3 = r0.mIconController
                                if (r1 == 0) goto L4e
                                com.android.systemui.statusbar.policy.KeyguardStateController r4 = r0.mKeyguardStateController
                                com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r4 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r4
                                boolean r5 = r4.mShowing
                                if (r5 == 0) goto L18
                                boolean r4 = r4.mOccluded
                                if (r4 == 0) goto L4e
                            L18:
                                boolean r4 = com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile()
                                if (r4 == 0) goto L35
                                boolean r4 = android.multiuser.Flags.enablePrivateSpaceFeatures()
                                if (r4 == 0) goto L35
                                android.os.UserManager r4 = r0.mUserManager     // Catch: android.content.res.Resources.NotFoundException -> L2b
                                java.lang.String r6 = r4.getProfileAccessibilityString(r6)     // Catch: android.content.res.Resources.NotFoundException -> L2b
                                goto L46
                            L2b:
                                java.lang.String r4 = "Accessibility string not found for userId:"
                                java.lang.String r5 = "PhoneStatusBarPolicy"
                                com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(r6, r4, r5)
                                java.lang.String r6 = ""
                                goto L46
                            L35:
                                android.app.admin.DevicePolicyManager r6 = r0.mDevicePolicyManager
                                android.app.admin.DevicePolicyResourcesManager r6 = r6.getResources()
                                com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda5 r4 = new com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda5
                                r4.<init>(r0)
                                java.lang.String r5 = "SystemUi.STATUS_BAR_WORK_ICON_ACCESSIBILITY"
                                java.lang.String r6 = r6.getString(r5, r4)
                            L46:
                                r4 = r3
                                com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r4 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r4
                                r4.setIcon(r6, r2, r1)
                                r6 = 1
                                goto L4f
                            L4e:
                                r6 = 0
                            L4f:
                                boolean r1 = r0.mProfileIconVisible
                                if (r1 == r6) goto L5a
                                com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl r3 = (com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl) r3
                                r3.setIconVisibility(r2, r6)
                                r0.mProfileIconVisible = r6
                            L5a:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda7.run():void");
                        }
                    });
                } catch (RemoteException e) {
                    Log.w("PhoneStatusBarPolicy", "updateProfileIcon: ", e);
                }
                break;
        }
    }
}
