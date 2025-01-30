package com.android.app.viewcapture.data;

import com.google.protobuf.AbstractProtobufList;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WindowData extends GeneratedMessageLite implements MessageLiteOrBuilder {
    private static final WindowData DEFAULT_INSTANCE;
    public static final int FRAMEDATA_FIELD_NUMBER = 1;
    private static volatile Parser PARSER = null;
    public static final int TITLE_FIELD_NUMBER = 2;
    private int bitField0_;
    private Internal.ProtobufList frameData_ = ProtobufArrayList.EMPTY_LIST;
    private String title_ = "";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.app.viewcapture.data.WindowData$1 */
    public abstract /* synthetic */ class AbstractC06281 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        public static final /* synthetic */ int[] f204xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f204xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f204xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f204xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f204xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f204xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f204xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f204xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder extends GeneratedMessageLite.Builder implements MessageLiteOrBuilder {
        public /* synthetic */ Builder(AbstractC06281 abstractC06281) {
            this();
        }

        private Builder() {
            super(WindowData.DEFAULT_INSTANCE);
        }
    }

    static {
        WindowData windowData = new WindowData();
        DEFAULT_INSTANCE = windowData;
        GeneratedMessageLite.registerDefaultInstance(WindowData.class, windowData);
    }

    private WindowData() {
    }

    public static void access$200(WindowData windowData, FrameData frameData) {
        windowData.getClass();
        Internal.ProtobufList protobufList = windowData.frameData_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            windowData.frameData_ = GeneratedMessageLite.mutableCopy(protobufList);
        }
        windowData.frameData_.add(frameData);
    }

    public static void access$700(WindowData windowData, String str) {
        windowData.getClass();
        str.getClass();
        windowData.bitField0_ |= 1;
        windowData.title_ = str;
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        int i = AbstractC06281.f204xa1df5c61[methodToInvoke.ordinal()];
        AbstractC06281 abstractC06281 = null;
        switch (i) {
            case 1:
                return new WindowData();
            case 2:
                return new Builder(abstractC06281);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဈ\u0000", new Object[]{"bitField0_", "frameData_", FrameData.class, "title_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (WindowData.class) {
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
