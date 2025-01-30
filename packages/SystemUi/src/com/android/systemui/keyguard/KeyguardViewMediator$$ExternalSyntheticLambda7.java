package com.android.systemui.keyguard;

import android.os.SystemClock;
import android.os.SystemProperties;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.LsRune;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.samsung.android.cover.CoverState;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.sdp.internal.SdpAuthenticator;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda7(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediatorHelperImpl.updateMonitor;
                CoverState coverState = keyguardUpdateMonitor.getCoverState();
                boolean isSecure = ((KeyguardViewMediator) keyguardViewMediatorHelperImpl.viewMediatorLazy.get()).isSecure();
                int i = keyguardViewMediatorHelperImpl.switchingUserId;
                if (keyguardViewMediatorHelperImpl.lastWakeReason == 103 && coverState != null && coverState.attached && keyguardViewMediatorHelperImpl.settingsHelper.isAutomaticUnlockEnabled()) {
                    if (i == -1 ? !isSecure || keyguardUpdateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId()) : !isSecure) {
                        z = true;
                    }
                    if (z) {
                        keyguardViewMediatorHelperImpl.removeShowMsg();
                        return;
                    }
                    return;
                }
                return;
            case 1:
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.f$0;
                keyguardViewMediatorHelperImpl2.hidingByDisabled = false;
                if (((Boolean) keyguardViewMediatorHelperImpl2.getViewMediatorProvider().isWakeAndUnlocking.invoke()).booleanValue() && keyguardViewMediatorHelperImpl2.drawnCallback != null) {
                    ((KeyguardViewController) keyguardViewMediatorHelperImpl2.viewControllerLazy.get()).getViewRootImpl().setReportNextDraw(false, "BioUnlock");
                    keyguardViewMediatorHelperImpl2.notifyDrawn();
                }
                ((DesktopManagerImpl) keyguardViewMediatorHelperImpl2.desktopManager).notifyDismissKeyguard();
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardViewMediatorHelperImpl2.updateMonitor;
                keyguardUpdateMonitor2.setUnlockingKeyguard(true);
                keyguardUpdateMonitor2.clearFailedUnlockAttempts(false);
                keyguardUpdateMonitor2.clearBiometricRecognized();
                keyguardUpdateMonitor2.requestSessionClose();
                keyguardViewMediatorHelperImpl2.f297pm.userActivity(SystemClock.uptimeMillis(), 2, 0);
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                    keyguardViewMediatorHelperImpl2.foldControllerImpl.resetFoldOpenState(false);
                }
                if (keyguardViewMediatorHelperImpl2.isSecure()) {
                    keyguardViewMediatorHelperImpl2.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$onSecurityPropertyUpdated$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SystemProperties.set("sys.locksecured", KeyguardViewMediatorHelperImpl.this.isShowing() ? "true" : "false");
                        }
                    });
                }
                keyguardViewMediatorHelperImpl2.disableRemoteUnlockAnimation = false;
                KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = keyguardViewMediatorHelperImpl2.fixedRotationMonitor;
                if (keyguardFixedRotationMonitor.isMonitorStarted && keyguardFixedRotationMonitor.isFixedRotated) {
                    z = true;
                }
                if (!z) {
                    keyguardFixedRotationMonitor.cancel();
                }
                if (LsRune.AOD_FULLSCREEN) {
                    ((CentralSurfacesImpl) ((CentralSurfaces) keyguardViewMediatorHelperImpl2.centralSurfacesLazy.get())).mLightRevealScrim.setRevealAmount(1.0f);
                    return;
                }
                return;
            case 2:
                this.f$0.onAbortKeyguardDone();
                return;
            case 3:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.f$0;
                if (keyguardViewMediatorHelperImpl3.fastUnlockController.isFastWakeAndUnlockMode()) {
                    return;
                }
                synchronized (keyguardViewMediatorHelperImpl3.getLock()) {
                    keyguardViewMediatorHelperImpl3.drawnCallback = null;
                    Unit unit = Unit.INSTANCE;
                }
                return;
            case 4:
                ((KeyguardWallpaperController) this.f$0.keyguardWallpaper).cleanUp(true);
                return;
            case 5:
                KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.f$0.fastUnlockController;
                if (keyguardFastBioUnlockController.isEnabled() && !keyguardFastBioUnlockController.isFastWakeAndUnlockMode() && !keyguardFastBioUnlockController.isFastUnlockMode()) {
                    z = true;
                }
                if (z) {
                    keyguardFastBioUnlockController.reset();
                    return;
                }
                return;
            case 6:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = this.f$0;
                keyguardViewMediatorHelperImpl4.getClass();
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
                    KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl4.foldControllerImpl;
                    if (keyguardFoldControllerImpl.initShowTime == 0) {
                        keyguardFoldControllerImpl.initShowTime = System.currentTimeMillis();
                    }
                }
                keyguardViewMediatorHelperImpl4.fastUnlockController.reset();
                keyguardViewMediatorHelperImpl4.updateMonitor.setUnlockingKeyguard(false);
                keyguardViewMediatorHelperImpl4.hidingByDisabled = false;
                return;
            case 7:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = this.f$0;
                if (keyguardViewMediatorHelperImpl5.updateMonitor.getUserHasTrust(((UserTrackerImpl) keyguardViewMediatorHelperImpl5.userTracker).getUserId())) {
                    return;
                }
                ((KnoxStateMonitorImpl) keyguardViewMediatorHelperImpl5.knoxStateMonitor).getClass();
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (!SemPersonaManager.isDoEnabled(currentUser)) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("lockSdp :: Maybe keyguard shown as user ", currentUser, "KnoxStateMonitorImpl");
                    return;
                }
                android.util.Log.d("KnoxStateMonitorImpl", "lockSdp :: Device Owner has been locked");
                try {
                    SdpAuthenticator.getInstance().onDeviceOwnerLocked(currentUser);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case 8:
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = this.f$0;
                if (keyguardViewMediatorHelperImpl6.isSecure()) {
                    keyguardViewMediatorHelperImpl6.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$onSecurityPropertyUpdated$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SystemProperties.set("sys.locksecured", KeyguardViewMediatorHelperImpl.this.isShowing() ? "true" : "false");
                        }
                    });
                    return;
                }
                return;
            case 9:
                this.f$0.getClass();
                return;
            case 10:
                this.f$0.onAbortHandleStartKeyguardExitAnimation();
                return;
            default:
                this.f$0.doKeyguardPendingIntent = null;
                return;
        }
    }
}
