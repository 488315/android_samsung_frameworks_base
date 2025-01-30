package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.p051sa.IDMAInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SendLogTask implements AsyncTaskClient {
    public final Configuration configuration;
    public final IDMAInterface dmaInterface;
    public final SimpleLog log;

    public SendLogTask(IDMAInterface iDMAInterface, Configuration configuration, SimpleLog simpleLog) {
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
        SimpleLog simpleLog = this.log;
        Configuration configuration = this.configuration;
        try {
            IDMAInterface iDMAInterface = this.dmaInterface;
            configuration.getClass();
            ((IDMAInterface.Stub.Proxy) iDMAInterface).sendLog(0, configuration.trackingId, simpleLog.type.getAbbrev(), simpleLog.timestamp, simpleLog.data);
        } catch (Exception e) {
            Debug.LogException(e.getClass(), e);
        }
    }
}
