package com.android.app.motiontool;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HandshakeResponse extends GeneratedMessageLite implements MessageLiteOrBuilder {
    private static final HandshakeResponse DEFAULT_INSTANCE;
    private static volatile Parser PARSER = null;
    public static final int SERVER_VERSION_FIELD_NUMBER = 2;
    public static final int STATUS_FIELD_NUMBER = 1;
    private int bitField0_;
    private int serverVersion_;
    private int status_ = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.app.motiontool.HandshakeResponse$1 */
    public abstract /* synthetic */ class AbstractC06161 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        public static final /* synthetic */ int[] f193xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f193xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f193xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f193xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f193xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f193xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f193xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f193xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder extends GeneratedMessageLite.Builder implements MessageLiteOrBuilder {
        public /* synthetic */ Builder(AbstractC06161 abstractC06161) {
            this();
        }

        private Builder() {
            super(HandshakeResponse.DEFAULT_INSTANCE);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Status implements Internal.EnumLite {
        OK(1),
        WINDOW_NOT_FOUND(2);

        private final int value;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        int i = AbstractC06161.f193xa1df5c61[methodToInvoke.ordinal()];
        AbstractC06161 abstractC06161 = null;
        switch (i) {
            case 1:
                return new HandshakeResponse();
            case 2:
                return new Builder(abstractC06161);
            case 3:
                Status status = Status.OK;
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002င\u0001", new Object[]{"bitField0_", "status_", Status.StatusVerifier.INSTANCE, "serverVersion_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (HandshakeResponse.class) {
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
