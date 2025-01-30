package com.android.app.motiontool;

import com.android.app.viewcapture.data.MotionWindowData;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PollTraceResponse extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int DATA_FIELD_NUMBER = 1;
    private static final PollTraceResponse DEFAULT_INSTANCE;
    private static volatile Parser PARSER;
    private int bitField0_;
    private MotionWindowData data_;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.app.motiontool.PollTraceResponse$1 */
    public abstract /* synthetic */ class AbstractC06211 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        public static final /* synthetic */ int[] f198xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f198xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f198xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f198xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f198xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f198xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f198xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f198xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder extends GeneratedMessageLite.Builder implements MessageLiteOrBuilder {
        public /* synthetic */ Builder(AbstractC06211 abstractC06211) {
            this();
        }

        private Builder() {
            super(PollTraceResponse.DEFAULT_INSTANCE);
        }
    }

    static {
        PollTraceResponse pollTraceResponse = new PollTraceResponse();
        DEFAULT_INSTANCE = pollTraceResponse;
        GeneratedMessageLite.registerDefaultInstance(PollTraceResponse.class, pollTraceResponse);
    }

    private PollTraceResponse() {
    }

    public static void access$100(PollTraceResponse pollTraceResponse, MotionWindowData motionWindowData) {
        pollTraceResponse.getClass();
        pollTraceResponse.data_ = motionWindowData;
        pollTraceResponse.bitField0_ |= 1;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        int i = AbstractC06211.f198xa1df5c61[methodToInvoke.ordinal()];
        AbstractC06211 abstractC06211 = null;
        switch (i) {
            case 1:
                return new PollTraceResponse();
            case 2:
                return new Builder(abstractC06211);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"bitField0_", "data_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (PollTraceResponse.class) {
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
}
