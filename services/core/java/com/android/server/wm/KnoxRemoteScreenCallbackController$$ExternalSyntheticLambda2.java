package com.android.server.wm;

import android.util.ArraySet;

import java.util.function.Consumer;

public final /* synthetic */ class KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ KnoxRemoteScreenCallbackController f$0;
    public final /* synthetic */ ArraySet f$1;

    public /* synthetic */ KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda2(
            KnoxRemoteScreenCallbackController knoxRemoteScreenCallbackController,
            ArraySet arraySet) {
        this.f$0 = knoxRemoteScreenCallbackController;
        this.f$1 = arraySet;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        KnoxRemoteScreenCallbackController knoxRemoteScreenCallbackController = this.f$0;
        ArraySet arraySet = this.f$1;
        ActivityRecord activityRecord = (ActivityRecord) obj;
        knoxRemoteScreenCallbackController.getClass();
        if (activityRecord.isVisibleRequested()
                && knoxRemoteScreenCallbackController.mLastInvokedStateByUid.containsKey(
                        Integer.valueOf(activityRecord.getUid()))) {
            arraySet.add(Integer.valueOf(activityRecord.getUid()));
        }
    }
}
