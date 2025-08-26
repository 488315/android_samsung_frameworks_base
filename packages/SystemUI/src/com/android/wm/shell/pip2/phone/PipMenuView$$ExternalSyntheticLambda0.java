package com.android.wm.shell.pip2.phone;

import android.animation.AnimatorSet;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipMenuView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipMenuView$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                PipMenuView pipMenuView = (PipMenuView) obj;
                pipMenuView.hideMenu(null, true, pipMenuView.mDidLastShowMenuResize, 1);
                break;
            case 1:
                PipMenuView pipMenuView2 = (PipMenuView) obj;
                AnimatorSet animatorSet = pipMenuView2.mMenuContainerAnimator;
                if (animatorSet != null) {
                    animatorSet.setStartDelay(30L);
                    pipMenuView2.setVisibility(0);
                    pipMenuView2.mMenuContainerAnimator.start();
                    break;
                }
                break;
            case 2:
                PipMenuView pipMenuView3 = (PipMenuView) obj;
                int i2 = PipMenuView.$r8$clinit;
                pipMenuView3.hideMenu(null, true, pipMenuView3.mDidLastShowMenuResize, 1);
                pipMenuView3.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(0));
                pipMenuView3.mAllowTouches = true;
                break;
            default:
                ((PhonePipMenuController) obj).mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                break;
        }
    }
}
