package com.android.server.wm;

import android.util.ArraySet;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class Transition$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Transition$$ExternalSyntheticLambda11(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                Transition transition = (Transition) obj2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                transition.getClass();
                if (activityRecord.mVisible && activityRecord.isVisibleRequested()) {
                    if (transition.mConfigAtEndActivities == null) {
                        transition.mConfigAtEndActivities = new ArrayList();
                    }
                    if (!transition.mConfigAtEndActivities.contains(activityRecord)) {
                        transition.mConfigAtEndActivities.add(activityRecord);
                        int i2 = activityRecord.mPauseConfigurationDispatchCount + 1;
                        activityRecord.mPauseConfigurationDispatchCount = i2;
                        if (i2 == 1
                                && ProtoLogImpl_54989576.Cache
                                        .WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                                    2612201759169917322L,
                                    0,
                                    "Pausing configuration dispatch for  %s",
                                    String.valueOf(activityRecord));
                        }
                        transition.snapshotStartState(activityRecord);
                        ((Transition.ChangeInfo) transition.mChanges.get(activityRecord)).mFlags |=
                                64;
                        break;
                    }
                }
                break;
            case 1:
                ArraySet arraySet = (ArraySet) obj2;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                if (activityRecord2.isVisibleRequested()) {
                    arraySet.add(activityRecord2);
                    break;
                }
                break;
            default:
                ((Runnable) obj2).run();
                break;
        }
    }
}
