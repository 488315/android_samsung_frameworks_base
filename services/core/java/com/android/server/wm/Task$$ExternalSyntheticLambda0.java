package com.android.server.wm;

import android.view.WindowManager;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class Task$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Task$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i;
        switch (this.$r8$classId) {
            case 0:
                return !((ActivityRecord) obj).finishing;
            case 1:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                return !activityRecord.mIsExiting
                        && activityRecord.mClientVisible
                        && activityRecord.mVisible;
            case 2:
                return ((WindowState) obj).mAttrs.isFullscreen();
            case 3:
                return ((ActivityRecord) obj).mVisible;
            case 4:
                WindowState windowState = (WindowState) obj;
                return !windowState.mAnimatingExit
                        && ((i = windowState.mAttrs.type) == 1 || i == 2 || i == 4);
            case 5:
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                return activityRecord2.mPopOverState.mIsActivated && !activityRecord2.finishing;
            case 6:
                return ((WindowState) obj).mAttrs.type == 3;
            case 7:
                WindowManager.LayoutParams layoutParams = ((WindowState) obj).mAttrs;
                return layoutParams.type == 1 && layoutParams.isFullscreen();
            case 8:
                ActivityRecord activityRecord3 = (ActivityRecord) obj;
                return activityRecord3.mStartingData != null && activityRecord3.showToCurrentUser();
            case 9:
                return ((TaskFragment) obj).isOrganizedTaskFragment();
            case 10:
                return ((WindowState) obj).mAttrs.type == 1;
            default:
                ActivityRecord activityRecord4 = (ActivityRecord) obj;
                return activityRecord4.mHandleExitSplashScreen
                        && activityRecord4.mTransferringSplashScreenState == 1;
        }
    }
}
