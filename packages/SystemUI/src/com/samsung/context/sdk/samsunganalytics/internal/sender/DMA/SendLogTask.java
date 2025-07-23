package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import com.sec.android.diagmonagent.sa.IDMAInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SendLogTask implements AsyncTaskClient {
    public final Configuration configuration;
    public final IDMAInterface dmaInterface;
    public final SimpleLog log;

    public SendLogTask(IDMAInterface iDMAInterface, Configuration configuration, SimpleLog simpleLog) {
        this.log = simpleLog;
        this.dmaInterface = iDMAInterface;
        this.configuration = configuration;
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final int onFinish() {
        return 0;
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final void run() {
        SimpleLog simpleLog = this.log;
        Configuration configuration = this.configuration;
        try {
            IDMAInterface iDMAInterface = this.dmaInterface;
            configuration.getClass();
            ((IDMAInterface.Stub.Proxy) iDMAInterface).sendLog(configuration.trackingId, simpleLog.type.getAbbrev(), simpleLog.data, simpleLog.timestamp);
        } catch (Exception e) {
            Debug.logwingW("failed to send log" + e.getMessage());
        }
    }
}
