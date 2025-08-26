package com.android.systemui.recordissue;

import android.util.SparseArray;
import com.android.systemui.settings.UserTracker;

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
