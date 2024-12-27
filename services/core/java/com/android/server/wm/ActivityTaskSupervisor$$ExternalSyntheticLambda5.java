package com.android.server.wm;

import android.app.ActivityManagerInternal;

import java.util.ArrayList;
import java.util.function.BiConsumer;

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
