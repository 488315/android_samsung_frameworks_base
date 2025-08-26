package com.android.wm.shell.desktopmode.persistence;

import com.android.wm.shell.desktopmode.persistence.DesktopTaskState;
import com.android.wm.shell.desktopmode.persistence.DesktopTaskTilingState;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* loaded from: classes3.dex */
public final class DesktopTask extends GeneratedMessageLite implements MessageLiteOrBuilder {
    private static final DesktopTask DEFAULT_INSTANCE;
    public static final int DESKTOP_TASK_STATE_FIELD_NUMBER = 2;
    public static final int DESKTOP_TASK_TILING_STATE_FIELD_NUMBER = 3;
    private static volatile Parser PARSER = null;
    public static final int TASK_ID_FIELD_NUMBER = 1;
    private int bitField0_;
    private int desktopTaskState_;
    private int desktopTaskTilingState_ = 1;
    private int taskId_;

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopTask$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public final class Builder extends GeneratedMessageLite.Builder {
        public /* synthetic */ Builder(int i) {
            this();
        }

        private Builder() {
            super(DesktopTask.DEFAULT_INSTANCE);
        }
    }

    /* renamed from: -$$Nest$msetDesktopTaskState, reason: not valid java name */
    public static void m3261$$Nest$msetDesktopTaskState(DesktopTask desktopTask, DesktopTaskState desktopTaskState) {
        desktopTask.getClass();
        desktopTask.desktopTaskState_ = desktopTaskState.getNumber();
        desktopTask.bitField0_ |= 2;
    }

    /* renamed from: -$$Nest$msetDesktopTaskTilingState, reason: not valid java name */
    public static void m3262$$Nest$msetDesktopTaskTilingState(DesktopTask desktopTask, DesktopTaskTilingState desktopTaskTilingState) {
        desktopTask.getClass();
        desktopTask.desktopTaskTilingState_ = desktopTaskTilingState.getNumber();
        desktopTask.bitField0_ |= 4;
    }

    /* renamed from: -$$Nest$msetTaskId, reason: not valid java name */
    public static void m3263$$Nest$msetTaskId(DesktopTask desktopTask, int i) {
        desktopTask.bitField0_ |= 1;
        desktopTask.taskId_ = i;
    }

    static {
        DesktopTask desktopTask = new DesktopTask();
        DEFAULT_INSTANCE = desktopTask;
        GeneratedMessageLite.registerDefaultInstance(DesktopTask.class, desktopTask);
    }

    private DesktopTask() {
    }

    public static DesktopTask getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        Parser defaultInstanceBasedParser;
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
            case 1:
                return new DesktopTask();
            case 2:
                return new Builder(0);
            case 3:
                DesktopTaskState desktopTaskState = DesktopTaskState.VISIBLE;
                DesktopTaskState.DesktopTaskStateVerifier desktopTaskStateVerifier = DesktopTaskState.DesktopTaskStateVerifier.INSTANCE;
                DesktopTaskTilingState desktopTaskTilingState = DesktopTaskTilingState.NONE;
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001င\u0000\u0002ဌ\u0001\u0003ဌ\u0002", new Object[]{"bitField0_", "taskId_", "desktopTaskState_", desktopTaskStateVerifier, "desktopTaskTilingState_", DesktopTaskTilingState.DesktopTaskTilingStateVerifier.INSTANCE});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser != null) {
                    return parser;
                }
                synchronized (DesktopTask.class) {
                    try {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return defaultInstanceBasedParser;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public final DesktopTaskState getDesktopTaskState() {
        DesktopTaskState desktopTaskState;
        int i = this.desktopTaskState_;
        if (i == 0) {
            desktopTaskState = DesktopTaskState.VISIBLE;
        } else if (i != 1) {
            DesktopTaskState desktopTaskState2 = DesktopTaskState.VISIBLE;
            desktopTaskState = null;
        } else {
            desktopTaskState = DesktopTaskState.MINIMIZED;
        }
        return desktopTaskState == null ? DesktopTaskState.VISIBLE : desktopTaskState;
    }

    public final DesktopTaskTilingState getDesktopTaskTilingState() {
        DesktopTaskTilingState desktopTaskTilingStateForNumber = DesktopTaskTilingState.forNumber(this.desktopTaskTilingState_);
        return desktopTaskTilingStateForNumber == null ? DesktopTaskTilingState.NONE : desktopTaskTilingStateForNumber;
    }

    public final int getTaskId() {
        return this.taskId_;
    }
}
