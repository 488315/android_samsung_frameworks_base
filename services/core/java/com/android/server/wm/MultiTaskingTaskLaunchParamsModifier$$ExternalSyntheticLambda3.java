package com.android.server.wm;

import android.graphics.Rect;

import com.android.internal.util.function.TriPredicate;

public final /* synthetic */ class MultiTaskingTaskLaunchParamsModifier$$ExternalSyntheticLambda3
        implements TriPredicate {
    public final boolean test(Object obj, Object obj2, Object obj3) {
        ActivityRecord topNonFinishingActivity;
        Task task = (Task) obj;
        String str = (String) obj2;
        Rect rect = (Rect) obj3;
        if (task.getChildCount() <= 0) {
            return false;
        }
        return str == null
                || rect == null
                || (topNonFinishingActivity = task.getTopNonFinishingActivity(true, true)) == null
                || !topNonFinishingActivity.packageName.equals(str)
                || !task.getBounds().equals(rect);
    }
}
