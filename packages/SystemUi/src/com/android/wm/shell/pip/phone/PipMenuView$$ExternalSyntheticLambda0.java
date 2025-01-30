package com.android.wm.shell.pip.phone;

import android.animation.AnimatorSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        switch (this.$r8$classId) {
            case 0:
                this.f$0.hideMenu$1();
                break;
            case 1:
                PipMenuView pipMenuView = this.f$0;
                AnimatorSet animatorSet = pipMenuView.mMenuContainerAnimator;
                if (animatorSet != null) {
                    animatorSet.setStartDelay(30L);
                    pipMenuView.setVisibility(0);
                    pipMenuView.mMenuContainerAnimator.start();
                    break;
                }
                break;
            default:
                PipMenuView pipMenuView2 = this.f$0;
                pipMenuView2.hideMenu$1();
                pipMenuView2.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(i));
                pipMenuView2.mAllowTouches = true;
                break;
        }
    }
}
