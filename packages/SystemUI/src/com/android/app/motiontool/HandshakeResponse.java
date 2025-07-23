package com.android.app.motiontool;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HandshakeResponse extends GeneratedMessageLite implements MessageLiteOrBuilder {
    private static final HandshakeResponse DEFAULT_INSTANCE;
    private static volatile Parser PARSER = null;
    public static final int SERVER_VERSION_FIELD_NUMBER = 2;
    public static final int STATUS_FIELD_NUMBER = 1;
    private int bitField0_;
    private int serverVersion_;
    private int status_ = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.app.motiontool.HandshakeResponse$1, reason: invalid class name */
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
            super(HandshakeResponse.DEFAULT_INSTANCE);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum Status implements Internal.EnumLite {
        OK(1),
        WINDOW_NOT_FOUND(2);

        private final int value;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class StatusVerifier implements Internal.EnumVerifier {
            public static final StatusVerifier INSTANCE = new StatusVerifier();

            private StatusVerifier() {
            }

            @Override // com.google.protobuf.Internal.EnumVerifier
            public final boolean isInRange(int i) {
                Status status;
                if (i == 1) {
                    status = Status.OK;
                } else if (i != 2) {
                    Status status2 = Status.OK;
                    status = null;
                } else {
                    status = Status.WINDOW_NOT_FOUND;
                }
                return status != null;
            }
        }

        static {
            new Internal.EnumLiteMap() { // from class: com.android.app.motiontool.HandshakeResponse.Status.1
                @Override // com.google.protobuf.Internal.EnumLiteMap
                public final Internal.EnumLite findValueByNumber(int i) {
                    if (i == 1) {
                        return Status.OK;
                    }
                    if (i == 2) {
                        return Status.WINDOW_NOT_FOUND;
                    }
                    Status status = Status.OK;
                    return null;
                }
            };
        }

        Status(int i) {
            this.value = i;
        }

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }
    }

    static {
        HandshakeResponse handshakeResponse = new HandshakeResponse();
        DEFAULT_INSTANCE = handshakeResponse;
        GeneratedMessageLite.registerDefaultInstance(HandshakeResponse.class, handshakeResponse);
    }

    private HandshakeResponse() {
    }

    public static void access$100(HandshakeResponse handshakeResponse, Status status) {
        handshakeResponse.getClass();
        handshakeResponse.status_ = status.getNumber();
        handshakeResponse.bitField0_ |= 1;
    }

    public static void access$300(HandshakeResponse handshakeResponse) {
        handshakeResponse.bitField0_ |= 2;
        handshakeResponse.serverVersion_ = 1;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        Parser parser;
        int i = AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()];
        AnonymousClass1 anonymousClass1 = null;
        switch (i) {
            case 1:
                return new HandshakeResponse();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                Status status = Status.OK;
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002င\u0001", new Object[]{"bitField0_", "status_", Status.StatusVerifier.INSTANCE, "serverVersion_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser2 = PARSER;
                if (parser2 != null) {
                    return parser2;
                }
                synchronized (HandshakeResponse.class) {
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
}
