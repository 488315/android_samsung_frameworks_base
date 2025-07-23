package com.android.systemui.keyguard;

import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.SurfaceControl;
import com.android.systemui.LsRune;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController$Stub$Proxy;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimationForLauncher$1 implements Runnable {
    public final /* synthetic */ KeyguardSecLegacyUnlockAnimationControllerImpl this$0;

    public KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimationForLauncher$1(KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl) {
        this.this$0 = keyguardSecLegacyUnlockAnimationControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (!this.this$0.aodAmbientWallpaperHelper.isAODFullScreenAndShowing()) {
            ((KeyguardFastBioUnlockController) this.this$0.keyguardFastBioUnlockControllerLazy.get()).reset();
        }
        if (LsRune.AOD_LIGHT_REVEAL && ((NotificationShadeWindowControllerImpl) this.this$0.notificationShadeWindowController).mHelper.getCurrentState().forceVisibleForUnlockAnimation) {
            ((CentralSurfacesImpl) ((CentralSurfaces) this.this$0.centralSurfacesLazy.get())).mLightRevealScrim.setRevealAmount(1.0f);
        }
        KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
        keyguardSecLegacyUnlockAnimationControllerImpl.aodAmbientWallpaperHelper.needWallpaperForUnlockAnimation = false;
        ((NotificationShadeWindowControllerImpl) keyguardSecLegacyUnlockAnimationControllerImpl.notificationShadeWindowController).mHelper.resetForceVisibleForUnlockAnimation();
        this.this$0.updateLeashAlpha(1.0f);
        this.this$0.applyTransaction();
        try {
            KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = this.this$0;
            ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = keyguardSecLegacyUnlockAnimationControllerImpl2.launcherUnlockController;
            if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
                iLauncherUnlockAnimationController$Stub$Proxy.playUnlockAnimation(200L, keyguardSecLegacyUnlockAnimationControllerImpl2.animStartDelay);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.this$0.updateKeyguardSurface(true);
        SurfaceControl.Transaction transaction = this.this$0.curTransaction;
        if (transaction != null) {
            transaction.close();
        }
        KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl3 = this.this$0;
        keyguardSecLegacyUnlockAnimationControllerImpl3.curTransaction = null;
        keyguardSecLegacyUnlockAnimationControllerImpl3.curLeash = null;
        try {
            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = keyguardSecLegacyUnlockAnimationControllerImpl3.surfaceBehindRemoteAnimationFinishedCallback;
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
            Log.d("KeyguardUnlock", "IRemoteAnimationFinishedCallback#onAnimationFinished");
        } catch (RemoteException e2) {
            e2.printStackTrace();
        }
        KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl4 = this.this$0;
        keyguardSecLegacyUnlockAnimationControllerImpl4.surfaceBehindRemoteAnimationFinishedCallback = null;
        Log.d("KeyguardUnlock", "disablePanelDetectorAndRestoreKeyguard");
        keyguardSecLegacyUnlockAnimationControllerImpl4.delayableExecutor.executeDelayed(new KeyguardSecLegacyUnlockAnimationControllerImpl$disablePanelDetectorAndRestoreKeyguard$1(keyguardSecLegacyUnlockAnimationControllerImpl4), 500L);
        this.this$0.animStartDelay = 0L;
    }
}
