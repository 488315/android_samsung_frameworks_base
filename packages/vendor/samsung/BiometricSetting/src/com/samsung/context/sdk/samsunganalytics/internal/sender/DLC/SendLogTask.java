package com.samsung.context.sdk.samsunganalytics.internal.sender.DLC;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.spp.push.dlc.api.IDlcService;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class SendLogTask implements AsyncTaskClient {
    public DLCBinder binder;
    public Configuration configuration;
    public int result;
    public SimpleLog simpleLog;

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        if (this.result == 0) {
            Debug.LogD("DLC Sender", "send result success : " + this.result);
            return 1;
        }
        Debug.LogD("DLC Sender", "send result fail : " + this.result);
        return -7;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        SimpleLog simpleLog = this.simpleLog;
        try {
            this.result =
                    ((IDlcService.Stub.Proxy) this.binder.dlcService)
                            .requestSend(
                                    simpleLog.type.getAbbrev(),
                                    this.configuration.trackingId.substring(0, 3),
                                    simpleLog.timestamp,
                                    simpleLog._id,
                                    simpleLog.data);
            Debug.LogENG("send to DLC : " + simpleLog.data);
        } catch (Exception e) {
            Debug.LogException(SendLogTask.class, e);
        }
    }
}
