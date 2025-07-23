package com.android.systemui.recordissue;

import android.util.SparseArray;
import com.android.systemui.settings.UserTracker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenRecordingStartTimeStore {
    public final SparseArray userIdToScreenRecordingStartTime = new SparseArray();
    public final UserTracker userTracker;

    public ScreenRecordingStartTimeStore(UserTracker userTracker) {
        this.userTracker = userTracker;
    }

    public static /* synthetic */ void getUserIdToScreenRecordingStartTime$annotations() {
    }
}
