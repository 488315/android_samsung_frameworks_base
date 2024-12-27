package com.android.server.wm;

import android.content.Intent;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.policy.PermissionPolicyService;

import java.util.Objects;
import java.util.function.Predicate;

public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda12
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda12(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                boolean[] zArr = (boolean[]) obj2;
                ActivityRecord topPausingActivity = ((Task) obj).getTopPausingActivity();
                if (topPausingActivity != null
                        && !topPausingActivity.isState(
                                ActivityRecord.State.PAUSED,
                                ActivityRecord.State.STOPPED,
                                ActivityRecord.State.STOPPING,
                                ActivityRecord.State.FINISHING)) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_STATES,
                                3785779399471740019L,
                                0,
                                null,
                                String.valueOf(topPausingActivity),
                                String.valueOf(topPausingActivity.mState));
                    }
                    if (ProtoLogGroup.WM_DEBUG_STATES.isEnabled()) {
                        zArr[0] = false;
                        break;
                    }
                }
                break;
            case 1:
                boolean[] zArr2 = (boolean[]) obj2;
                ActivityRecord topResumedActivity = ((Task) obj).getTopResumedActivity();
                if (topResumedActivity != null) {
                    if (!topResumedActivity.nowVisible) {
                        break;
                    } else {
                        zArr2[0] = true;
                        break;
                    }
                }
                break;
            default:
                PermissionPolicyService.Internal internal = (PermissionPolicyService.Internal) obj2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.canBeTopRunning() && activityRecord.isVisibleRequested()) {
                    Intent intent = activityRecord.intent;
                    internal.getClass();
                    if (!Objects.equals(
                                    intent.getPackage(),
                                    PermissionPolicyService.this.mPackageManager
                                            .getPermissionControllerPackageName())
                            || (!Objects.equals(
                                            intent.getAction(),
                                            "android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER")
                                    && !Objects.equals(
                                            intent.getAction(),
                                            "android.content.pm.action.REQUEST_PERMISSIONS"))) {}
                }
                break;
        }
        return false;
    }
}
