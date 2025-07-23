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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder extends GeneratedMessageLite.Builder {
        public /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(MotionToolsRequest.DEFAULT_INSTANCE);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public static MotionToolsRequest parseFrom(byte[] bArr) {
        MotionToolsRequest motionToolsRequest = DEFAULT_INSTANCE;
        int length = bArr.length;
        ExtensionRegistryLite emptyRegistry = ExtensionRegistryLite.getEmptyRegistry();
        GeneratedMessageLite newMutableInstance$1 = motionToolsRequest.newMutableInstance$1();
        try {
            Schema schemaFor = Protobuf.INSTANCE.schemaFor(newMutableInstance$1);
            schemaFor.mergeFrom(newMutableInstance$1, bArr, 0, length, new ArrayDecoders.Registers(emptyRegistry));
            schemaFor.makeImmutable(newMutableInstance$1);
            if (GeneratedMessageLite.isInitialized(newMutableInstance$1, true)) {
                return (MotionToolsRequest) newMutableInstance$1;
            }
            InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(new UninitializedMessageException(newMutableInstance$1).getMessage());
            invalidProtocolBufferException.setUnfinishedMessage(newMutableInstance$1);
            throw invalidProtocolBufferException;
        } catch (InvalidProtocolBufferException e) {
            InvalidProtocolBufferException invalidProtocolBufferException2 = e;
            if (invalidProtocolBufferException2.getThrownFromInputStream()) {
                invalidProtocolBufferException2 = new InvalidProtocolBufferException((IOException) invalidProtocolBufferException2);
            }
            invalidProtocolBufferException2.setUnfinishedMessage(newMutableInstance$1);
            throw invalidProtocolBufferException2;
        } catch (UninitializedMessageException e2) {
            InvalidProtocolBufferException invalidProtocolBufferException3 = new InvalidProtocolBufferException(e2.getMessage());
            invalidProtocolBufferException3.setUnfinishedMessage(newMutableInstance$1);
            throw invalidProtocolBufferException3;
        } catch (IOException e3) {
            if (e3.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e3.getCause());
            }
            InvalidProtocolBufferException invalidProtocolBufferException4 = new InvalidProtocolBufferException(e3);
            invalidProtocolBufferException4.setUnfinishedMessage(newMutableInstance$1);
            throw invalidProtocolBufferException4;
        } catch (IndexOutOfBoundsException unused) {
            InvalidProtocolBufferException truncatedMessage = InvalidProtocolBufferException.truncatedMessage();
            truncatedMessage.setUnfinishedMessage(newMutableInstance$1);
            throw truncatedMessage;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        Parser parser;
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
                Parser parser2 = PARSER;
                if (parser2 != null) {
                    return parser2;
                }
                synchronized (MotionToolsRequest.class) {
                    try {
                        parser = PARSER;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    } catch (Throwable th) {
                        throw th;
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
