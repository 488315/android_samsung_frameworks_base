package com.android.app.viewcapture.data;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractProtobufList;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MotionWindowData extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int CLASSNAME_FIELD_NUMBER = 2;
    private static final MotionWindowData DEFAULT_INSTANCE;
    public static final int FRAMEDATA_FIELD_NUMBER = 1;
    private static volatile Parser PARSER;
    private Internal.ProtobufList classname_;
    private Internal.ProtobufList frameData_;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.app.viewcapture.data.MotionWindowData$1 */
    public abstract /* synthetic */ class AbstractC06261 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        public static final /* synthetic */ int[] f202xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f202xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f202xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f202xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f202xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f202xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f202xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f202xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder extends GeneratedMessageLite.Builder implements MessageLiteOrBuilder {
        public /* synthetic */ Builder(AbstractC06261 abstractC06261) {
            this();
        }

        private Builder() {
            super(MotionWindowData.DEFAULT_INSTANCE);
        }
    }

    static {
        MotionWindowData motionWindowData = new MotionWindowData();
        DEFAULT_INSTANCE = motionWindowData;
        GeneratedMessageLite.registerDefaultInstance(MotionWindowData.class, motionWindowData);
    }

    private MotionWindowData() {
        ProtobufArrayList protobufArrayList = ProtobufArrayList.EMPTY_LIST;
        this.frameData_ = protobufArrayList;
        this.classname_ = protobufArrayList;
    }

    public static void access$400(MotionWindowData motionWindowData, Iterable iterable) {
        Internal.ProtobufList protobufList = motionWindowData.frameData_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            motionWindowData.frameData_ = GeneratedMessageLite.mutableCopy(protobufList);
        }
        AbstractMessageLite.addAll(iterable, motionWindowData.frameData_);
    }

    public static void access$500(MotionWindowData motionWindowData) {
        motionWindowData.getClass();
        motionWindowData.frameData_ = ProtobufArrayList.EMPTY_LIST;
    }

    public static void access$900(MotionWindowData motionWindowData, Iterable iterable) {
        Internal.ProtobufList protobufList = motionWindowData.classname_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            motionWindowData.classname_ = GeneratedMessageLite.mutableCopy(protobufList);
        }
        AbstractMessageLite.addAll(iterable, motionWindowData.classname_);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        int i = AbstractC06261.f202xa1df5c61[methodToInvoke.ordinal()];
        AbstractC06261 abstractC06261 = null;
        switch (i) {
            case 1:
                return new MotionWindowData();
            case 2:
                return new Builder(abstractC06261);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0002\u0000\u0001\u001b\u0002\u001a", new Object[]{"frameData_", FrameData.class, "classname_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (MotionWindowData.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    }
                }
                return parser;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public final Internal.ProtobufList getFrameDataList() {
        return this.frameData_;
    }
}
