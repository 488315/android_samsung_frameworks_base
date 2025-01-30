package com.android.app.motiontool;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ErrorResponse extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int CODE_FIELD_NUMBER = 1;
    private static final ErrorResponse DEFAULT_INSTANCE;
    public static final int MESSAGE_FIELD_NUMBER = 2;
    private static volatile Parser PARSER;
    private int bitField0_;
    private int code_;
    private String message_ = "";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.app.motiontool.ErrorResponse$1 */
    public abstract /* synthetic */ class AbstractC06131 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        public static final /* synthetic */ int[] f191xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f191xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f191xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder extends GeneratedMessageLite.Builder implements MessageLiteOrBuilder {
        public /* synthetic */ Builder(AbstractC06131 abstractC06131) {
            this();
        }

        private Builder() {
            super(ErrorResponse.DEFAULT_INSTANCE);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Code implements Internal.EnumLite {
        UNKNOWN(0),
        INVALID_REQUEST(1),
        UNKNOWN_TRACE_ID(2),
        WINDOW_NOT_FOUND(3);

        private final int value;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class CodeVerifier implements Internal.EnumVerifier {
            public static final CodeVerifier INSTANCE = new CodeVerifier();

            private CodeVerifier() {
            }

            @Override // com.google.protobuf.Internal.EnumVerifier
            public final boolean isInRange(int i) {
                Code code;
                if (i == 0) {
                    code = Code.UNKNOWN;
                } else if (i == 1) {
                    code = Code.INVALID_REQUEST;
                } else if (i == 2) {
                    code = Code.UNKNOWN_TRACE_ID;
                } else if (i != 3) {
                    Code code2 = Code.UNKNOWN;
                    code = null;
                } else {
                    code = Code.WINDOW_NOT_FOUND;
                }
                return code != null;
            }
        }

        static {
            new Internal.EnumLiteMap() { // from class: com.android.app.motiontool.ErrorResponse.Code.1
                @Override // com.google.protobuf.Internal.EnumLiteMap
                public final Internal.EnumLite findValueByNumber(int i) {
                    if (i == 0) {
                        return Code.UNKNOWN;
                    }
                    if (i == 1) {
                        return Code.INVALID_REQUEST;
                    }
                    if (i == 2) {
                        return Code.UNKNOWN_TRACE_ID;
                    }
                    if (i == 3) {
                        return Code.WINDOW_NOT_FOUND;
                    }
                    Code code = Code.UNKNOWN;
                    return null;
                }
            };
        }

        Code(int i) {
            this.value = i;
        }

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }
    }

    static {
        ErrorResponse errorResponse = new ErrorResponse();
        DEFAULT_INSTANCE = errorResponse;
        GeneratedMessageLite.registerDefaultInstance(ErrorResponse.class, errorResponse);
    }

    private ErrorResponse() {
    }

    public static void access$100(ErrorResponse errorResponse, Code code) {
        errorResponse.getClass();
        errorResponse.code_ = code.getNumber();
        errorResponse.bitField0_ |= 1;
    }

    public static void access$300(ErrorResponse errorResponse, String str) {
        errorResponse.getClass();
        str.getClass();
        errorResponse.bitField0_ |= 2;
        errorResponse.message_ = str;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        int i = AbstractC06131.f191xa1df5c61[methodToInvoke.ordinal()];
        AbstractC06131 abstractC06131 = null;
        switch (i) {
            case 1:
                return new ErrorResponse();
            case 2:
                return new Builder(abstractC06131);
            case 3:
                Code code = Code.UNKNOWN;
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဈ\u0001", new Object[]{"bitField0_", "code_", Code.CodeVerifier.INSTANCE, "message_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (ErrorResponse.class) {
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
