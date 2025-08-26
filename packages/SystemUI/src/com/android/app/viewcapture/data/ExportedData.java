package com.android.app.viewcapture.data;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractProtobufList;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* loaded from: classes.dex */
public final class ExportedData extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int CLASSNAME_FIELD_NUMBER = 4;
    private static final ExportedData DEFAULT_INSTANCE;
    public static final int MAGIC_NUMBER_FIELD_NUMBER = 1;
    public static final int PACKAGE_FIELD_NUMBER = 3;
    private static volatile Parser PARSER = null;
    public static final int REAL_TO_ELAPSED_TIME_OFFSET_NANOS_FIELD_NUMBER = 5;
    public static final int WINDOWDATA_FIELD_NUMBER = 2;
    private int bitField0_;
    private Internal.ProtobufList classname_;
    private long magicNumber_;
    private String package_;
    private long realToElapsedTimeOffsetNanos_;
    private Internal.ProtobufList windowData_;

    /* renamed from: com.android.app.viewcapture.data.ExportedData$1, reason: invalid class name */
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
            super(ExportedData.DEFAULT_INSTANCE);
        }
    }

    public enum MagicNumber implements Internal.EnumLite {
        INVALID(0),
        MAGIC_NUMBER_L(1703961976),
        MAGIC_NUMBER_H(1751482995);

        private final int value;

        static {
            new Internal.EnumLiteMap() { // from class: com.android.app.viewcapture.data.ExportedData.MagicNumber.1
                @Override // com.google.protobuf.Internal.EnumLiteMap
                public final Internal.EnumLite findValueByNumber(int i) {
                    if (i == 0) {
                        return MagicNumber.INVALID;
                    }
                    if (i == 1703961976) {
                        return MagicNumber.MAGIC_NUMBER_L;
                    }
                    if (i == 1751482995) {
                        return MagicNumber.MAGIC_NUMBER_H;
                    }
                    MagicNumber magicNumber = MagicNumber.INVALID;
                    return null;
                }
            };
        }

        MagicNumber(int i) {
            this.value = i;
        }

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }
    }

    static {
        ExportedData exportedData = new ExportedData();
        DEFAULT_INSTANCE = exportedData;
        GeneratedMessageLite.registerDefaultInstance(ExportedData.class, exportedData);
    }

    private ExportedData() {
        ProtobufArrayList protobufArrayList = ProtobufArrayList.EMPTY_LIST;
        this.windowData_ = protobufArrayList;
        this.package_ = "";
        this.classname_ = protobufArrayList;
    }

    public static void access$100(ExportedData exportedData, long j) {
        exportedData.bitField0_ |= 1;
        exportedData.magicNumber_ = j;
    }

    public static void access$1400(ExportedData exportedData, Iterable iterable) {
        Internal.ProtobufList protobufList = exportedData.classname_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            exportedData.classname_ = GeneratedMessageLite.mutableCopy(protobufList);
        }
        AbstractMessageLite.addAll(iterable, exportedData.classname_);
    }

    public static void access$1700(ExportedData exportedData, long j) {
        exportedData.bitField0_ |= 4;
        exportedData.realToElapsedTimeOffsetNanos_ = j;
    }

    public static void access$600(ExportedData exportedData, Iterable iterable) {
        Internal.ProtobufList protobufList = exportedData.windowData_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            exportedData.windowData_ = GeneratedMessageLite.mutableCopy(protobufList);
        }
        AbstractMessageLite.addAll(iterable, exportedData.windowData_);
    }

    public static void access$900(ExportedData exportedData, String str) {
        exportedData.getClass();
        str.getClass();
        exportedData.bitField0_ |= 2;
        exportedData.package_ = str;
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
                return new ExportedData();
            case 2:
                return new Builder(anonymousClass1);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001စ\u0000\u0002\u001b\u0003ဈ\u0001\u0004\u001a\u0005စ\u0002", new Object[]{"bitField0_", "magicNumber_", "windowData_", WindowData.class, "package_", "classname_", "realToElapsedTimeOffsetNanos_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser != null) {
                    return parser;
                }
                synchronized (ExportedData.class) {
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
