package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
