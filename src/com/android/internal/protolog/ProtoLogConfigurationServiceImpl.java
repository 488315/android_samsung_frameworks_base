package com.android.internal.protolog;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.util.Log;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import com.android.internal.protolog.IProtoLogConfigurationService;
import com.android.internal.protolog.ProtoLogDataSource;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class ProtoLogConfigurationServiceImpl extends IProtoLogConfigurationService.Stub implements ProtoLogConfigurationService {
    private static final String LOG_TAG = "ProtoLogConfigurationService";
    private final Map<IProtoLogClient, String> mClientConfigFiles;
    private final Map<String, Integer> mConfigFileCounts;
    private final ProtoLogDataSource mDataSource;
    private final Map<String, Set<IProtoLogClient>> mGroupToClients;
    private final Map<String, Boolean> mLogGroupToLogcatStatus;
    private final Set<String> mRegisteredGroups;
    private final Set<Integer> mRunningInstances;
    private final ViewerConfigFileTracer mViewerConfigFileTracer;

    @FunctionalInterface
    public interface ViewerConfigFileTracer {
        void trace(ProtoLogDataSource protoLogDataSource, String str);
    }

    public ProtoLogConfigurationServiceImpl() {
        this(ProtoLog.getSharedSingleInstanceDataSource(), new ViewerConfigFileTracer() { // from class: com.android.internal.protolog.ProtoLogConfigurationServiceImpl$$ExternalSyntheticLambda1
            @Override // com.android.internal.protolog.ProtoLogConfigurationServiceImpl.ViewerConfigFileTracer
            public final void trace(ProtoLogDataSource protoLogDataSource, String str) {
                ProtoLogConfigurationServiceImpl.dumpViewerConfig(protoLogDataSource, str);
            }
        });
    }

    public ProtoLogConfigurationServiceImpl(ProtoLogDataSource protoLogDataSource) {
        this(protoLogDataSource, new ViewerConfigFileTracer() { // from class: com.android.internal.protolog.ProtoLogConfigurationServiceImpl$$ExternalSyntheticLambda1
            @Override // com.android.internal.protolog.ProtoLogConfigurationServiceImpl.ViewerConfigFileTracer
            public final void trace(ProtoLogDataSource protoLogDataSource2, String str) {
                ProtoLogConfigurationServiceImpl.dumpViewerConfig(protoLogDataSource2, str);
            }
        });
    }

    public ProtoLogConfigurationServiceImpl(ViewerConfigFileTracer viewerConfigFileTracer) {
        this(ProtoLog.getSharedSingleInstanceDataSource(), viewerConfigFileTracer);
    }

    public ProtoLogConfigurationServiceImpl(ProtoLogDataSource protoLogDataSource, ViewerConfigFileTracer viewerConfigFileTracer) {
        this.mConfigFileCounts = new HashMap();
        this.mClientConfigFiles = new HashMap();
        this.mRegisteredGroups = new HashSet();
        this.mGroupToClients = new HashMap();
        this.mLogGroupToLogcatStatus = new TreeMap();
        this.mRunningInstances = new HashSet();
        this.mViewerConfigFileTracer = viewerConfigFileTracer;
        protoLogDataSource.registerOnStartCallback(new ProtoLogDataSource.Instance.TracingInstanceStartCallback() { // from class: com.android.internal.protolog.ProtoLogConfigurationServiceImpl$$ExternalSyntheticLambda3
            @Override // com.android.internal.protolog.ProtoLogDataSource.Instance.TracingInstanceStartCallback
            public final void run(int i, ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
                this.f$0.onTracingInstanceStart(i, protoLogConfig);
            }
        });
        protoLogDataSource.registerOnFlushCallback(new Runnable() { // from class: com.android.internal.protolog.ProtoLogConfigurationServiceImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onTracingInstanceFlush();
            }
        });
        protoLogDataSource.registerOnStopCallback(new ProtoLogDataSource.Instance.TracingInstanceStopCallback() { // from class: com.android.internal.protolog.ProtoLogConfigurationServiceImpl$$ExternalSyntheticLambda5
            @Override // com.android.internal.protolog.ProtoLogDataSource.Instance.TracingInstanceStopCallback
            public final void run(int i, ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
                this.f$0.onTracingInstanceStop(i, protoLogConfig);
            }
        });
        this.mDataSource = protoLogDataSource;
    }

    @Override // com.android.internal.protolog.IProtoLogConfigurationService
    public void registerClient(final IProtoLogClient iProtoLogClient, IProtoLogConfigurationService.RegisterClientArgs registerClientArgs) throws RemoteException {
        iProtoLogClient.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.internal.protolog.ProtoLogConfigurationServiceImpl$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                this.f$0.lambda$registerClient$0(iProtoLogClient);
            }
        }, 0);
        String str = registerClientArgs.viewerConfigFile;
        if (str != null) {
            registerViewerConfigFile(iProtoLogClient, str);
        }
        registerGroups(iProtoLogClient, registerClientArgs.groups, registerClientArgs.groupsDefaultLogcatStatus);
    }

    @Override // android.os.Binder
    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
        new ProtoLogCommandHandler(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    @Override // com.android.internal.protolog.ProtoLogConfigurationService
    public String[] getGroups() {
        return (String[]) this.mRegisteredGroups.toArray(new String[0]);
    }

    @Override // com.android.internal.protolog.ProtoLogConfigurationService
    public void enableProtoLogToLogcat(PrintWriter printWriter, String... strArr) {
        toggleProtoLogToLogcat(printWriter, true, strArr);
    }

    @Override // com.android.internal.protolog.ProtoLogConfigurationService
    public void disableProtoLogToLogcat(PrintWriter printWriter, String... strArr) {
        toggleProtoLogToLogcat(printWriter, false, strArr);
    }

    @Override // com.android.internal.protolog.ProtoLogConfigurationService
    public boolean isLoggingToLogcat(String str) {
        Boolean bool = this.mLogGroupToLogcatStatus.get(str);
        if (bool == null) {
            throw new RuntimeException("Trying to get logcat logging status of non-registered group " + str);
        }
        return bool.booleanValue();
    }

    private void registerViewerConfigFile(IProtoLogClient iProtoLogClient, String str) {
        this.mConfigFileCounts.put(str, Integer.valueOf(this.mConfigFileCounts.getOrDefault(str, 0).intValue() + 1));
        this.mClientConfigFiles.put(iProtoLogClient, str);
    }

    private void registerGroups(IProtoLogClient iProtoLogClient, String[] strArr, boolean[] zArr) throws RemoteException {
        if (strArr.length != zArr.length) {
            throw new RuntimeException("Expected groups and logcatStatuses to have the same length, but groups has length " + strArr.length + " and logcatStatuses has length " + zArr.length);
        }
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            boolean z = zArr[i];
            this.mRegisteredGroups.add(str);
            this.mGroupToClients.putIfAbsent(str, new HashSet());
            this.mGroupToClients.get(str).add(iProtoLogClient);
            if (!this.mLogGroupToLogcatStatus.containsKey(str)) {
                this.mLogGroupToLogcatStatus.put(str, Boolean.valueOf(z));
            }
            boolean zBooleanValue = this.mLogGroupToLogcatStatus.get(str).booleanValue();
            if (zBooleanValue != z) {
                iProtoLogClient.toggleLogcat(zBooleanValue, new String[]{str});
            }
        }
    }

    private void toggleProtoLogToLogcat(PrintWriter printWriter, boolean z, String[] strArr) {
        HashMap map = new HashMap();
        for (String str : strArr) {
            Set<IProtoLogClient> set = this.mGroupToClients.get(str);
            if (set == null) {
                String str2 = "Attempting to toggle log to logcat for group " + str + " with no registered clients. This is a no-op.";
                Log.w(LOG_TAG, str2);
                printWriter.println("WARNING: " + str2);
            } else {
                for (IProtoLogClient iProtoLogClient : set) {
                    map.putIfAbsent(iProtoLogClient, new HashSet());
                    ((Set) map.get(iProtoLogClient)).add(str);
                }
            }
        }
        for (IProtoLogClient iProtoLogClient2 : map.keySet()) {
            try {
                String[] strArr2 = (String[]) ((Set) map.get(iProtoLogClient2)).toArray(new String[0]);
                printWriter.println("Toggling logcat logging for client " + iProtoLogClient2.toString() + " to " + z + " for groups: [" + String.join(", ", strArr2) + NavigationBarInflaterView.SIZE_MOD_END);
                iProtoLogClient2.toggleLogcat(z, strArr2);
                printWriter.println("- Done");
            } catch (RemoteException e) {
                printWriter.println("- Failed");
                throw new RuntimeException("Failed to toggle logcat status for groups on client", e);
            }
        }
        for (String str3 : strArr) {
            this.mLogGroupToLogcatStatus.put(str3, Boolean.valueOf(z));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTracingInstanceStart(int i, ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
        this.mRunningInstances.add(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTracingInstanceFlush() {
        Iterator<String> it = this.mConfigFileCounts.keySet().iterator();
        while (it.hasNext()) {
            this.mViewerConfigFileTracer.trace(this.mDataSource, it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTracingInstanceStop(int i, ProtoLogDataSource.ProtoLogConfig protoLogConfig) {
        this.mRunningInstances.remove(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dumpViewerConfig(ProtoLogDataSource protoLogDataSource, final String str) {
        Utils.dumpViewerConfig(protoLogDataSource, new ViewerConfigInputStreamProvider() { // from class: com.android.internal.protolog.ProtoLogConfigurationServiceImpl$$ExternalSyntheticLambda2
            @Override // com.android.internal.protolog.ViewerConfigInputStreamProvider
            public final AutoClosableProtoInputStream getInputStream() {
                return ProtoLogConfigurationServiceImpl.lambda$dumpViewerConfig$1(str);
            }
        });
    }

    static /* synthetic */ AutoClosableProtoInputStream lambda$dumpViewerConfig$1(String str) {
        try {
            return new AutoClosableProtoInputStream(new FileInputStream(str));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to load viewer config file " + str, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onClientBinderDeath, reason: merged with bridge method [inline-methods] */
    public void lambda$registerClient$0(IProtoLogClient iProtoLogClient) {
        String str = this.mClientConfigFiles.get(iProtoLogClient);
        if (str != null) {
            int iIntValue = this.mConfigFileCounts.get(str).intValue() - 1;
            this.mConfigFileCounts.put(str, Integer.valueOf(iIntValue));
            if (iIntValue == 0) {
                this.mViewerConfigFileTracer.trace(this.mDataSource, str);
            }
        }
    }

    private static void writeViewerConfigGroup(ProtoInputStream protoInputStream, ProtoOutputStream protoOutputStream) throws IOException {
        long jStart = protoInputStream.start(2246267895810L);
        long jStart2 = protoOutputStream.start(2246267895810L);
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                protoOutputStream.write(1155346202625L, protoInputStream.readInt(1155346202625L));
            } else if (fieldNumber == 2) {
                protoOutputStream.write(1138166333442L, protoInputStream.readString(1138166333442L));
            } else if (fieldNumber == 3) {
                protoOutputStream.write(1138166333443L, protoInputStream.readString(1138166333443L));
            } else {
                throw new RuntimeException("Unexpected field id " + protoInputStream.getFieldNumber());
            }
        }
        protoInputStream.end(jStart);
        protoOutputStream.end(jStart2);
    }

    private static void writeViewerConfigMessage(ProtoInputStream protoInputStream, ProtoOutputStream protoOutputStream) throws IOException {
        long jStart = protoInputStream.start(2246267895809L);
        long jStart2 = protoOutputStream.start(2246267895809L);
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                protoOutputStream.write(1125281431553L, protoInputStream.readLong(1125281431553L));
            } else if (fieldNumber == 2) {
                protoOutputStream.write(1138166333442L, protoInputStream.readString(1138166333442L));
            } else if (fieldNumber == 3) {
                protoOutputStream.write(1159641169923L, protoInputStream.readInt(1159641169923L));
            } else if (fieldNumber == 4) {
                protoOutputStream.write(1155346202628L, protoInputStream.readInt(1155346202628L));
            } else if (fieldNumber == 5) {
                protoOutputStream.write(1138166333445L, protoInputStream.readString(1138166333445L));
            } else {
                throw new RuntimeException("Unexpected field id " + protoInputStream.getFieldNumber());
            }
        }
        protoInputStream.end(jStart);
        protoOutputStream.end(jStart2);
    }
}
