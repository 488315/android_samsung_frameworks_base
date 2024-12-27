package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda13
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda13(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                WindowState windowState = (WindowState) obj;
                if (i2 != windowState.mSession.mPid
                        || !windowState.isVisibleNow()
                        || windowState.mAttrs.type == 2005) {}
                break;
            case 1:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.finishing || activityRecord.mUserId != i2) {}
                break;
            default:
                WindowState windowState2 = (WindowState) obj;
                if (windowState2.mSession.mPid != i2 || !windowState2.isVisible()) {}
                break;
        }
        return false;
    }
}
