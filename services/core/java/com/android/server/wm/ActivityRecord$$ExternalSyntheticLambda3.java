package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityRecord$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ActivityRecord$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((WindowState) obj).performShowLocked();
                break;
            case 1:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.mTaskOverlay) {
                    DisplayContent displayContent = activityRecord.mDisplayContent;
                    displayContent.prepareAppTransition(2, 0);
                    activityRecord.setVisibility(false);
                    displayContent.executeAppTransition();
                    break;
                }
                break;
            case 2:
                ((ActivityRecord) obj).recomputeConfiguration();
                break;
            case 3:
                ((WindowState) obj).onExitAnimationDone();
                break;
            case 4:
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                activityRecord2.mPendingOptions = null;
                activityRecord2.mPendingRemoteAnimation = null;
                activityRecord2.mPendingRemoteTransition = null;
                break;
            default:
                WindowState windowState = (WindowState) obj;
                WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
                if (windowStateAnimator.mDrawState == 4) {
                    windowStateAnimator.resetDrawState();
                    windowState.forceReportingResized();
                    break;
                }
                break;
        }
    }
}
