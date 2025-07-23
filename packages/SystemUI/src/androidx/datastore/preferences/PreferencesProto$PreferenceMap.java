package androidx.datastore.preferences;

import androidx.datastore.preferences.protobuf.CodedInputStream;
import androidx.datastore.preferences.protobuf.ExtensionRegistryLite;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException;
import androidx.datastore.preferences.protobuf.MapEntryLite;
import androidx.datastore.preferences.protobuf.MapFieldLite;
import androidx.datastore.preferences.protobuf.MessageLiteOrBuilder;
import androidx.datastore.preferences.protobuf.Parser;
import androidx.datastore.preferences.protobuf.RawMessageInfo;
import androidx.datastore.preferences.protobuf.UninitializedMessageException;
import androidx.datastore.preferences.protobuf.WireFormat$FieldType;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PreferencesProto$PreferenceMap extends GeneratedMessageLite implements MessageLiteOrBuilder {
    private static final PreferencesProto$PreferenceMap DEFAULT_INSTANCE;
    private static volatile Parser PARSER = null;
    public static final int PREFERENCES_FIELD_NUMBER = 1;
    private MapFieldLite<String, PreferencesProto$Value> preferences_ = MapFieldLite.EMPTY_MAP_FIELD;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder extends GeneratedMessageLite.Builder {
        public /* synthetic */ Builder(PreferencesProto$1 preferencesProto$1) {
            this();
        }

        private Builder() {
            super(PreferencesProto$PreferenceMap.DEFAULT_INSTANCE);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PreferencesDefaultEntryHolder {
        public static final MapEntryLite defaultEntry = MapEntryLite.newDefaultInstance(WireFormat$FieldType.STRING, WireFormat$FieldType.MESSAGE, PreferencesProto$Value.getDefaultInstance());

        private PreferencesDefaultEntryHolder() {
        }
    }

    static {
        PreferencesProto$PreferenceMap preferencesProto$PreferenceMap = new PreferencesProto$PreferenceMap();
        DEFAULT_INSTANCE = preferencesProto$PreferenceMap;
        GeneratedMessageLite.registerDefaultInstance(PreferencesProto$PreferenceMap.class, preferencesProto$PreferenceMap);
    }

    private PreferencesProto$PreferenceMap() {
    }

    public static MapFieldLite access$100(PreferencesProto$PreferenceMap preferencesProto$PreferenceMap) {
        if (!preferencesProto$PreferenceMap.preferences_.isMutable()) {
            preferencesProto$PreferenceMap.preferences_ = preferencesProto$PreferenceMap.preferences_.mutableCopy();
        }
        return preferencesProto$PreferenceMap.preferences_;
    }

    public static Builder newBuilder() {
        PreferencesProto$PreferenceMap preferencesProto$PreferenceMap = DEFAULT_INSTANCE;
        preferencesProto$PreferenceMap.getClass();
        return (Builder) ((GeneratedMessageLite.Builder) preferencesProto$PreferenceMap.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_BUILDER));
    }

    public static PreferencesProto$PreferenceMap parseFrom(InputStream inputStream) {
        CodedInputStream streamDecoder;
        PreferencesProto$PreferenceMap preferencesProto$PreferenceMap = DEFAULT_INSTANCE;
        if (inputStream == null) {
            byte[] bArr = Internal.EMPTY_BYTE_ARRAY;
            streamDecoder = CodedInputStream.newInstance(bArr, 0, bArr.length, false);
        } else {
            streamDecoder = new CodedInputStream.StreamDecoder(inputStream, 4096);
        }
        GeneratedMessageLite parsePartialFrom = GeneratedMessageLite.parsePartialFrom(preferencesProto$PreferenceMap, streamDecoder, ExtensionRegistryLite.getEmptyRegistry());
        if (GeneratedMessageLite.isInitialized(parsePartialFrom, true)) {
            return (PreferencesProto$PreferenceMap) parsePartialFrom;
        }
        InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(new UninitializedMessageException(parsePartialFrom).getMessage());
        invalidProtocolBufferException.setUnfinishedMessage(parsePartialFrom);
        throw invalidProtocolBufferException;
    }

    @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        Parser parser;
        int i = PreferencesProto$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()];
        PreferencesProto$1 preferencesProto$1 = null;
        switch (i) {
            case 1:
                return new PreferencesProto$PreferenceMap();
            case 2:
                return new Builder(preferencesProto$1);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u00012", new Object[]{"preferences_", PreferencesDefaultEntryHolder.defaultEntry});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser2 = PARSER;
                if (parser2 != null) {
                    return parser2;
                }
                synchronized (PreferencesProto$PreferenceMap.class) {
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

    public final Map getPreferencesMap() {
        return Collections.unmodifiableMap(this.preferences_);
    }
}
