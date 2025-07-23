package com.android.internal.protolog;

import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.protolog.IProtoLogConfigurationService;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.protolog.common.ILogger;
import com.android.internal.protolog.common.IProtoLogGroup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ProcessedPerfettoProtoLogImpl extends PerfettoProtoLogImpl {
    private static final String LOG_TAG = "PerfettoProtoLogImpl";
    private final String mViewerConfigFilePath;

    @Deprecated
    private final ViewerConfigInputStreamProvider mViewerConfigInputStreamProvider;
    private final ProtoLogViewerConfigReader mViewerConfigReader;

    public ProcessedPerfettoProtoLogImpl(ProtoLogDataSource protoLogDataSource, final String str, ProtoLogCacheUpdater protoLogCacheUpdater, IProtoLogGroup[] iProtoLogGroupArr) throws ServiceManager.ServiceNotFoundException {
        this(protoLogDataSource, str, new ViewerConfigInputStreamProvider() { // from class: com.android.internal.protolog.ProcessedPerfettoProtoLogImpl.1
            @Override // com.android.internal.protolog.ViewerConfigInputStreamProvider
            public AutoClosableProtoInputStream getInputStream() {
                try {
                    return new AutoClosableProtoInputStream(new FileInputStream(str));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Failed to load viewer config file " + str, e);
                }
            }
        }, protoLogCacheUpdater, iProtoLogGroupArr);
    }

    public ProcessedPerfettoProtoLogImpl(ProtoLogDataSource protoLogDataSource, String str, ViewerConfigInputStreamProvider viewerConfigInputStreamProvider, ProtoLogCacheUpdater protoLogCacheUpdater, IProtoLogGroup[] iProtoLogGroupArr) throws ServiceManager.ServiceNotFoundException {
        super(protoLogDataSource, protoLogCacheUpdater, iProtoLogGroupArr);
        this.mViewerConfigFilePath = str;
        this.mViewerConfigInputStreamProvider = viewerConfigInputStreamProvider;
        this.mViewerConfigReader = new ProtoLogViewerConfigReader(viewerConfigInputStreamProvider);
        loadLogcatGroupsViewerConfig(iProtoLogGroupArr);
    }

    public ProcessedPerfettoProtoLogImpl(ProtoLogDataSource protoLogDataSource, String str, ViewerConfigInputStreamProvider viewerConfigInputStreamProvider, ProtoLogViewerConfigReader protoLogViewerConfigReader, ProtoLogCacheUpdater protoLogCacheUpdater, IProtoLogGroup[] iProtoLogGroupArr, IProtoLogConfigurationService iProtoLogConfigurationService) throws ServiceManager.ServiceNotFoundException {
        super(protoLogDataSource, protoLogCacheUpdater, iProtoLogGroupArr, iProtoLogConfigurationService);
        this.mViewerConfigFilePath = str;
        this.mViewerConfigInputStreamProvider = viewerConfigInputStreamProvider;
        this.mViewerConfigReader = protoLogViewerConfigReader;
        loadLogcatGroupsViewerConfig(iProtoLogGroupArr);
    }

    @Override // com.android.internal.protolog.PerfettoProtoLogImpl
    protected IProtoLogConfigurationService.RegisterClientArgs createConfigurationServiceRegisterClientArgs() {
        IProtoLogConfigurationService.RegisterClientArgs registerClientArgs = new IProtoLogConfigurationService.RegisterClientArgs();
        registerClientArgs.viewerConfigFile = this.mViewerConfigFilePath;
        return registerClientArgs;
    }

    @Override // com.android.internal.protolog.PerfettoProtoLogImpl, com.android.internal.protolog.common.IProtoLog
    public int startLoggingToLogcat(String[] strArr, ILogger iLogger) {
        if (!validateGroups(iLogger, strArr)) {
            return -1;
        }
        this.mViewerConfigReader.loadViewerConfig(strArr, iLogger);
        return super.startLoggingToLogcat(strArr, iLogger);
    }

    @Override // com.android.internal.protolog.PerfettoProtoLogImpl, com.android.internal.protolog.common.IProtoLog
    public int stopLoggingToLogcat(String[] strArr, ILogger iLogger) {
        if (!validateGroups(iLogger, strArr)) {
            return -1;
        }
        int stopLoggingToLogcat = super.stopLoggingToLogcat(strArr, iLogger);
        if (stopLoggingToLogcat != 0) {
            throw new RuntimeException("Failed to stop logging to logcat");
        }
        this.mViewerConfigReader.unloadViewerConfig(strArr, iLogger);
        return stopLoggingToLogcat;
    }

    @Override // com.android.internal.protolog.PerfettoProtoLogImpl
    @Deprecated
    void dumpViewerConfig() {
        Log.d(LOG_TAG, "Dumping viewer config to trace from " + this.mViewerConfigFilePath);
        Utils.dumpViewerConfig(this.mDataSource, this.mViewerConfigInputStreamProvider);
        Log.d(LOG_TAG, "Successfully dumped viewer config to trace from " + this.mViewerConfigFilePath);
    }

    @Override // com.android.internal.protolog.PerfettoProtoLogImpl
    String getLogcatMessageString(PerfettoProtoLogImpl.Message message) {
        String message2 = message.getMessage(this.mViewerConfigReader);
        if (message2 != null) {
            return message2;
        }
        throw new RuntimeException(getReasonForFailureToGetMessageString(message));
    }

    private String getReasonForFailureToGetMessageString(PerfettoProtoLogImpl.Message message) {
        if (message.getMessageHash() == null) {
            return "Trying to get message from null message hash";
        }
        try {
            if (this.mViewerConfigReader.messageHashIsAvailableInFile(message.getMessageHash().longValue())) {
                return "Failed to decode message for logcat logging. Message hash (" + message.getMessageHash() + ") is not available in viewerConfig file (" + this.mViewerConfigFilePath + "). This might be due to the viewer config file and the executing code being out of sync.";
            }
            return "Failed to decode message for logcat. Message hash (" + message.getMessageHash() + ") was available in the viewerConfig file (" + this.mViewerConfigFilePath + ") but wasn't loaded into memory from file before decoding! This is likely a bug.";
        } catch (IOException unused) {
            return "Failed to get string message to log but could not identify the root cause due to an IO error in reading the viewer config file.";
        }
    }

    private void loadLogcatGroupsViewerConfig(IProtoLogGroup[] iProtoLogGroupArr) {
        final ArrayList arrayList = new ArrayList();
        for (IProtoLogGroup iProtoLogGroup : iProtoLogGroupArr) {
            if (iProtoLogGroup.isLogToLogcat()) {
                arrayList.add(iProtoLogGroup.name());
            }
        }
        this.mBackgroundLoggingService.execute(new Runnable() { // from class: com.android.internal.protolog.ProcessedPerfettoProtoLogImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProcessedPerfettoProtoLogImpl.this.lambda$loadLogcatGroupsViewerConfig$0(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadLogcatGroupsViewerConfig$0(ArrayList arrayList) {
        this.mViewerConfigReader.loadViewerConfig((String[]) arrayList.toArray(new String[0]));
        readyToLogToLogcat();
    }
}
