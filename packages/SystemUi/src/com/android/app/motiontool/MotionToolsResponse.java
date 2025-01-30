package com.android.app.motiontool;

import com.android.app.motiontool.ErrorResponse;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MotionToolsResponse extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int BEGIN_TRACE_FIELD_NUMBER = 3;
    private static final MotionToolsResponse DEFAULT_INSTANCE;
    public static final int END_TRACE_FIELD_NUMBER = 4;
    public static final int ERROR_FIELD_NUMBER = 1;
    public static final int HANDSHAKE_FIELD_NUMBER = 2;
    private static volatile Parser PARSER = null;
    public static final int POLL_TRACE_FIELD_NUMBER = 5;
    private int bitField0_;
    private int typeCase_ = 0;
    private Object type_;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.app.motiontool.MotionToolsResponse$1 */
    public abstract /* synthetic */ class AbstractC06191 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        public static final /* synthetic */ int[] f196xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f196xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f196xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f196xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f196xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f196xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f196xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f196xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder extends GeneratedMessageLite.Builder implements MessageLiteOrBuilder {
        public /* synthetic */ Builder(AbstractC06191 abstractC06191) {
            this();
        }

        public final void setError(ErrorResponse.Builder builder) {
            copyOnWrite();
            MotionToolsResponse.access$200((MotionToolsResponse) this.instance, (ErrorResponse) builder.build());
        }

        private Builder() {
            super(MotionToolsResponse.DEFAULT_INSTANCE);
        }
    }

    static {
        MotionToolsResponse motionToolsResponse = new MotionToolsResponse();
        DEFAULT_INSTANCE = motionToolsResponse;
        GeneratedMessageLite.registerDefaultInstance(MotionToolsResponse.class, motionToolsResponse);
    }

    private MotionToolsResponse() {
    }

    public static void access$1100(MotionToolsResponse motionToolsResponse, EndTraceResponse endTraceResponse) {
        motionToolsResponse.getClass();
        motionToolsResponse.type_ = endTraceResponse;
        motionToolsResponse.typeCase_ = 4;
    }

    public static void access$1400(MotionToolsResponse motionToolsResponse, PollTraceResponse pollTraceResponse) {
        motionToolsResponse.getClass();
        motionToolsResponse.type_ = pollTraceResponse;
        motionToolsResponse.typeCase_ = 5;
    }

    public static void access$200(MotionToolsResponse motionToolsResponse, ErrorResponse errorResponse) {
        motionToolsResponse.getClass();
        motionToolsResponse.type_ = errorResponse;
        motionToolsResponse.typeCase_ = 1;
    }

    public static void access$500(MotionToolsResponse motionToolsResponse, HandshakeResponse handshakeResponse) {
        motionToolsResponse.getClass();
        motionToolsResponse.type_ = handshakeResponse;
        motionToolsResponse.typeCase_ = 2;
    }

    public static void access$800(MotionToolsResponse motionToolsResponse, BeginTraceResponse beginTraceResponse) {
        motionToolsResponse.getClass();
        motionToolsResponse.type_ = beginTraceResponse;
        motionToolsResponse.typeCase_ = 3;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        int i = AbstractC06191.f196xa1df5c61[methodToInvoke.ordinal()];
        AbstractC06191 abstractC06191 = null;
        switch (i) {
            case 1:
                return new MotionToolsResponse();
            case 2:
                return new Builder(abstractC06191);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0001\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ြ\u0000\u0002ြ\u0000\u0003ြ\u0000\u0004ြ\u0000\u0005ြ\u0000", new Object[]{"type_", "typeCase_", "bitField0_", ErrorResponse.class, HandshakeResponse.class, BeginTraceResponse.class, EndTraceResponse.class, PollTraceResponse.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (MotionToolsResponse.class) {
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
