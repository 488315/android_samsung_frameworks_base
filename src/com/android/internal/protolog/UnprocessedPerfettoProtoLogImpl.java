package com.android.internal.protolog;

import android.os.ServiceManager;
import com.android.internal.protolog.IProtoLogConfigurationService;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;

/* loaded from: classes3.dex */
public class UnprocessedPerfettoProtoLogImpl extends PerfettoProtoLogImpl {
    static /* synthetic */ void lambda$new$0(IProtoLog iProtoLog) {
    }

    @Override // com.android.internal.protolog.PerfettoProtoLogImpl
    void dumpViewerConfig() {
    }

    public UnprocessedPerfettoProtoLogImpl(ProtoLogDataSource protoLogDataSource, IProtoLogGroup[] iProtoLogGroupArr) throws ServiceManager.ServiceNotFoundException {
        super(protoLogDataSource, new ProtoLogCacheUpdater() { // from class: com.android.internal.protolog.UnprocessedPerfettoProtoLogImpl$$ExternalSyntheticLambda0
            @Override // com.android.internal.protolog.ProtoLogCacheUpdater
            public final void update(IProtoLog iProtoLog) {
                UnprocessedPerfettoProtoLogImpl.lambda$new$0(iProtoLog);
            }
        }, iProtoLogGroupArr);
        readyToLogToLogcat();
    }

    @Override // com.android.internal.protolog.PerfettoProtoLogImpl
    protected IProtoLogConfigurationService.RegisterClientArgs createConfigurationServiceRegisterClientArgs() {
        return new IProtoLogConfigurationService.RegisterClientArgs();
    }

    @Override // com.android.internal.protolog.PerfettoProtoLogImpl
    String getLogcatMessageString(PerfettoProtoLogImpl.Message message) {
        String message2 = message.getMessage();
        if (message2 != null) {
            return message2;
        }
        throw new RuntimeException("Failed to decode message for logcat. Message not available without ViewerConfig to decode the hash.");
    }
}
