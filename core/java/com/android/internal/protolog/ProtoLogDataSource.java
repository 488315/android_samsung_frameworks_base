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

import com.android.internal.protolog.common.LogLevel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ProtoLogDataSource extends DataSource<Instance, TlsState, IncrementalState> {
    private final Runnable mOnFlush;
    private final Consumer<ProtoLogConfig> mOnStart;
    private final Consumer<ProtoLogConfig> mOnStop;

    public static class IncrementalState {
        public final Map<String, Integer> argumentInterningMap = new HashMap();
        public final Map<String, Integer> stacktraceInterningMap = new HashMap();
        public boolean clearReported = false;
    }

    public ProtoLogDataSource(
            Consumer<ProtoLogConfig> onStart, Runnable onFlush, Consumer<ProtoLogConfig> onStop) {
        super("android.protolog");
        this.mOnStart = onStart;
        this.mOnFlush = onFlush;
        this.mOnStop = onStop;
    }

    @Override // android.tracing.perfetto.DataSource
    public Instance createInstance(ProtoInputStream configStream, int instanceIndex) {
        ProtoLogConfig config = null;
        while (configStream.nextField() != -1) {
            try {
                try {
                    if (configStream.getFieldNumber() == 126) {
                        if (config != null) {
                            throw new RuntimeException("ProtoLog config already set in loop");
                        }
                        config = readProtoLogConfig(configStream);
                    }
                } catch (WireTypeMismatchException e) {
                    throw new RuntimeException("Failed to parse ProtoLog DataSource config", e);
                }
            } catch (IOException e2) {
                throw new RuntimeException("Failed to read ProtoLog DataSource config", e2);
            }
        }
        if (config == null) {
            config = ProtoLogConfig.DEFAULT;
        }
        return new Instance(
                this, instanceIndex, config, this.mOnStart, this.mOnFlush, this.mOnStop);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.tracing.perfetto.DataSource
    public TlsState createTlsState(CreateTlsStateArgs<Instance> args) {
        Instance dsInstance = args.getDataSourceInstanceLocked();
        try {
            if (dsInstance == null) {
                TlsState tlsState = new TlsState(ProtoLogConfig.DEFAULT);
                if (dsInstance != null) {
                    dsInstance.close();
                }
                return tlsState;
            }
            TlsState tlsState2 = new TlsState(dsInstance.mConfig);
            if (dsInstance != null) {
                dsInstance.close();
            }
            return tlsState2;
        } catch (Throwable th) {
            if (dsInstance != null) {
                try {
                    dsInstance.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.tracing.perfetto.DataSource
    public IncrementalState createIncrementalState(CreateIncrementalStateArgs<Instance> args) {
        return new IncrementalState();
    }

    public static class TlsState {
        private final ProtoLogConfig mConfig;

        private TlsState(ProtoLogConfig config) {
            this.mConfig = config;
        }

        public LogLevel getLogFromLevel(String groupTag) {
            return getConfigFor(groupTag).logFrom;
        }

        public boolean getShouldCollectStacktrace(String groupTag) {
            return getConfigFor(groupTag).collectStackTrace;
        }

        private GroupConfig getConfigFor(String groupTag) {
            return this.mConfig.getConfigFor(groupTag);
        }
    }

    public static class ProtoLogConfig {
        private static final ProtoLogConfig DEFAULT =
                new ProtoLogConfig(LogLevel.WTF, new HashMap());
        private final LogLevel mDefaultLogFromLevel;
        private final Map<String, GroupConfig> mGroupConfigs;

        private ProtoLogConfig(
                LogLevel defaultLogFromLevel, Map<String, GroupConfig> groupConfigs) {
            this.mDefaultLogFromLevel = defaultLogFromLevel;
            this.mGroupConfigs = groupConfigs;
        }

        public GroupConfig getConfigFor(String groupTag) {
            return this.mGroupConfigs.getOrDefault(groupTag, getDefaultGroupConfig());
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

        public GroupConfig(LogLevel logFromLevel, boolean collectStackTrace) {
            this.logFrom = logFromLevel;
            this.collectStackTrace = collectStackTrace;
        }
    }

    private ProtoLogConfig readProtoLogConfig(ProtoInputStream configStream) throws IOException {
        long group_overrides_token;
        long config_token = configStream.start(1146756268158L);
        LogLevel defaultLogFromLevel = LogLevel.WTF;
        Map<String, GroupConfig> groupConfigs = new HashMap<>();
        while (configStream.nextField() != -1) {
            if (configStream.getFieldNumber() == 2) {
                int tracingMode = configStream.readInt(1159641169922L);
                switch (tracingMode) {
                    case 0:
                        break;
                    case 1:
                        defaultLogFromLevel = LogLevel.DEBUG;
                        break;
                    default:
                        throw new RuntimeException("Unhandled ProtoLog tracing mode type");
                }
            }
            int tracingMode2 = configStream.getFieldNumber();
            int i = 1;
            if (tracingMode2 == 1) {
                long group_overrides_token2 = configStream.start(2246267895809L);
                String tag = null;
                LogLevel logFromLevel = defaultLogFromLevel;
                boolean collectStackTrace = false;
                while (configStream.nextField() != -1) {
                    if (configStream.getFieldNumber() != i) {
                        group_overrides_token = group_overrides_token2;
                    } else {
                        group_overrides_token = group_overrides_token2;
                        tag = configStream.readString(1138166333441L);
                    }
                    if (configStream.getFieldNumber() == 2) {
                        int logFromInt = configStream.readInt(1159641169922L);
                        switch (logFromInt) {
                            case 1:
                                LogLevel logFromLevel2 = LogLevel.DEBUG;
                                logFromLevel = logFromLevel2;
                                break;
                            case 2:
                                LogLevel logFromLevel3 = LogLevel.VERBOSE;
                                logFromLevel = logFromLevel3;
                                break;
                            case 3:
                                LogLevel logFromLevel4 = LogLevel.INFO;
                                logFromLevel = logFromLevel4;
                                break;
                            case 4:
                                LogLevel logFromLevel5 = LogLevel.WARN;
                                logFromLevel = logFromLevel5;
                                break;
                            case 5:
                                LogLevel logFromLevel6 = LogLevel.ERROR;
                                logFromLevel = logFromLevel6;
                                break;
                            case 6:
                                LogLevel logFromLevel7 = LogLevel.WTF;
                                logFromLevel = logFromLevel7;
                                break;
                            default:
                                throw new RuntimeException("Unhandled log level");
                        }
                    }
                    int logFromInt2 = configStream.getFieldNumber();
                    if (logFromInt2 == 3) {
                        collectStackTrace = configStream.readBoolean(1133871366147L);
                        group_overrides_token2 = group_overrides_token;
                        i = 1;
                    } else {
                        group_overrides_token2 = group_overrides_token;
                        i = 1;
                    }
                }
                long group_overrides_token3 = group_overrides_token2;
                if (tag == null) {
                    throw new RuntimeException(
                            "Failed to decode proto config. Got a group override without a group"
                                + " tag.");
                }
                groupConfigs.put(tag, new GroupConfig(logFromLevel, collectStackTrace));
                configStream.end(group_overrides_token3);
            }
        }
        configStream.end(config_token);
        return new ProtoLogConfig(defaultLogFromLevel, groupConfigs);
    }

    public static class Instance extends DataSourceInstance {
        private final ProtoLogConfig mConfig;
        private final Runnable mOnFlush;
        private final Consumer<ProtoLogConfig> mOnStart;
        private final Consumer<ProtoLogConfig> mOnStop;

        public Instance(
                DataSource<Instance, TlsState, IncrementalState> dataSource,
                int instanceIdx,
                ProtoLogConfig config,
                Consumer<ProtoLogConfig> onStart,
                Runnable onFlush,
                Consumer<ProtoLogConfig> onStop) {
            super(dataSource, instanceIdx);
            this.mOnStart = onStart;
            this.mOnFlush = onFlush;
            this.mOnStop = onStop;
            this.mConfig = config;
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onStart(StartCallbackArguments args) {
            this.mOnStart.accept(this.mConfig);
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onFlush(FlushCallbackArguments args) {
            this.mOnFlush.run();
        }

        @Override // android.tracing.perfetto.DataSourceInstance
        public void onStop(StopCallbackArguments args) {
            this.mOnStop.accept(this.mConfig);
        }
    }
}
