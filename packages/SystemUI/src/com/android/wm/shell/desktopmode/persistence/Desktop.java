package com.android.wm.shell.desktopmode.persistence;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractProtobufList;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.IntArrayList;
import com.google.protobuf.Internal;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.google.protobuf.WireFormat$FieldType;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes3.dex */
public final class Desktop extends GeneratedMessageLite implements MessageLiteOrBuilder {
    private static final Desktop DEFAULT_INSTANCE;
    public static final int DESKTOP_ID_FIELD_NUMBER = 2;
    public static final int DISPLAY_ID_FIELD_NUMBER = 1;
    private static volatile Parser PARSER = null;
    public static final int TASKS_BY_TASK_ID_FIELD_NUMBER = 3;
    public static final int USED_FIELD_NUMBER = 15;
    public static final int Z_ORDERED_TASKS_FIELD_NUMBER = 4;
    private int bitField0_;
    private int desktopId_;
    private int displayId_;
    private int used_;
    private MapFieldLite<Integer, DesktopTask> tasksByTaskId_ = MapFieldLite.EMPTY_MAP_FIELD;
    private Internal.IntList zOrderedTasks_ = IntArrayList.EMPTY_LIST;

    /* renamed from: com.android.wm.shell.desktopmode.persistence.Desktop$1, reason: invalid class name */
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
            super(Desktop.DEFAULT_INSTANCE);
        }
    }

    public final class TasksByTaskIdDefaultEntryHolder {
        public static final MapEntryLite defaultEntry = MapEntryLite.newDefaultInstance(WireFormat$FieldType.INT32, 0, WireFormat$FieldType.MESSAGE, DesktopTask.getDefaultInstance());

        private TasksByTaskIdDefaultEntryHolder() {
        }
    }

    /* renamed from: -$$Nest$maddAllZOrderedTasks, reason: not valid java name */
    public static void m3249$$Nest$maddAllZOrderedTasks(Desktop desktop, Iterable iterable) {
        Internal.ProtobufList protobufList = desktop.zOrderedTasks_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            int size = protobufList.size();
            desktop.zOrderedTasks_ = ((IntArrayList) protobufList).mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
        }
        AbstractMessageLite.addAll(iterable, desktop.zOrderedTasks_);
    }

    /* renamed from: -$$Nest$mclearZOrderedTasks, reason: not valid java name */
    public static void m3250$$Nest$mclearZOrderedTasks(Desktop desktop) {
        desktop.getClass();
        desktop.zOrderedTasks_ = IntArrayList.EMPTY_LIST;
    }

    /* renamed from: -$$Nest$mgetMutableTasksByTaskIdMap, reason: not valid java name */
    public static MapFieldLite m3251$$Nest$mgetMutableTasksByTaskIdMap(Desktop desktop) {
        if (!desktop.tasksByTaskId_.isMutable()) {
            desktop.tasksByTaskId_ = desktop.tasksByTaskId_.mutableCopy();
        }
        return desktop.tasksByTaskId_;
    }

    /* renamed from: -$$Nest$msetDesktopId, reason: not valid java name */
    public static void m3252$$Nest$msetDesktopId(int i, Desktop desktop) {
        desktop.bitField0_ |= 2;
        desktop.desktopId_ = i;
    }

    /* renamed from: -$$Nest$msetDisplayId, reason: not valid java name */
    public static void m3253$$Nest$msetDisplayId(int i, Desktop desktop) {
        desktop.bitField0_ |= 1;
        desktop.displayId_ = i;
    }

    /* renamed from: -$$Nest$msetUsed, reason: not valid java name */
    public static void m3254$$Nest$msetUsed(int i, Desktop desktop) {
        desktop.bitField0_ |= 4;
        desktop.used_ = i;
    }

    static {
        Desktop desktop = new Desktop();
        DEFAULT_INSTANCE = desktop;
        GeneratedMessageLite.registerDefaultInstance(Desktop.class, desktop);
    }

    private Desktop() {
    }

    public static Desktop getDefaultInstance() {
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
                return new Desktop();
            case 2:
                return new Builder(0);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u000f\u0005\u0001\u0001\u0000\u0001င\u0000\u0002င\u0001\u00032\u0004\u0016\u000fင\u0002", new Object[]{"bitField0_", "displayId_", "desktopId_", "tasksByTaskId_", TasksByTaskIdDefaultEntryHolder.defaultEntry, "zOrderedTasks_", "used_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser != null) {
                    return parser;
                }
                synchronized (Desktop.class) {
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

    public final int getDesktopId() {
        return this.desktopId_;
    }

    public final int getDisplayId() {
        return this.displayId_;
    }

    public final Map getTasksByTaskIdMap() {
        return Collections.unmodifiableMap(this.tasksByTaskId_);
    }

    public final int getUsed() {
        return this.used_;
    }

    public final int getZOrderedTasksCount() {
        return this.zOrderedTasks_.size();
    }

    public final Internal.IntList getZOrderedTasksList() {
        return this.zOrderedTasks_;
    }
}
