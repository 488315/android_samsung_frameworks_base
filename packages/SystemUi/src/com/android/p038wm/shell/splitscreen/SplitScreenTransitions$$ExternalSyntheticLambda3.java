package com.android.p038wm.shell.splitscreen;

import android.animation.ValueAnimator;
import android.util.Log;
import com.android.p038wm.shell.common.split.DividerRoundedCorner;
import com.android.p038wm.shell.common.split.DividerView;
import com.android.p038wm.shell.common.split.SplitLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DividerView dividerView;
        DividerView dividerView2;
        DividerRoundedCorner dividerRoundedCorner = null;
        switch (this.$r8$classId) {
            case 0:
                ((ValueAnimator) this.f$0).start();
                break;
            case 1:
                SplitLayout splitLayout = SplitScreenTransitions.this.mStageCoordinator.mSplitLayout;
                if (splitLayout != null && (dividerView2 = splitLayout.mSplitWindowManager.mDividerView) != null) {
                    dividerRoundedCorner = dividerView2.mDividerRoundedCorner;
                }
                if (dividerRoundedCorner != null) {
                    Log.d("SplitScreenTransitions", "prepareRadiusAnimation: for " + dividerRoundedCorner);
                    dividerRoundedCorner.mNeedRadiusAnim = true;
                    break;
                }
                break;
            default:
                SplitLayout splitLayout2 = SplitScreenTransitions.this.mStageCoordinator.mSplitLayout;
                if (splitLayout2 != null && (dividerView = splitLayout2.mSplitWindowManager.mDividerView) != null) {
                    dividerRoundedCorner = dividerView.mDividerRoundedCorner;
                }
                if (dividerRoundedCorner != null && dividerRoundedCorner.isAttachedToWindow()) {
                    Log.d("SplitScreenTransitions", "startRadiusAnimation: for " + dividerRoundedCorner);
                    dividerRoundedCorner.startRadiusAnimation();
                    break;
                } else {
                    Log.d("SplitScreenTransitions", "startRadiusAnimation failed : corner=" + dividerRoundedCorner);
                    break;
                }
                break;
        }
    }
}
