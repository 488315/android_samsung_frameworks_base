package com.android.server.am;

import android.os.RemoteCallback;

import java.util.function.Consumer;

public final /* synthetic */ class ActivityManagerServiceExt$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityManagerServiceExt f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ActivityManagerServiceExt$$ExternalSyntheticLambda0(
            ActivityManagerServiceExt activityManagerServiceExt, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = activityManagerServiceExt;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityManagerServiceExt activityManagerServiceExt = this.f$0;
                ProcessRecord processRecord = (ProcessRecord) this.f$1;
                activityManagerServiceExt.getClass();
                ActivityManagerServiceExt.updateNeverKill(
                        (RemoteCallback) obj, true, processRecord.processName, processRecord.mPid);
                break;
            case 1:
                ActivityManagerServiceExt activityManagerServiceExt2 = this.f$0;
                ProcessRecord processRecord2 = (ProcessRecord) this.f$1;
                activityManagerServiceExt2.getClass();
                ActivityManagerServiceExt.updateNeverKill(
                        (RemoteCallback) obj,
                        true,
                        processRecord2.processName,
                        processRecord2.mPid);
                break;
            default:
                ActivityManagerServiceExt activityManagerServiceExt3 = this.f$0;
                String str = (String) this.f$1;
                ProcessRecord processRecord3 = (ProcessRecord) obj;
                activityManagerServiceExt3.getClass();
                if (processRecord3.mPkgList.containsKey(str)) {
                    processRecord3.mDedicated = true;
                    activityManagerServiceExt3.mLongLiveCallbacks.add(
                            new ActivityManagerServiceExt.LongLiveCallback(processRecord3, str));
                    activityManagerServiceExt3.mCb4Process.ifPresent(
                            new ActivityManagerServiceExt$$ExternalSyntheticLambda1(
                                    activityManagerServiceExt3, str, processRecord3, 1));
                    break;
                }
                break;
        }
    }
}
