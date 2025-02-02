package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.p001sa.IDMAInterface;

/* loaded from: classes.dex */
public final class SendLogTask implements AsyncTaskClient {
    private Configuration configuration;
    private IDMAInterface dmaInterface;
    private SimpleLog log;

    SendLogTask(IDMAInterface iDMAInterface, Configuration configuration, SimpleLog simpleLog) {
        this.log = simpleLog;
        this.dmaInterface = iDMAInterface;
        this.configuration = configuration;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        try {
            IDMAInterface iDMAInterface = this.dmaInterface;
            this.configuration.getClass();
            iDMAInterface.sendLog(this.configuration.getTrackingId(), this.log.getType().getAbbrev(), this.log.getTimestamp(), this.log.getData());
        } catch (Exception e) {
            Debug.LogException(e.getClass(), e);
        }
    }
}
