package com.android.server.wm;

import com.android.internal.util.function.QuintPredicate;
import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PersonaActivityHelper$$ExternalSyntheticLambda0 implements QuintPredicate {
    public final boolean test(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        ActivityRecord activityRecord;
        Task task = (Task) obj;
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        int intValue = ((Integer) obj3).intValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        int intValue2 = ((Integer) obj5).intValue();
        return (task == null || (activityRecord = task.topRunningActivityLocked()) == null || ((!booleanValue || !SemPersonaManager.isKnoxId(activityRecord.mUserId)) && activityRecord.mUserId != intValue) || (((!booleanValue2 || !activityRecord.nowVisible) && (booleanValue2 || !activityRecord.mVisible)) || ((intValue2 == -1 || intValue2 != activityRecord.getWindowingMode()) && intValue2 != -1))) ? false : true;
    }
}
