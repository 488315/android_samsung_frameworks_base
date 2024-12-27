package com.android.server.wm;

import android.content.ComponentName;
import android.content.Intent;

import com.android.internal.util.function.QuintPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda36
        implements QuintPredicate {
    public final boolean test(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        int intValue = ((Integer) obj2).intValue();
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        Intent intent = (Intent) obj4;
        ComponentName componentName = (ComponentName) obj5;
        if (!activityRecord.canBeTopRunning() || activityRecord.mUserId != intValue) {
            return false;
        }
        if (booleanValue) {
            if (!activityRecord.intent.filterEquals(intent)) {
                return false;
            }
        } else if (!activityRecord.mActivityComponent.equals(componentName)) {
            return false;
        }
        return true;
    }
}
