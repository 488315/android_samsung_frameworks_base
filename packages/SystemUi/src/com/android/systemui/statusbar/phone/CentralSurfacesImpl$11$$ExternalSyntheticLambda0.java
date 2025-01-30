package com.android.systemui.statusbar.phone;

import android.hardware.fingerprint.FingerprintManager;
import android.provider.Settings;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$11$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl.C300411 f$0;

    public /* synthetic */ CentralSurfacesImpl$11$$ExternalSyntheticLambda0(CentralSurfacesImpl.C300411 c300411, int i) {
        this.$r8$classId = i;
        this.f$0 = c300411;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b7, code lost:
    
        if (((com.android.systemui.flags.FeatureFlagsRelease) r0.mFeatureFlags).isEnabled(com.android.systemui.flags.Flags.ZJ_285570694_LOCKSCREEN_TRANSITION_FROM_AOD) != false) goto L40;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl.C300411 c300411 = this.f$0;
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                boolean z2 = true;
                centralSurfacesImpl.mDeviceInteractive = true;
                if (centralSurfacesImpl.shouldAnimateDozeWakeup()) {
                    boolean z3 = Settings.Secure.getIntForUser(centralSurfacesImpl.mContext.getContentResolver(), "sfps_performant_auth_enabled_v2", -1, ((UserTrackerImpl) centralSurfacesImpl.mUserTracker).getUserId()) > 0;
                    if (!centralSurfacesImpl.mDozeServiceHost.mPulsing && ((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).mDozeAmount == 1.0f && centralSurfacesImpl.mWakefulnessLifecycle.mLastWakeReason == 1) {
                        Provider provider = centralSurfacesImpl.mFingerprintManager;
                        if (((FingerprintManager) provider.get()).isPowerbuttonFps() && ((FingerprintManager) provider.get()).hasEnrolledFingerprints() && !z3) {
                            z = true;
                            centralSurfacesImpl.mShouldDelayWakeUpAnimation = z;
                        }
                    }
                    z = false;
                    centralSurfacesImpl.mShouldDelayWakeUpAnimation = z;
                } else {
                    centralSurfacesImpl.mShouldDelayWakeUpAnimation = false;
                }
                ShadeSurface shadeSurface = centralSurfacesImpl.mShadeSurface;
                boolean z4 = centralSurfacesImpl.mShouldDelayWakeUpAnimation;
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) shadeSurface;
                if (notificationPanelViewController.mWillPlayDelayedDozeAmountAnimation != z4) {
                    notificationPanelViewController.mWillPlayDelayedDozeAmountAnimation = z4;
                    notificationPanelViewController.mWakeUpCoordinator.logDelayingClockWakeUpAnimation(z4);
                    notificationPanelViewController.mKeyguardMediaController.getClass();
                    notificationPanelViewController.positionClockAndNotifications(false);
                }
                centralSurfacesImpl.mWakeUpCoordinator.setWakingUp(true, centralSurfacesImpl.mShouldDelayWakeUpAnimation);
                centralSurfacesImpl.updateVisibleToUser();
                centralSurfacesImpl.updateIsKeyguard();
                if (centralSurfacesImpl.mDozeParameters.getAlwaysOn()) {
                    break;
                }
                z2 = false;
                centralSurfacesImpl.mShouldDelayLockscreenTransitionFromAod = z2;
                if (!z2) {
                    c300411.startLockscreenTransitionFromAod();
                    break;
                }
                break;
            case 1:
                this.f$0.startLockscreenTransitionFromAod();
                break;
            case 2:
                CentralSurfacesImpl centralSurfacesImpl2 = CentralSurfacesImpl.this;
                centralSurfacesImpl2.mCommandQueueCallbacks.onCameraLaunchGestureDetected(centralSurfacesImpl2.mLastCameraLaunchSource);
                break;
            default:
                CentralSurfacesImpl.this.mCommandQueueCallbacks.onEmergencyActionLaunchGestureDetected();
                break;
        }
    }
}
