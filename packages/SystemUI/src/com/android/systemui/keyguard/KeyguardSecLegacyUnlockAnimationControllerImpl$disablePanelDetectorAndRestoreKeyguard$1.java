package com.android.systemui.keyguard;

import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class KeyguardSecLegacyUnlockAnimationControllerImpl$disablePanelDetectorAndRestoreKeyguard$1 implements Runnable {
    public final /* synthetic */ KeyguardSecLegacyUnlockAnimationControllerImpl this$0;

    public KeyguardSecLegacyUnlockAnimationControllerImpl$disablePanelDetectorAndRestoreKeyguard$1(KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl) {
        this.this$0 = keyguardSecLegacyUnlockAnimationControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
        int i = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
        keyguardSecLegacyUnlockAnimationControllerImpl.getClass();
        Log.i("KeyguardUnlock", "Disable panel detector");
        StandaloneCoroutine standaloneCoroutine = keyguardSecLegacyUnlockAnimationControllerImpl.shadeExpansionCollectorJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        ((KeyguardSurfaceControllerImpl) this.this$0.keyguardSurfaceControllerLazy.get()).restoreKeyguardSurfaceIfVisible();
    }
}
