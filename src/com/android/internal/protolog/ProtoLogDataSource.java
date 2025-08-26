package com.android.internal.protolog;

import android.tracing.perfetto.CreateIncrementalStateArgs;
import android.tracing.perfetto.CreateTlsStateArgs;
import android.tracing.perfetto.DataSource;
import android.tracing.perfetto.DataSourceInstance;
import android.tracing.perfetto.FlushCallbackArguments;
import android.tracing.perfetto.StartCallbackArguments;
import android.tracing.perfetto.StopCallbackArguments;
import android.util.proto.ProtoInputStream;
import android.util.proto.WireTypeMismatchException;
import com.android.internal.protolog.ProtoLogDataSource;
import com.android.internal.protolog.common.LogLevel;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class ProtoLogDataSource extends DataSource<Instance, TlsState, IncrementalState> {
    private static final String DATASOURCE_NAME = "android.protolog";
    private final Set<Runnable> mOnFlushCallbacks;
    private final Set<Instance.TracingInstanceStartCallback> mOnStartCallbacks;
    private final Set<Instance.TracingInstanceStopCallback> mOnStopCallbacks;
    private final Map<Integer, ProtoLogConfig> mRunningInstances;

    public static class IncrementalState {
        public final Set<Integer> protologGroupInterningSet = new HashSet();
        public final Set<Long> protologMessageInterningSet = new HashSet();
        public final Map<String, Integer> argumentInterningMap = new HashMap();
        public final Map<String, Integer> stacktraceInterningMap = new HashMap();
        public boolean clearReported = false;
    }

    @Override // android.tracing.perfetto.DataSource
    public /* bridge */ /* synthetic */ IncrementalState createIncrementalState(CreateIncrementalStateArgs createIncrementalStateArgs) {
        return createIncrementalState((CreateIncrementalStateArgs<Instance>) createIncrementalStateArgs);
    }

    @Override // android.tracing.perfetto.DataSource
    public /* bridge */ /* synthetic */ TlsState createTlsState(CreateTlsStateArgs createTlsStateArgs) {
        return createTlsState((CreateTlsStateArgs<Instance>) createTlsStateArgs);
    }

    public ProtoLogDataSource() {
        this(DATASOURCE_NAME);
    }

    public ProtoLogDataSource(String str) {
        super(str);
        this.mRunningInstances = new TreeMap();
        this.mOnStartCallbacks = new HashSet();
        this.mOnFlushCallbacks = new HashSet();
        this.mOnStopCallbacks = new HashSet();
    }

    @Override // android.tracing.perfetto.DataSource
    public Instance createInstance(ProtoInputStream protoInputStream, int i) {
        ProtoLogConfig protoLogConfig = null;
        while (protoInputStream.nextField() != -1) {
            try {
                try {
                    if (protoInputStream.getFieldNumber() == 126) {
                        if (protoLogConfig != null) {
                            throw new RuntimeException("ProtoLog config already set in loop");
                        }
                        protoLogConfig = readProtoLogConfig(protoInputStream);
                    }
                } catch (WireTypeMismatchException e) {
                    throw new RuntimeException("Failed to parse ProtoLog DataSource config", e);
                }
            } catch (IOException e2) {
                throw new RuntimeException("Failed to read ProtoLog DataSource config", e2);
            }
        }
        if (protoLogConfig == null) {
            protoLogConfig = ProtoLogConfig.DEFAULT;
        }
        return new Instance(this, i, protoLogConfig, new Instance.TracingInstanceStartCallback() { // from class: com.android.internal.protolog.ProtoLogDataSource$$ExternalSyntheticLambda0
            @Override // com.android.internal.protolog.ProtoLogDataSource.Instance.TracingInstanceStartCallback
            public final void run(int i2, ProtoLogDataSource.ProtoLogConfig protoLogConfig2) {
                this.f$0.executeOnStartCallbacks(i2, protoLogConfig2);
            }
        }, new Runnable() { // from class: com.android.internal.protolog.ProtoLogDataSource$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.executeOnFlushCallbacks();
            }
        }, new Instance.TracingInstanceStopCallback() { // from class: com.android.internal.protolog.ProtoLogDataSource$$ExternalSyntheticLambda2
            @Override // com.android.internal.protolog.ProtoLogDataSource.Instance.TracingInstanceStopCallback
            public final void run(int i2, ProtoLogDataSource.ProtoLogConfig protoLogConfig2) {
                this.f$0.executeOnStopCallbacks(i2, protoLogConfig2);
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.tracing.perfetto.DataSource
    public TlsState createTlsState(CreateTlsStateArgs<Instance> createTlsStateArgs) {
        Instance instance = (Instance) createTlsStateArgs.getDataSourceInstanceLocked();
        try {
            if (instance == null) {
                TlsState tlsState = new TlsState(ProtoLogConfig.DEFAULT);
                if (instance != null) {
                    instance.close();
                }
                return tlsState;
            }
            TlsState tlsState2 = new TlsState(instance.mConfig);
            if (instance != null) {
                instance.close();
            }
            return tlsState2;
        } catch (Throwable th) {
            if (instance != null) {
                try {
                    instance.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.tracing.perfetto.DataSource
    public IncrementalState createIncrementalState(CreateIncrementalStateArgs<Instance> createIncrementalStateArgs) {
        return new IncrementalState();
    }

    public synchronized void registerOnStartCallback(Instance.TracingInstanceStartCallback tracingInstanceStartCallback) {
        this.mOnStartCallbacks.add(tracingInstanceStartCallback);
        for (Integer num : this.mRunningInstances.keySet()) {
            tracingInstanceStartCallback.run(num.intValue(), this.mRunningInstances.get(num));
        }
    }

    public void registerOnFlushCallback(Runnable runnable) {
        this.mOnFlushCallbacks.add(runnable);
    }

    public void registerOnStopCallback(Instance.TracingInstanceStopCallback tracingInstanceStopCallback) {
        this.mOnStopCallbacks.add(tracingInstanceStopCallback);
    }

    public void unregisterOnStartCallback(Instance.TracingInstanceStartCallback tracingInstanceStartCallback) {
        this.mOnStartCallbacks.add(tracingInstanceStartCallback);
    }

    public void unregisterOnFlushCallback(Runnable runnable) {
        this.mOnFlushCallbacks.add(runnable);
    }

    public void unregisterOnStopCallback(Instance.TracingInstanceStopCallback tracingInstanceStopCallback) {
        this.mOnStopCallbacks.add(tracingInstanceStopCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void executeOnStartCallbacks(int i, ProtoLogConfig protoLogConfig) {
        this.mRunningInstances.put(Integer.valueOf(i), protoLogConfig);
        Iterator<Instance.TracingInstanceStartCallback> it = this.mOnStartCallbacks.iterator();
        while (it.hasNext()) {
            it.next().run(i, protoLogConfig);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeOnFlushCallbacks() {
        Iterator<Runnable> it = this.mOnFlushCallbacks.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void executeOnStopCallbacks(int i, ProtoLogConfig protoLogConfig) {
        this.mRunningInstances.remove(Integer.valueOf(i), protoLogConfig);
        Iterator<Instance.TracingInstanceStopCallback> it = this.mOnStopCallbacks.iterator();
        while (it.hasNext()) {
            it.next().run(i, protoLogConfig);
        }
    }

    public static class TlsState {
        private final ProtoLogConfig mConfig;

        private TlsState(ProtoLogConfig protoLogConfig) {
            this.mConfig = protoLogConfig;
        }

        public LogLevel getLogFromLevel(String str) {
            return getConfigFor(str).logFrom;
        }

        public boolean getShouldCollectStacktrace(String str) {
            return getConfigFor(str).collectStackTrace;
        }

        private GroupConfig getConfigFor(String str) {
            return this.mConfig.getConfigFor(str);
        }
    }

    public static class ProtoLogConfig {
        private static final ProtoLogConfig DEFAULT = new ProtoLogConfig(LogLevel.WTF, new HashMap());
        private final LogLevel mDefaultLogFromLevel;
        private final Map<String, GroupConfig> mGroupConfigs;

        private ProtoLogConfig(LogLevel logLevel, Map<String, GroupConfig> map) {
            this.mDefaultLogFromLevel = logLevel;
            this.mGroupConfigs = map;
        }

        public GroupConfig getConfigFor(String str) {
            return this.mGroupConfigs.getOrDefault(str, getDefaultGroupConfig());
        }

        public GroupConfig getDefaultGroupConfig() {
            return new GroupConfig(this.mDefaultLogFromLevel, false);
        }

        public Set<String> getGroupTagsWithOverriddenConfigs() {
            return this.mGroupConfigs.keySet();
        }
    }

    public static class GroupConfig {
        public final boolean collectStackTrace;
        public final LogLevel logFrom;

        public GroupConfig(LogLevel logLevel, boolean z) {
            this.logFrom = logLevel;
            this.collectStackTrace = z;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ProtoLogConfig readProtoLogConfig(ProtoInputStream protoInputStream) throws IOException {
        int i;
        long jStart = protoInputStream.start(1146756268158L);
        LogLevel logLevelLogLevelFromInt = LogLevel.WTF;
        HashMap map = new HashMap();
        while (true) {
            String string = null;
            Object[] objArr = 0;
            int i2 = -1;
            if (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                long j = 1159641169922L;
                int i3 = 1;
                if (fieldNumber == 1) {
                    long jStart2 = protoInputStream.start(2246267895809L);
                    boolean z = false;
                    LogLevel logLevelLogLevelFromInt2 = logLevelLogLevelFromInt;
                    while (protoInputStream.nextField() != i2) {
                        long j2 = jStart2;
                        if (protoInputStream.getFieldNumber() == i3) {
                            string = protoInputStream.readString(1138166333441L);
                        }
                        if (protoInputStream.getFieldNumber() == 2) {
                            logLevelLogLevelFromInt2 = logLevelFromInt(protoInputStream.readInt(j));
                        }
                        if (protoInputStream.getFieldNumber() == 3) {
                            z = protoInputStream.readBoolean(1133871366147L);
                            jStart2 = j2;
                            i2 = -1;
                            j = 1159641169922L;
                        } else {
                            jStart2 = j2;
                        }
                        i3 = 1;
                    }
                    long j3 = jStart2;
                    if (string == null) {
                        throw new RuntimeException("Failed to decode proto config. Got a group override without a group tag.");
                    }
                    map.put(string, new GroupConfig(logLevelLogLevelFromInt2, z));
                    protoInputStream.end(j3);
                } else if (fieldNumber != 2) {
                    if (fieldNumber == 3 && (i = protoInputStream.readInt(1159641169923L)) < logLevelLogLevelFromInt.ordinal()) {
                        logLevelLogLevelFromInt = logLevelFromInt(i);
                    }
                } else {
                    int i4 = protoInputStream.readInt(1159641169922L);
                    if (i4 == 0) {
                        continue;
                    } else if (i4 == 1) {
                        logLevelLogLevelFromInt = LogLevel.DEBUG;
                    } else {
                        throw new RuntimeException("Unhandled ProtoLog tracing mode type");
                    }
                }
            } else {
                protoInputStream.end(jStart);
                return new ProtoLogConfig(logLevelLogLevelFromInt, map);
            }
        }
    }

    private LogLevel logLevelFromInt(int i) {
        switch (i) {
            case 1:
                return LogLevel.DEBUG;
            case 2:
                return LogLevel.VERBOSE;
            case 3:
                return LogLevel.INFO;
            case 4:
                return LogLevel.WARN;
            case 5:
                return LogLevel.ERROR;
            case 6:
                return LogLevel.WTF;
            default:
                throw new RuntimeException("Unhandled log level");
        }
    }

    public static class Instance extends DataSourceInstance {
        private final ProtoLogConfig mConfig;
        private final int mInstanceIndex;
        private final Runnable mOnFlush;
        private final TracingInstanceStartCallback mOnStart;
        private final TracingInstanceStopCallback mOnStop;

        public interface TracingInstanceStartCallback {
            void run(int i, ProtoLogConfig protoLogConfig);
        }

        public interface TracingInstanceStopCallback {
            void run(int i, ProtoLogConfig protoLogConfig);
        }

        public Instance(DataSource<Instance, TlsState, IncrementalState> dataSource, int i, ProtoLogConfig protoLogConfig, TracingInstanceStartCallback tracingInstanceStartCallback, Runnable runnable, TracingInstanceStopCallback tracingInstanceStopCallback) {
            super(dataSource, i);
            this.mInstanceIndex = i;
            this.mOnStart = tracingInstanceStartCallback;
            this.mOnFlush = runnable;
            this.mOnStop = tracingInstanceStopCallback;
            this.mConfig = protoLogConfig;
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onStart(StartCallbackArguments startCallbackArguments) {
            this.mOnStart.run(this.mInstanceIndex, this.mConfig);
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onFlush(FlushCallbackArguments flushCallbackArguments) {
            this.mOnFlush.run();
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onStop(StopCallbackArguments stopCallbackArguments) {
            this.mOnStop.run(this.mInstanceIndex, this.mConfig);
        }
    }
}
