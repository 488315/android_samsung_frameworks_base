package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class WindowState$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WindowState$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                return (!activityRecord.inMultiWindowMode()
                                || activityRecord.inSplitScreenWindowingMode())
                        && activityRecord.mAppCompatController.mAppCompatLetterboxPolicy
                                        .getLetterboxDirection()
                                != 0;
            default:
                WindowState.DrawHandler drawHandler = (WindowState.DrawHandler) obj;
                return drawHandler.mIsEnteringPipFromSplit && drawHandler.mType == 1;
        }
    }
}
