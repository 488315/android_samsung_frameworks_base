package com.android.server.wm;

import com.android.server.wm.DeviceStateController;
import com.android.server.wm.DisplayRotation;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatDeviceStateQuery {
    public final ActivityRecord mActivityRecord;

    public AppCompatDeviceStateQuery(ActivityRecord activityRecord) {
        this.mActivityRecord = activityRecord;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0030, code lost:
    
        if (r4 == ((android.util.ArraySet) r3.mTabletopRotations).contains(java.lang.Integer.valueOf(com.android.server.wm.DisplayRotation.this.mRotation))) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isDisplayFullScreenAndInPosture(boolean r4) {
        /*
            r3 = this;
            com.android.server.wm.ActivityRecord r3 = r3.mActivityRecord
            com.android.server.wm.Task r0 = r3.task
            com.android.server.wm.DisplayContent r3 = r3.mDisplayContent
            if (r3 == 0) goto L3a
            if (r0 == 0) goto L3a
            boolean r1 = r3.inTransition()
            if (r1 != 0) goto L3a
            com.android.server.wm.DisplayRotation r3 = r3.mDisplayRotation
            com.android.server.wm.DeviceStateController$DeviceState r1 = com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED
            com.android.server.wm.DisplayRotation$FoldController r3 = r3.mFoldController
            if (r3 != 0) goto L19
            goto L3a
        L19:
            com.android.server.wm.DeviceStateController$DeviceState r2 = r3.mDeviceState
            if (r1 == r2) goto L1e
            goto L3a
        L1e:
            if (r2 != r1) goto L32
            java.util.Set r1 = r3.mTabletopRotations
            com.android.server.wm.DisplayRotation r3 = com.android.server.wm.DisplayRotation.this
            int r3 = r3.mRotation
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            android.util.ArraySet r1 = (android.util.ArraySet) r1
            boolean r3 = r1.contains(r3)
            if (r4 != r3) goto L3a
        L32:
            int r3 = r0.getWindowingMode()
            r4 = 1
            if (r3 != r4) goto L3a
            goto L3b
        L3a:
            r4 = 0
        L3b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.AppCompatDeviceStateQuery.isDisplayFullScreenAndInPosture(boolean):boolean");
    }

    public final boolean isDisplayFullScreenAndSeparatingHinge() {
        DisplayRotation.FoldController foldController;
        DeviceStateController.DeviceState deviceState;
        ActivityRecord activityRecord = this.mActivityRecord;
        Task task = activityRecord.task;
        DisplayContent displayContent = activityRecord.mDisplayContent;
        return (displayContent == null || task == null || (foldController = displayContent.mDisplayRotation.mFoldController) == null || ((deviceState = foldController.mDeviceState) != DeviceStateController.DeviceState.HALF_FOLDED && (deviceState != DeviceStateController.DeviceState.OPEN || !foldController.mIsDisplayAlwaysSeparatingHinge)) || task.getWindowingMode() != 1) ? false : true;
    }
}
