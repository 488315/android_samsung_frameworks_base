package com.android.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardPatternView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardPatternView f$0;

    public /* synthetic */ KeyguardPatternView$$ExternalSyntheticLambda1(KeyguardPatternView keyguardPatternView, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardPatternView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardPatternView keyguardPatternView = this.f$0;
        switch (i) {
            case 0:
                int i2 = KeyguardPatternView.$r8$clinit;
                keyguardPatternView.setAlpha(1.0f);
                keyguardPatternView.mAppearAnimationUtils.startAnimation2d(keyguardPatternView.mLockPatternView.getCellStates(), new KeyguardPatternView$$ExternalSyntheticLambda1(keyguardPatternView, 1), keyguardPatternView);
                break;
            default:
                keyguardPatternView.mLockPatternView.invalidate();
                break;
        }
    }
}
