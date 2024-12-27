package com.android.server.wm;

import android.util.ArraySet;

import java.util.function.Consumer;

public final /* synthetic */ class ScreenRecordingCallbackController$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ ScreenRecordingCallbackController f$0;
    public final /* synthetic */ ArraySet f$1;

    public /* synthetic */ ScreenRecordingCallbackController$$ExternalSyntheticLambda3(
            ScreenRecordingCallbackController screenRecordingCallbackController,
            ArraySet arraySet) {
        this.f$0 = screenRecordingCallbackController;
        this.f$1 = arraySet;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ScreenRecordingCallbackController screenRecordingCallbackController = this.f$0;
        ArraySet arraySet = this.f$1;
        ActivityRecord activityRecord = (ActivityRecord) obj;
        screenRecordingCallbackController.getClass();
        if (activityRecord.isVisibleRequested()
                && screenRecordingCallbackController.mLastInvokedStateByUid.containsKey(
                        Integer.valueOf(activityRecord.getUid()))) {
            arraySet.add(Integer.valueOf(activityRecord.getUid()));
        }
    }
}
