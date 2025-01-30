package com.android.systemui.keyguard;

import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda3 implements BooleanSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda3(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediatorHelperImpl;
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0110, code lost:
    
        if (r0 != false) goto L54;
     */
    @Override // java.util.function.BooleanSupplier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean getAsBoolean() {
        boolean z;
        int i;
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.f$0;
                if (keyguardViewMediatorHelperImpl.isSecure()) {
                    if (keyguardViewMediatorHelperImpl.activityManager.getLockTaskModeState() == 0) {
                        z = false;
                        break;
                    } else {
                        z = true;
                        break;
                    }
                }
                if (!keyguardViewMediatorHelperImpl.updateMonitor.isForcedLock()) {
                    return true;
                }
                return false;
            case 1:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = this.f$0;
                keyguardViewMediatorHelperImpl2.fastUnlockController.logLapTime("onWakeAndUnlocking", new Object[0]);
                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_BIO_WAKE_AND_UNLOCK);
                if (!keyguardViewMediatorHelperImpl2.isFastWakeAndUnlockMode()) {
                    return false;
                }
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY_OPTICAL) {
                    keyguardViewMediatorHelperImpl2.updateMonitor.removeMaskViewForOpticalFpSensor();
                }
                keyguardViewMediatorHelperImpl2.removeShowMsg();
                ((KeyguardViewController) keyguardViewMediatorHelperImpl2.viewControllerLazy.get()).onWakeAndUnlock();
                return true;
            case 2:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = this.f$0;
                boolean isKeyguardHiding = keyguardViewMediatorHelperImpl3.isKeyguardHiding();
                boolean isUnlockStartedOrFinished = keyguardViewMediatorHelperImpl3.isUnlockStartedOrFinished();
                boolean z2 = !keyguardViewMediatorHelperImpl3.isShowing() && ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) keyguardViewMediatorHelperImpl3.shadeWindowControllerHelper$delegate.getValue())).getCurrentState().keyguardShowing;
                boolean z3 = !keyguardViewMediatorHelperImpl3.isShowing() && ((KeyguardViewMediator) keyguardViewMediatorHelperImpl3.viewMediatorLazy.get()).isInputRestricted();
                if (!isKeyguardHiding && (!isUnlockStartedOrFinished || z2 || z3)) {
                    r1 = false;
                }
                if (r1) {
                    StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("cancel handleHide ", isKeyguardHiding, " ", isUnlockStartedOrFinished, " ");
                    m69m.append(z2);
                    m69m.append(" ");
                    m69m.append(z3);
                    KeyguardViewMediatorHelperImpl.logD(m69m.toString());
                }
                return r1;
            case 3:
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl4 = this.f$0;
                if (keyguardViewMediatorHelperImpl4.isUnlockStartedOrFinished()) {
                    i = 1;
                } else if (((KeyguardViewMediator) keyguardViewMediatorHelperImpl4.viewMediatorLazy.get()).isHiding()) {
                    i = 0;
                } else {
                    keyguardViewMediatorHelperImpl4.getViewMediatorProvider().setShowingLocked.invoke(Boolean.valueOf(keyguardViewMediatorHelperImpl4.isShowing()), Boolean.TRUE);
                    keyguardViewMediatorHelperImpl4.onAbortHandleStartKeyguardExitAnimation();
                    i = 2;
                }
                if (i == 0) {
                    return false;
                }
                KeyguardViewMediatorHelperImpl.logD("cancel handleCancelKeyguardExitAnimation why=" + i);
                return true;
            case 4:
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl5 = this.f$0;
                keyguardViewMediatorHelperImpl5.getHandler().postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$doKeyguardLockedAfterUnlockAnimation$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Log.m138d("KeyguardViewMediator", "PendingPinLock : doKeyguardLockedAfterUnlockAnimation");
                        KeyguardViewMediatorHelperImpl.this.doKeyguardLocked(null);
                    }
                }, ((KeyguardUnlockAnimationController) keyguardViewMediatorHelperImpl5.unlockAnimationControllerLazy.get()).getUnlockAnimationDuration());
                return true;
            default:
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl6 = this.f$0;
                KeyguardDisplayManager keyguardDisplayManager = keyguardViewMediatorHelperImpl6.keyguardDisplayManager;
                keyguardDisplayManager.getClass();
                KeyguardVisibilityMonitor keyguardVisibilityMonitor = (KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class);
                if (keyguardVisibilityMonitor.curVisibility == 0) {
                    keyguardVisibilityMonitor.addVisibilityChangedListener(keyguardDisplayManager.mVisibilityListener);
                } else {
                    keyguardDisplayManager.hide();
                }
                if (keyguardViewMediatorHelperImpl6.isAODShowStateCbRegistered) {
                    keyguardViewMediatorHelperImpl6.settingsHelper.unregisterCallback(keyguardViewMediatorHelperImpl6.aodShowStateCallback);
                }
                keyguardViewMediatorHelperImpl6.isAODShowStateCbRegistered = false;
                keyguardViewMediatorHelperImpl6.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$registerSysDumpHeap$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediatorHelperImpl.this.sysDumpTrigger.getClass();
                    }
                });
                return false;
        }
    }
}
