package com.android.server.wm;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ ActivityTaskManagerService f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ ActivityRecord f$2;

    public /* synthetic */ ActivityTaskManagerService$$ExternalSyntheticLambda16(ActivityTaskManagerService activityTaskManagerService, boolean z, ActivityRecord activityRecord) {
        this.f$0 = activityTaskManagerService;
        this.f$1 = z;
        this.f$2 = activityRecord;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ActivityTaskManagerService activityTaskManagerService = this.f$0;
        boolean z = this.f$1;
        ActivityRecord activityRecord = this.f$2;
        if (activityTaskManagerService.mUpdateLock.isHeld() != z) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_IMMERSIVE_enabled[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_IMMERSIVE, 6075150529915862250L, 0, null, String.valueOf(z), String.valueOf(activityRecord));
            }
            if (z) {
                activityTaskManagerService.mUpdateLock.acquire();
            } else {
                activityTaskManagerService.mUpdateLock.release();
            }
        }
    }
}
