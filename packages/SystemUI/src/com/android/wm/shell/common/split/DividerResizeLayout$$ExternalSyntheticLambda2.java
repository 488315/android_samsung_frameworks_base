package com.android.wm.shell.common.split;

import com.android.wm.shell.common.split.DividerResizeLayout;

/* loaded from: classes3.dex */
public final /* synthetic */ class DividerResizeLayout$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ DividerResizeLayout f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DividerResizeLayout$$ExternalSyntheticLambda2(DividerResizeLayout dividerResizeLayout, int i, int i2) {
        this.f$0 = dividerResizeLayout;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DividerResizeLayout dividerResizeLayout = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        for (int size = dividerResizeLayout.mResizeTargets.size() - 1; size >= 0; size--) {
            DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) dividerResizeLayout.mResizeTargets.valueAt(size);
            if (dividerResizeTarget != null) {
                dividerResizeTarget.calculateBoundsForPosition(i, dividerResizeTarget.mTmpBounds);
                if (dividerResizeTarget.mSplitDismissSide != i2) {
                    dividerResizeTarget.mSplitDismissSide = i2;
                }
                dividerResizeTarget.startOutlineInsetsAnimation(i2 == 0);
                dividerResizeTarget.startBoundsAnimation(dividerResizeTarget.mTmpBounds, false, 300L);
            }
        }
    }
}
