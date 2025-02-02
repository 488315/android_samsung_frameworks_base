package com.samsung.context.sdk.samsunganalytics.internal.sender.DLC;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;

/* loaded from: classes.dex */
public final class SendLogTask implements AsyncTaskClient {
    private DLCBinder binder;
    private Configuration configuration;
    private int result = -1;
    private SimpleLog simpleLog;

    public SendLogTask(DLCBinder dLCBinder, Configuration configuration, SimpleLog simpleLog) {
        this.binder = dLCBinder;
        this.configuration = configuration;
        this.simpleLog = simpleLog;
    }

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
        try {
            this.result = this.binder.getDlcService().requestSend(this.simpleLog.getType().getAbbrev(), this.configuration.getTrackingId().substring(0, 3), this.simpleLog.getTimestamp(), this.simpleLog.getId(), this.simpleLog.getData());
            Debug.LogENG("send to DLC : " + this.simpleLog.getData());
        } catch (Exception e) {
            Debug.LogException(SendLogTask.class, e);
        }
    }
}
