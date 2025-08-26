package com.android.app.motiontool;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* loaded from: classes.dex */
public final class ErrorResponse extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int CODE_FIELD_NUMBER = 1;
    private static final ErrorResponse DEFAULT_INSTANCE;
    public static final int MESSAGE_FIELD_NUMBER = 2;
    private static volatile Parser PARSER;
    private int bitField0_;
    private int code_;
    private String message_ = "";

    /* renamed from: com.android.app.motiontool.ErrorResponse$1, reason: invalid class name */
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
        public /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(ErrorResponse.DEFAULT_INSTANCE);
        }
    }

    public enum Code implements Internal.EnumLite {
        UNKNOWN(0),
        INVALID_REQUEST(1),
        UNKNOWN_TRACE_ID(2),
        WINDOW_NOT_FOUND(3);

        private final int value;

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
        Parser defaultInstanceBasedParser;
        int i = AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()];
        AnonymousClass1 anonymousClass1 = null;
        switch (i) {
            case 1:
                return new ErrorResponse();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                Code code = Code.UNKNOWN;
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဈ\u0001", new Object[]{"bitField0_", "code_", Code.CodeVerifier.INSTANCE, "message_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser != null) {
                    return parser;
                }
                synchronized (ErrorResponse.class) {
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
}
