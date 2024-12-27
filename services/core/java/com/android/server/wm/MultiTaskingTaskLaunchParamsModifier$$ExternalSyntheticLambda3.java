package com.android.server.wm;

import android.graphics.Rect;

import com.android.internal.util.function.TriPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
