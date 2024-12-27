package com.android.server.wm;

import android.view.WindowManager;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformController$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FreeformController f$0;

    public /* synthetic */ FreeformController$$ExternalSyntheticLambda0(
            FreeformController freeformController, int i) {
        this.$r8$classId = i;
        this.f$0 = freeformController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityRecord activityRecord;
        String str;
        ActivityRecord activityRecord2;
        int i = this.$r8$classId;
        FreeformController freeformController = this.f$0;
        WindowState windowState = (WindowState) obj;
        switch (i) {
            case 0:
                freeformController.releaseForceHidePolicyIfNeededLocked(windowState);
                break;
            default:
                freeformController.getClass();
                if ((windowState.isVisible()
                                || (windowState.isVisibleRequested()
                                        && (activityRecord2 = windowState.mActivityRecord) != null
                                        && activityRecord2.isVisibleRequested()))
                        && !windowState.inFreeformWindowingMode()) {
                    if (windowState.getTask() == null
                            || windowState.getTask().shouldBeVisible(null)) {
                        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
                        int i2 = layoutParams.samsungFlags;
                        int i3 = layoutParams.multiWindowFlags;
                        int i4 = 67108864 & i2;
                        if (i4 != 0
                                && (str = layoutParams.packageName) != null
                                && str.equals("com.samsung.android.app.smartcapture")) {
                            freeformController.mIsForceHideWithoutAnimation = true;
                        }
                        int i5 = i3 & 512;
                        if (i5 != 0) {
                            freeformController.mIsForceHideWithoutAnimation = true;
                        }
                        if (freeformController.mTmpForceHideFreeformRequester == null
                                && ((i3 & 256) != 0
                                        || i5 != 0
                                        || i4 != 0
                                        || ((activityRecord = windowState.mActivityRecord) != null
                                                && activityRecord.mRequestFreeformForceHiding))) {
                            freeformController.mTmpForceHideFreeformRequester = windowState;
                        }
                        if (freeformController.mTmpForceHideMinimizeRequester == null
                                && (33554432 & i2) != 0) {
                            freeformController.mTmpForceHideMinimizeRequester = windowState;
                            break;
                        }
                    }
                }
                break;
        }
    }
}
