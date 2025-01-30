package com.android.wm.shell.pip.tv;

import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TvPipMenuController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TvPipMenuController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ TvPipMenuController$$ExternalSyntheticLambda0(TvPipMenuController tvPipMenuController, boolean z) {
        this.f$0 = tvPipMenuController;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TvPipMenuController tvPipMenuController = this.f$0;
        boolean z = this.f$1;
        TvPipMenuView tvPipMenuView = tvPipMenuController.mPipMenuView;
        if (tvPipMenuView != null) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1855178708, 0, "%s: onPipTransitionFinished()", "TvPipMenuView");
            }
            ViewPropertyAnimator duration = tvPipMenuView.mPipBackground.animate().alpha(0.0f).setDuration(tvPipMenuView.mResizeAnimationDuration / 2);
            Interpolator interpolator = TvPipInterpolators.ENTER;
            duration.setInterpolator(interpolator).start();
            if (z) {
                tvPipMenuView.mEduTextDrawer.init();
            }
            tvPipMenuView.mButtonLayoutManager.setOrientation(tvPipMenuView.mCurrentPipBounds.height() > tvPipMenuView.mCurrentPipBounds.width() ? 1 : 0);
            if (tvPipMenuView.mCurrentMenuMode != 2 || tvPipMenuView.mActionButtonsRecyclerView.getAlpha() == 1.0f) {
                return;
            }
            tvPipMenuView.mActionButtonsRecyclerView.animate().alpha(1.0f).setInterpolator(interpolator).setDuration(tvPipMenuView.mResizeAnimationDuration / 2);
        }
    }
}
