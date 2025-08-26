package com.android.app.motiontool;

import com.google.protobuf.ArrayDecoders;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.Protobuf;
import com.google.protobuf.RawMessageInfo;
import com.google.protobuf.Schema;
import com.google.protobuf.UninitializedMessageException;
import java.io.IOException;

/* loaded from: classes.dex */
public final class MotionToolsRequest extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int BEGIN_TRACE_FIELD_NUMBER = 2;
    private static final MotionToolsRequest DEFAULT_INSTANCE;
    public static final int END_TRACE_FIELD_NUMBER = 3;
    public static final int HANDSHAKE_FIELD_NUMBER = 1;
    private static volatile Parser PARSER = null;
    public static final int POLL_TRACE_FIELD_NUMBER = 4;
    private int bitField0_;
    private int typeCase_ = 0;
    private Object type_;

    /* renamed from: com.android.app.motiontool.MotionToolsRequest$1, reason: invalid class name */
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
            super(MotionToolsRequest.DEFAULT_INSTANCE);
        }
    }

    public enum TypeCase {
        HANDSHAKE(1),
        BEGIN_TRACE(2),
        END_TRACE(3),
        POLL_TRACE(4),
        TYPE_NOT_SET(0);

        private final int value;

        TypeCase(int i) {
            this.value = i;
        }

        public final int getNumber() {
            return this.value;
        }
    }

    static {
        MotionToolsRequest motionToolsRequest = new MotionToolsRequest();
        DEFAULT_INSTANCE = motionToolsRequest;
        GeneratedMessageLite.registerDefaultInstance(MotionToolsRequest.class, motionToolsRequest);
    }

    private MotionToolsRequest() {
    }

    public static MotionToolsRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        MotionToolsRequest motionToolsRequest = DEFAULT_INSTANCE;
        int length = bArr.length;
        ExtensionRegistryLite emptyRegistry = ExtensionRegistryLite.getEmptyRegistry();
        GeneratedMessageLite generatedMessageLiteNewMutableInstance$1 = motionToolsRequest.newMutableInstance$1();
        try {
            Schema schemaSchemaFor = Protobuf.INSTANCE.schemaFor(generatedMessageLiteNewMutableInstance$1);
            schemaSchemaFor.mergeFrom(generatedMessageLiteNewMutableInstance$1, bArr, 0, length, new ArrayDecoders.Registers(emptyRegistry));
            schemaSchemaFor.makeImmutable(generatedMessageLiteNewMutableInstance$1);
            if (GeneratedMessageLite.isInitialized(generatedMessageLiteNewMutableInstance$1, true)) {
                return (MotionToolsRequest) generatedMessageLiteNewMutableInstance$1;
            }
            InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(new UninitializedMessageException(generatedMessageLiteNewMutableInstance$1).getMessage());
            invalidProtocolBufferException.setUnfinishedMessage(generatedMessageLiteNewMutableInstance$1);
            throw invalidProtocolBufferException;
        } catch (InvalidProtocolBufferException e) {
            InvalidProtocolBufferException invalidProtocolBufferException2 = e;
            if (invalidProtocolBufferException2.getThrownFromInputStream()) {
                invalidProtocolBufferException2 = new InvalidProtocolBufferException((IOException) invalidProtocolBufferException2);
            }
            invalidProtocolBufferException2.setUnfinishedMessage(generatedMessageLiteNewMutableInstance$1);
            throw invalidProtocolBufferException2;
        } catch (UninitializedMessageException e2) {
            InvalidProtocolBufferException invalidProtocolBufferException3 = new InvalidProtocolBufferException(e2.getMessage());
            invalidProtocolBufferException3.setUnfinishedMessage(generatedMessageLiteNewMutableInstance$1);
            throw invalidProtocolBufferException3;
        } catch (IOException e3) {
            if (e3.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e3.getCause());
            }
            InvalidProtocolBufferException invalidProtocolBufferException4 = new InvalidProtocolBufferException(e3);
            invalidProtocolBufferException4.setUnfinishedMessage(generatedMessageLiteNewMutableInstance$1);
            throw invalidProtocolBufferException4;
        } catch (IndexOutOfBoundsException unused) {
            InvalidProtocolBufferException invalidProtocolBufferExceptionTruncatedMessage = InvalidProtocolBufferException.truncatedMessage();
            invalidProtocolBufferExceptionTruncatedMessage.setUnfinishedMessage(generatedMessageLiteNewMutableInstance$1);
            throw invalidProtocolBufferExceptionTruncatedMessage;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        Parser defaultInstanceBasedParser;
        int i = AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()];
        AnonymousClass1 anonymousClass1 = null;
        switch (i) {
            case 1:
                return new MotionToolsRequest();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0001\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ြ\u0000\u0002ြ\u0000\u0003ြ\u0000\u0004ြ\u0000", new Object[]{"type_", "typeCase_", "bitField0_", HandshakeRequest.class, BeginTraceRequest.class, EndTraceRequest.class, PollTraceRequest.class});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser != null) {
                    return parser;
                }
                synchronized (MotionToolsRequest.class) {
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

    public final BeginTraceRequest getBeginTrace() {
        return this.typeCase_ == 2 ? (BeginTraceRequest) this.type_ : BeginTraceRequest.getDefaultInstance();
    }

    public final EndTraceRequest getEndTrace() {
        return this.typeCase_ == 3 ? (EndTraceRequest) this.type_ : EndTraceRequest.getDefaultInstance();
    }

    public final HandshakeRequest getHandshake() {
        return this.typeCase_ == 1 ? (HandshakeRequest) this.type_ : HandshakeRequest.getDefaultInstance();
    }

    public final PollTraceRequest getPollTrace() {
        return this.typeCase_ == 4 ? (PollTraceRequest) this.type_ : PollTraceRequest.getDefaultInstance();
    }

    public final TypeCase getTypeCase() {
        int i = this.typeCase_;
        if (i == 0) {
            return TypeCase.TYPE_NOT_SET;
        }
        if (i == 1) {
            return TypeCase.HANDSHAKE;
        }
        if (i == 2) {
            return TypeCase.BEGIN_TRACE;
        }
        if (i == 3) {
            return TypeCase.END_TRACE;
        }
        if (i == 4) {
            return TypeCase.POLL_TRACE;
        }
        TypeCase typeCase = TypeCase.HANDSHAKE;
        return null;
    }
}
