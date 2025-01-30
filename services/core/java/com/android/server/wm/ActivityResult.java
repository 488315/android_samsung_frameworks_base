package com.android.server.wm;

import android.app.ResultInfo;
import android.content.Intent;

/* loaded from: classes3.dex */
public final class ActivityResult extends ResultInfo {
    public final ActivityRecord mFrom;

    public ActivityResult(ActivityRecord activityRecord, String str, int i, int i2, Intent intent) {
        super(str, i, i2, intent);
        this.mFrom = activityRecord;
    }
}
