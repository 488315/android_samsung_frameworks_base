package com.android.systemui.keyguard;

import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.SurfaceControl;
import com.android.systemui.LsRune;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;

/* loaded from: classes2.dex */
public final class KeyguardSecLegacyUnlockAnimationControllerImpl$onFinished$1 implements Runnable {
    public final /* synthetic */ KeyguardSecLegacyUnlockAnimationControllerImpl this$0;

    public KeyguardSecLegacyUnlockAnimationControllerImpl$onFinished$1(KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl) {
        this.this$0 = keyguardSecLegacyUnlockAnimationControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SurfaceControl.Transaction transaction = this.this$0.curTransaction;
        if (transaction != null) {
            transaction.close();
        }
        final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
        keyguardSecLegacyUnlockAnimationControllerImpl.curTransaction = null;
        keyguardSecLegacyUnlockAnimationControllerImpl.curLeash = null;
        keyguardSecLegacyUnlockAnimationControllerImpl.openingWallpaperLeash = null;
        keyguardSecLegacyUnlockAnimationControllerImpl.closingWallpaperLeash = null;
        keyguardSecLegacyUnlockAnimationControllerImpl.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$onFinished$1.1
            @Override // java.lang.Runnable
            public final void run() {
                if (!keyguardSecLegacyUnlockAnimationControllerImpl.aodAmbientWallpaperHelper.isAODFullScreenAndShowing()) {
                    ((KeyguardFastBioUnlockController) keyguardSecLegacyUnlockAnimationControllerImpl.keyguardFastBioUnlockControllerLazy.get()).reset();
                }
                if (LsRune.AOD_LIGHT_REVEAL && ((NotificationShadeWindowControllerImpl) keyguardSecLegacyUnlockAnimationControllerImpl.notificationShadeWindowController).mHelper.getCurrentState().forceVisibleForUnlockAnimation) {
                    ((CentralSurfacesImpl) ((CentralSurfaces) keyguardSecLegacyUnlockAnimationControllerImpl.centralSurfacesLazy.get())).mLightRevealScrim.setRevealAmount(1.0f);
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = keyguardSecLegacyUnlockAnimationControllerImpl;
                keyguardSecLegacyUnlockAnimationControllerImpl2.aodAmbientWallpaperHelper.needWallpaperForUnlockAnimation = false;
                ((NotificationShadeWindowControllerImpl) keyguardSecLegacyUnlockAnimationControllerImpl2.notificationShadeWindowController).mHelper.resetForceVisibleForUnlockAnimation();
                final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl3 = keyguardSecLegacyUnlockAnimationControllerImpl;
                try {
                    keyguardSecLegacyUnlockAnimationControllerImpl3.delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl.onFinished.1.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((KeyguardSurfaceControllerImpl) keyguardSecLegacyUnlockAnimationControllerImpl3.keyguardSurfaceControllerLazy.get()).restoreKeyguardSurfaceByTransaction();
                        }
                    }, keyguardSecLegacyUnlockAnimationControllerImpl3.isPrimaryBouncerShowing ? 500L : keyguardSecLegacyUnlockAnimationControllerImpl3.animStartDelay);
                } catch (Exception unused) {
                    Log.d("KeyguardUnlock", "onStarted fail to restoreKeyguardSurfaceByTransaction");
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl4 = keyguardSecLegacyUnlockAnimationControllerImpl;
                keyguardSecLegacyUnlockAnimationControllerImpl4.animStartDelay = 0L;
                try {
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = keyguardSecLegacyUnlockAnimationControllerImpl4.surfaceBehindRemoteAnimationFinishedCallback;
                    if (iRemoteAnimationFinishedCallback != null) {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    }
                    Log.d("KeyguardUnlock", "IRemoteAnimationFinishedCallback#onAnimationFinished");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl5 = keyguardSecLegacyUnlockAnimationControllerImpl;
                keyguardSecLegacyUnlockAnimationControllerImpl5.surfaceBehindRemoteAnimationFinishedCallback = null;
                Log.d("KeyguardUnlock", "disablePanelDetectorAndRestoreKeyguard");
                keyguardSecLegacyUnlockAnimationControllerImpl5.delayableExecutor.executeDelayed(new KeyguardSecLegacyUnlockAnimationControllerImpl$disablePanelDetectorAndRestoreKeyguard$1(keyguardSecLegacyUnlockAnimationControllerImpl5), 500L);
            }
        });
    }
}
