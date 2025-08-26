package com.android.wm.shell.splitscreen;

import android.util.Log;
import com.android.wm.shell.common.split.DividerRoundedCorner;
import com.android.wm.shell.common.split.DividerView;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.common.split.SplitWindowManager;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenTransitions.TransitSession f$0;

    public /* synthetic */ SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0(SplitScreenTransitions.TransitSession transitSession, int i) {
        this.$r8$classId = i;
        this.f$0 = transitSession;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SplitWindowManager splitWindowManager;
        DividerView dividerView;
        SplitWindowManager splitWindowManager2;
        DividerView dividerView2;
        int i = this.$r8$classId;
        SplitScreenTransitions.TransitSession transitSession = this.f$0;
        switch (i) {
            case 0:
                SplitLayout splitLayout = SplitScreenTransitions.this.mStageCoordinator.mSplitLayout;
                DividerRoundedCorner dividerRoundedCorner = null;
                if (splitLayout != null && (splitWindowManager = splitLayout.mSplitWindowManager) != null && (dividerView = splitWindowManager.mDividerView) != null) {
                    dividerRoundedCorner = dividerView.mCorners;
                }
                if (dividerRoundedCorner != null) {
                    Log.d("SplitScreenTransitions", "prepareRadiusAnimation: for " + dividerRoundedCorner);
                    dividerRoundedCorner.mNeedRadiusAnim = true;
                    break;
                }
                break;
            default:
                SplitLayout splitLayout2 = SplitScreenTransitions.this.mStageCoordinator.mSplitLayout;
                DividerRoundedCorner dividerRoundedCorner2 = null;
                if (splitLayout2 != null && (splitWindowManager2 = splitLayout2.mSplitWindowManager) != null && (dividerView2 = splitWindowManager2.mDividerView) != null) {
                    dividerRoundedCorner2 = dividerView2.mCorners;
                }
                if (dividerRoundedCorner2 != null && dividerRoundedCorner2.isAttachedToWindow()) {
                    Log.d("SplitScreenTransitions", "startRadiusAnimation: for " + dividerRoundedCorner2);
                    dividerRoundedCorner2.startRadiusAnimation();
                    break;
                }
                break;
        }
    }
}
