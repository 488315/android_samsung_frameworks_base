package com.android.server.wm;

import android.app.ResultInfo;
import android.content.Intent;
import android.os.IBinder;

public final class ActivityResult extends ResultInfo {
    public final ActivityRecord mFrom;

    public ActivityResult(
            ActivityRecord activityRecord,
            String str,
            int i,
            int i2,
            Intent intent,
            IBinder iBinder) {
        super(str, i, i2, intent, iBinder);
        this.mFrom = activityRecord;
    }
}
