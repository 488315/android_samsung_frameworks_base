package com.android.wm.shell.desktopmode.education.data;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.WireFormat$FieldType;
import java.io.InputStream;

/* loaded from: classes3.dex */
public final class WindowingEducationProto extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int APP_HANDLE_EDUCATION_FIELD_NUMBER = 3;
    public static final int APP_HANDLE_HINT_USED_TIMESTAMP_MILLIS_FIELD_NUMBER = 6;
    public static final int APP_HANDLE_HINT_VIEWED_TIMESTAMP_MILLIS_FIELD_NUMBER = 5;
    public static final int APP_TO_WEB_EDUCATION_FIELD_NUMBER = 4;
    private static final WindowingEducationProto DEFAULT_INSTANCE;
    public static final int EDUCATION_VIEWED_TIMESTAMP_MILLIS_FIELD_NUMBER = 1;
    public static final int ENTER_DESKTOP_MODE_HINT_VIEWED_TIMESTAMP_MILLIS_FIELD_NUMBER = 7;
    public static final int EXIT_DESKTOP_MODE_HINT_VIEWED_TIMESTAMP_MILLIS_FIELD_NUMBER = 8;
    public static final int FEATURE_USED_TIMESTAMP_MILLIS_FIELD_NUMBER = 2;
    private static volatile Parser PARSER;
    private long appHandleHintUsedTimestampMillis_;
    private long appHandleHintViewedTimestampMillis_;
    private int bitField0_;
    private int educationDataCase_ = 0;
    private Object educationData_;
    private long educationViewedTimestampMillis_;
    private long enterDesktopModeHintViewedTimestampMillis_;
    private long exitDesktopModeHintViewedTimestampMillis_;
    private long featureUsedTimestampMillis_;

    /* renamed from: com.android.wm.shell.desktopmode.education.data.WindowingEducationProto$1, reason: invalid class name */
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

    public final class AppHandleEducation extends GeneratedMessageLite implements MessageLiteOrBuilder {
        public static final int APP_USAGE_STATS_FIELD_NUMBER = 1;
        public static final int APP_USAGE_STATS_LAST_UPDATE_TIMESTAMP_MILLIS_FIELD_NUMBER = 2;
        private static final AppHandleEducation DEFAULT_INSTANCE;
        private static volatile Parser PARSER;
        private long appUsageStatsLastUpdateTimestampMillis_;
        private MapFieldLite<String, Integer> appUsageStats_ = MapFieldLite.EMPTY_MAP_FIELD;
        private int bitField0_;

        public final class AppUsageStatsDefaultEntryHolder {
            public static final MapEntryLite defaultEntry = MapEntryLite.newDefaultInstance(WireFormat$FieldType.STRING, "", WireFormat$FieldType.INT32, 0);

            private AppUsageStatsDefaultEntryHolder() {
            }
        }

        public final class Builder extends GeneratedMessageLite.Builder {
            public /* synthetic */ Builder(int i) {
                this();
            }

            private Builder() {
                super(AppHandleEducation.DEFAULT_INSTANCE);
            }
        }

        static {
            AppHandleEducation appHandleEducation = new AppHandleEducation();
            DEFAULT_INSTANCE = appHandleEducation;
            GeneratedMessageLite.registerDefaultInstance(AppHandleEducation.class, appHandleEducation);
        }

        private AppHandleEducation() {
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new AppHandleEducation();
                case 2:
                    return new Builder(0);
                case 3:
                    return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0001\u0000\u0000\u00012\u0002ဂ\u0000", new Object[]{"bitField0_", "appUsageStats_", AppUsageStatsDefaultEntryHolder.defaultEntry, "appUsageStatsLastUpdateTimestampMillis_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (AppHandleEducation.class) {
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

    public final class AppToWebEducation extends GeneratedMessageLite implements MessageLiteOrBuilder {
        private static final AppToWebEducation DEFAULT_INSTANCE;
        public static final int EDUCATION_SHOWN_COUNT_FIELD_NUMBER = 1;
        private static volatile Parser PARSER;
        private int bitField0_;
        private long educationShownCount_;

        public final class Builder extends GeneratedMessageLite.Builder {
            public /* synthetic */ Builder(int i) {
                this();
            }

            private Builder() {
                super(AppToWebEducation.DEFAULT_INSTANCE);
            }
        }

        static {
            AppToWebEducation appToWebEducation = new AppToWebEducation();
            DEFAULT_INSTANCE = appToWebEducation;
            GeneratedMessageLite.registerDefaultInstance(AppToWebEducation.class, appToWebEducation);
        }

        private AppToWebEducation() {
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new AppToWebEducation();
                case 2:
                    return new Builder(0);
                case 3:
                    return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဂ\u0000", new Object[]{"bitField0_", "educationShownCount_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (AppToWebEducation.class) {
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

    public final class Builder extends GeneratedMessageLite.Builder {
        public /* synthetic */ Builder(int i) {
            this();
        }

        private Builder() {
            super(WindowingEducationProto.DEFAULT_INSTANCE);
        }
    }

    static {
        WindowingEducationProto windowingEducationProto = new WindowingEducationProto();
        DEFAULT_INSTANCE = windowingEducationProto;
        GeneratedMessageLite.registerDefaultInstance(WindowingEducationProto.class, windowingEducationProto);
    }

    private WindowingEducationProto() {
    }

    public static WindowingEducationProto getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static WindowingEducationProto parseFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        GeneratedMessageLite partialFrom = GeneratedMessageLite.parsePartialFrom(DEFAULT_INSTANCE, new CodedInputStream.StreamDecoder(inputStream, 4096), ExtensionRegistryLite.getEmptyRegistry());
        if (GeneratedMessageLite.isInitialized(partialFrom, true)) {
            return (WindowingEducationProto) partialFrom;
        }
        InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(new UninitializedMessageException(partialFrom).getMessage());
        invalidProtocolBufferException.setUnfinishedMessage(partialFrom);
        throw invalidProtocolBufferException;
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        Parser defaultInstanceBasedParser;
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
            case 1:
                return new WindowingEducationProto();
            case 2:
                return new Builder(0);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\b\u0001\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဂ\u0000\u0002ဂ\u0001\u0003ြ\u0000\u0004ြ\u0000\u0005ဂ\u0002\u0006ဂ\u0003\u0007ဂ\u0004\bဂ\u0005", new Object[]{"educationData_", "educationDataCase_", "bitField0_", "educationViewedTimestampMillis_", "featureUsedTimestampMillis_", AppHandleEducation.class, AppToWebEducation.class, "appHandleHintViewedTimestampMillis_", "appHandleHintUsedTimestampMillis_", "enterDesktopModeHintViewedTimestampMillis_", "exitDesktopModeHintViewedTimestampMillis_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser != null) {
                    return parser;
                }
                synchronized (WindowingEducationProto.class) {
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
