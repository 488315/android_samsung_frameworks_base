package com.android.wm.shell.pip.phone;

import android.animation.AnimatorSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipMenuView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipMenuView f$0;

    public /* synthetic */ PipMenuView$$ExternalSyntheticLambda0(PipMenuView pipMenuView, int i) {
        this.$r8$classId = i;
        this.f$0 = pipMenuView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 0;
        int i2 = this.$r8$classId;
        PipMenuView pipMenuView = this.f$0;
        switch (i2) {
            case 0:
                pipMenuView.hideMenu$1();
                break;
            case 1:
                int i3 = PipMenuView.$r8$clinit;
                pipMenuView.hideMenu$1();
                pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(i));
                pipMenuView.mAllowTouches = true;
                break;
            default:
                AnimatorSet animatorSet = pipMenuView.mMenuContainerAnimator;
                if (animatorSet != null) {
                    animatorSet.setStartDelay(30L);
                    pipMenuView.setVisibility(0);
                    pipMenuView.mMenuContainerAnimator.start();
                    break;
                }
                break;
        }
    }
}
