package com.android.systemui.keyguard;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardFastBioUnlockController$updateBrightnessRunnable$1 implements Runnable {
    public float adjustedBrightness;
    public float brightness;
    public int displayState;
    public boolean isAodBrightThanNormal;
    public final /* synthetic */ KeyguardFastBioUnlockController this$0;

    public KeyguardFastBioUnlockController$updateBrightnessRunnable$1(KeyguardFastBioUnlockController keyguardFastBioUnlockController) {
        this.this$0 = keyguardFastBioUnlockController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.this$0;
        boolean z = this.isAodBrightThanNormal;
        int i = this.displayState;
        float f = this.brightness;
        float f2 = this.adjustedBrightness;
        if (keyguardFastBioUnlockController.curIsAodBrighterThanNormal == z) {
            return;
        }
        keyguardFastBioUnlockController.curIsAodBrighterThanNormal = z;
        String str = "onBrightnessChanged isAodBrighterThanNormal=" + z + ", displayState=" + i;
        if (!(f == -1.0f)) {
            if (!(f2 == -1.0f)) {
                str = str + " brightness=" + f + " adjustedBrightness=" + f2;
            }
        }
        KeyguardFastBioUnlockController.logD(str);
    }
}
