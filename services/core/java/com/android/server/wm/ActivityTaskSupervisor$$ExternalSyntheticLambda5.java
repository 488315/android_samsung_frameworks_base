package com.android.server.wm;

import android.app.ActivityManagerInternal;

import java.util.ArrayList;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskSupervisor$$ExternalSyntheticLambda5
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) obj;
        switch (this.$r8$classId) {
            case 0:
                activityManagerInternal.setLongLiveProcess(((Integer) obj2).intValue());
                break;
            default:
                activityManagerInternal.killProcessesForRemovedTask((ArrayList) obj2);
                break;
        }
    }
}
