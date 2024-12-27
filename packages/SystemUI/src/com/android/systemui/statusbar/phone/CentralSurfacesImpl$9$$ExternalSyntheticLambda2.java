package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$9$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl.AnonymousClass9 f$0;

    public /* synthetic */ CentralSurfacesImpl$9$$ExternalSyntheticLambda2(CentralSurfacesImpl.AnonymousClass9 anonymousClass9, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass9;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00cc, code lost:
    
        if (((com.android.systemui.flags.FeatureFlagsClassicRelease) r0.mFeatureFlags).isEnabled(com.android.systemui.flags.Flags.ZJ_285570694_LOCKSCREEN_TRANSITION_FROM_AOD) != false) goto L48;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r8 = this;
            int r0 = r8.$r8$classId
            com.android.systemui.statusbar.phone.CentralSurfacesImpl$9 r8 = r8.f$0
            switch(r0) {
                case 0: goto L1d;
                case 1: goto L13;
                case 2: goto Lb;
                default: goto L7;
            }
        L7:
            r8.startLockscreenTransitionFromAod()
            return
        Lb:
            com.android.systemui.statusbar.phone.CentralSurfacesImpl r8 = com.android.systemui.statusbar.phone.CentralSurfacesImpl.this
            com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks r8 = r8.mCommandQueueCallbacks
            r8.onEmergencyActionLaunchGestureDetected()
            return
        L13:
            com.android.systemui.statusbar.phone.CentralSurfacesImpl r8 = com.android.systemui.statusbar.phone.CentralSurfacesImpl.this
            com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks r0 = r8.mCommandQueueCallbacks
            int r8 = r8.mLastCameraLaunchSource
            r0.onCameraLaunchGestureDetected(r8)
            return
        L1d:
            com.android.systemui.statusbar.phone.CentralSurfacesImpl r0 = com.android.systemui.statusbar.phone.CentralSurfacesImpl.this
            r1 = 1
            r0.mDeviceInteractive = r1
            com.android.systemui.FeatureFlagsImpl r2 = com.android.systemui.Flags.FEATURE_FLAGS
            r2.getClass()
            com.android.systemui.keyguard.MigrateClocksToBlueprint r2 = com.android.systemui.keyguard.MigrateClocksToBlueprint.INSTANCE
            com.android.systemui.Flags.migrateClocksToBlueprint()
            com.android.systemui.statusbar.phone.DozeServiceHost r2 = r0.mDozeServiceHost
            boolean r2 = r2.mAnimateWakeup
            r3 = 0
            if (r2 == 0) goto L43
            com.android.systemui.statusbar.phone.BiometricUnlockController r2 = r0.mBiometricUnlockController
            int r2 = r2.mMode
            if (r2 == r1) goto L43
            com.android.systemui.statusbar.SecLightRevealScrimHelper r2 = r0.mSecLightRevealScrimHelper
            boolean r2 = r2.disableLightRevealAnimation()
            if (r2 != 0) goto L43
            r2 = r1
            goto L44
        L43:
            r2 = r3
        L44:
            if (r2 == 0) goto La1
            android.content.Context r2 = r0.mContext
            android.content.ContentResolver r2 = r2.getContentResolver()
            com.android.systemui.settings.UserTracker r4 = r0.mUserTracker
            com.android.systemui.settings.UserTrackerImpl r4 = (com.android.systemui.settings.UserTrackerImpl) r4
            int r5 = r4.getUserId()
            java.lang.String r6 = "sfps_performant_auth_enabled_v2"
            r7 = -1
            int r2 = android.provider.Settings.Secure.getIntForUser(r2, r6, r7, r5)
            if (r2 <= 0) goto L60
            r2 = r1
            goto L61
        L60:
            r2 = r3
        L61:
            com.android.systemui.statusbar.phone.DozeServiceHost r5 = r0.mDozeServiceHost
            boolean r5 = r5.mPulsing
            if (r5 != 0) goto L9d
            com.android.systemui.statusbar.SysuiStatusBarStateController r5 = r0.mStatusBarStateController
            com.android.systemui.statusbar.StatusBarStateControllerImpl r5 = (com.android.systemui.statusbar.StatusBarStateControllerImpl) r5
            float r5 = r5.mDozeAmount
            r6 = 1065353216(0x3f800000, float:1.0)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 != 0) goto L9d
            com.android.systemui.keyguard.WakefulnessLifecycle r5 = r0.mWakefulnessLifecycle
            int r5 = r5.mLastWakeReason
            if (r5 != r1) goto L9d
            javax.inject.Provider r5 = r0.mFingerprintManager
            java.lang.Object r6 = r5.get()
            if (r6 == 0) goto L9d
            java.lang.Object r5 = r5.get()
            android.hardware.fingerprint.FingerprintManager r5 = (android.hardware.fingerprint.FingerprintManager) r5
            boolean r5 = r5.isPowerbuttonFps()
            if (r5 == 0) goto L9d
            int r4 = r4.getUserId()
            com.android.keyguard.KeyguardUpdateMonitor r5 = r0.mKeyguardUpdateMonitor
            boolean r4 = r5.isUnlockWithFingerprintPossible(r4)
            if (r4 == 0) goto L9d
            if (r2 != 0) goto L9d
            r2 = r1
            goto L9e
        L9d:
            r2 = r3
        L9e:
            r0.mShouldDelayWakeUpAnimation = r2
            goto La3
        La1:
            r0.mShouldDelayWakeUpAnimation = r3
        La3:
            boolean r2 = r0.mShouldDelayWakeUpAnimation
            com.android.systemui.shade.ShadeSurface r4 = r0.mShadeSurface
            r4.setWillPlayDelayedDozeAmountAnimation(r2)
            boolean r2 = r0.mShouldDelayWakeUpAnimation
            com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r4 = r0.mWakeUpCoordinator
            r4.setWakingUp(r1, r2)
            r0.updateIsKeyguard(r3)
            com.android.systemui.statusbar.phone.DozeParameters r2 = r0.mDozeParameters
            boolean r4 = r2.getAlwaysOn()
            if (r4 == 0) goto Lcf
            boolean r2 = r2.getDisplayNeedsBlanking()
            if (r2 != 0) goto Lcf
            com.android.systemui.flags.ReleasedFlag r2 = com.android.systemui.flags.Flags.ZJ_285570694_LOCKSCREEN_TRANSITION_FROM_AOD
            com.android.systemui.flags.FeatureFlags r4 = r0.mFeatureFlags
            com.android.systemui.flags.FeatureFlagsClassicRelease r4 = (com.android.systemui.flags.FeatureFlagsClassicRelease) r4
            boolean r2 = r4.isEnabled(r2)
            if (r2 == 0) goto Lcf
            goto Ld0
        Lcf:
            r1 = r3
        Ld0:
            r0.mShouldDelayLockscreenTransitionFromAod = r1
            if (r1 != 0) goto Ld7
            r8.startLockscreenTransitionFromAod()
        Ld7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl$9$$ExternalSyntheticLambda2.run():void");
    }
}
