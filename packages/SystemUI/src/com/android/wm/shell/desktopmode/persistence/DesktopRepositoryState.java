package com.android.wm.shell.desktopmode.persistence;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RawMessageInfo;
import com.google.protobuf.WireFormat$FieldType;
import java.util.Collections;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopRepositoryState extends GeneratedMessageLite implements MessageLiteOrBuilder {
    private static final DesktopRepositoryState DEFAULT_INSTANCE;
    public static final int DESKTOP_FIELD_NUMBER = 1;
    private static volatile Parser PARSER;
    private MapFieldLite<Integer, Desktop> desktop_ = MapFieldLite.EMPTY_MAP_FIELD;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState$1, reason: invalid class name */
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
        public /* synthetic */ Builder(int i) {
            this();
        }

        private Builder() {
            super(DesktopRepositoryState.DEFAULT_INSTANCE);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DesktopDefaultEntryHolder {
        public static final MapEntryLite defaultEntry = MapEntryLite.newDefaultInstance(WireFormat$FieldType.INT32, 0, WireFormat$FieldType.MESSAGE, Desktop.getDefaultInstance());

        private DesktopDefaultEntryHolder() {
        }
    }

    /* renamed from: -$$Nest$mgetMutableDesktopMap, reason: not valid java name */
    public static MapFieldLite m3242$$Nest$mgetMutableDesktopMap(DesktopRepositoryState desktopRepositoryState) {
        if (!desktopRepositoryState.desktop_.isMutable()) {
            desktopRepositoryState.desktop_ = desktopRepositoryState.desktop_.mutableCopy();
        }
        return desktopRepositoryState.desktop_;
    }

    static {
        DesktopRepositoryState desktopRepositoryState = new DesktopRepositoryState();
        DEFAULT_INSTANCE = desktopRepositoryState;
        GeneratedMessageLite.registerDefaultInstance(DesktopRepositoryState.class, desktopRepositoryState);
    }

    private DesktopRepositoryState() {
    }

    public static DesktopRepositoryState getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        Parser parser;
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
            case 1:
                return new DesktopRepositoryState();
            case 2:
                return new Builder(0);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u00012", new Object[]{"desktop_", DesktopDefaultEntryHolder.defaultEntry});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser2 = PARSER;
                if (parser2 != null) {
                    return parser2;
                }
                synchronized (DesktopRepositoryState.class) {
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

    public final Map getDesktopMap() {
        return Collections.unmodifiableMap(this.desktop_);
    }

    public final Desktop getDesktopOrDefault(int i, Desktop desktop) {
        MapFieldLite<Integer, Desktop> mapFieldLite = this.desktop_;
        return mapFieldLite.containsKey(Integer.valueOf(i)) ? mapFieldLite.get(Integer.valueOf(i)) : desktop;
    }

    public final Desktop getDesktopOrThrow(int i) {
        MapFieldLite<Integer, Desktop> mapFieldLite = this.desktop_;
        if (mapFieldLite.containsKey(Integer.valueOf(i))) {
            return mapFieldLite.get(Integer.valueOf(i));
        }
        throw new IllegalArgumentException();
    }
}
