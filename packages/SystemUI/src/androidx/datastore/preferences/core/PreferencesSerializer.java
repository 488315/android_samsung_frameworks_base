package androidx.datastore.preferences.core;

import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.okio.OkioSerializer;
import androidx.datastore.preferences.PreferencesMapCompat;
import androidx.datastore.preferences.PreferencesProto$PreferenceMap;
import androidx.datastore.preferences.PreferencesProto$StringSet;
import androidx.datastore.preferences.PreferencesProto$Value;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.protobuf.ByteString;
import androidx.datastore.preferences.protobuf.CodedOutputStream;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException;
import com.sec.ims.presence.ServiceTuple;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import okio.RealBufferedSink;
import okio.RealBufferedSource;
import okio.RealBufferedSource$inputStream$1;

/* loaded from: classes.dex */
public final class PreferencesSerializer implements OkioSerializer {
    public static final PreferencesSerializer INSTANCE = new PreferencesSerializer();

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PreferencesProto$Value.ValueCase.values().length];
            try {
                iArr[PreferencesProto$Value.ValueCase.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PreferencesProto$Value.ValueCase.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PreferencesProto$Value.ValueCase.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PreferencesProto$Value.ValueCase.INTEGER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[PreferencesProto$Value.ValueCase.LONG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[PreferencesProto$Value.ValueCase.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[PreferencesProto$Value.ValueCase.STRING_SET.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[PreferencesProto$Value.ValueCase.BYTES.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[PreferencesProto$Value.ValueCase.VALUE_NOT_SET.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private PreferencesSerializer() {
    }

    public final MutablePreferences readFrom(RealBufferedSource realBufferedSource) throws CorruptionException {
        byte[] bArr;
        PreferencesMapCompat.Companion companion = PreferencesMapCompat.Companion;
        RealBufferedSource$inputStream$1 realBufferedSource$inputStream$1 = new RealBufferedSource$inputStream$1(realBufferedSource);
        companion.getClass();
        try {
            PreferencesProto$PreferenceMap from = PreferencesProto$PreferenceMap.parseFrom(realBufferedSource$inputStream$1);
            MutablePreferences mutablePreferences = new MutablePreferences(null, false, 1, null);
            Preferences.Pair[] pairArr = (Preferences.Pair[]) Arrays.copyOf(new Preferences.Pair[0], 0);
            mutablePreferences.checkNotFrozen$datastore_preferences_core();
            for (Preferences.Pair pair : pairArr) {
                mutablePreferences.setUnchecked$datastore_preferences_core(pair.key, pair.value);
            }
            for (Map.Entry entry : from.getPreferencesMap().entrySet()) {
                String str = (String) entry.getKey();
                PreferencesProto$Value preferencesProto$Value = (PreferencesProto$Value) entry.getValue();
                str.getClass();
                preferencesProto$Value.getClass();
                INSTANCE.getClass();
                PreferencesProto$Value.ValueCase valueCase = preferencesProto$Value.getValueCase();
                switch (valueCase == null ? -1 : WhenMappings.$EnumSwitchMapping$0[valueCase.ordinal()]) {
                    case -1:
                        throw new CorruptionException("Value case is null.", null, 2, null);
                    case 0:
                    default:
                        throw new NoWhenBranchMatchedException();
                    case 1:
                        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(str), Boolean.valueOf(preferencesProto$Value.getBoolean()));
                        break;
                    case 2:
                        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(str), Float.valueOf(preferencesProto$Value.getFloat()));
                        break;
                    case 3:
                        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(str), Double.valueOf(preferencesProto$Value.getDouble()));
                        break;
                    case 4:
                        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(str), Integer.valueOf(preferencesProto$Value.getInteger()));
                        break;
                    case 5:
                        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(str), Long.valueOf(preferencesProto$Value.getLong()));
                        break;
                    case 6:
                        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(str), preferencesProto$Value.getString());
                        break;
                    case 7:
                        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(str), CollectionsKt___CollectionsKt.toSet(preferencesProto$Value.getStringSet().getStringsList()));
                        break;
                    case 8:
                        Preferences.Key key = new Preferences.Key(str);
                        ByteString bytes = preferencesProto$Value.getBytes();
                        int size = bytes.size();
                        if (size == 0) {
                            bArr = Internal.EMPTY_BYTE_ARRAY;
                        } else {
                            byte[] bArr2 = new byte[size];
                            bytes.copyToInternal(size, bArr2);
                            bArr = bArr2;
                        }
                        mutablePreferences.setUnchecked$datastore_preferences_core(key, bArr);
                        break;
                    case 9:
                        throw new CorruptionException("Value not set.", null, 2, null);
                }
            }
            return new MutablePreferences(new LinkedHashMap(mutablePreferences.asMap()), true);
        } catch (InvalidProtocolBufferException e) {
            throw new CorruptionException("Unable to parse preferences proto.", e);
        }
    }

    public final Unit writeTo(Object obj, final RealBufferedSink realBufferedSink) throws IOException {
        GeneratedMessageLite generatedMessageLiteBuild;
        Map mapAsMap = ((Preferences) obj).asMap();
        PreferencesProto$PreferenceMap.Builder builderNewBuilder = PreferencesProto$PreferenceMap.newBuilder();
        for (Map.Entry entry : mapAsMap.entrySet()) {
            Preferences.Key key = (Preferences.Key) entry.getKey();
            Object value = entry.getValue();
            String str = key.name;
            if (value instanceof Boolean) {
                PreferencesProto$Value.Builder builderNewBuilder2 = PreferencesProto$Value.newBuilder();
                boolean zBooleanValue = ((Boolean) value).booleanValue();
                builderNewBuilder2.copyOnWrite();
                PreferencesProto$Value.access$500((PreferencesProto$Value) builderNewBuilder2.instance, zBooleanValue);
                generatedMessageLiteBuild = builderNewBuilder2.build();
            } else if (value instanceof Float) {
                PreferencesProto$Value.Builder builderNewBuilder3 = PreferencesProto$Value.newBuilder();
                float fFloatValue = ((Number) value).floatValue();
                builderNewBuilder3.copyOnWrite();
                PreferencesProto$Value.access$700((PreferencesProto$Value) builderNewBuilder3.instance, fFloatValue);
                generatedMessageLiteBuild = builderNewBuilder3.build();
            } else if (value instanceof Double) {
                PreferencesProto$Value.Builder builderNewBuilder4 = PreferencesProto$Value.newBuilder();
                double dDoubleValue = ((Number) value).doubleValue();
                builderNewBuilder4.copyOnWrite();
                PreferencesProto$Value.access$1900((PreferencesProto$Value) builderNewBuilder4.instance, dDoubleValue);
                generatedMessageLiteBuild = builderNewBuilder4.build();
            } else if (value instanceof Integer) {
                PreferencesProto$Value.Builder builderNewBuilder5 = PreferencesProto$Value.newBuilder();
                int iIntValue = ((Number) value).intValue();
                builderNewBuilder5.copyOnWrite();
                PreferencesProto$Value.access$900((PreferencesProto$Value) builderNewBuilder5.instance, iIntValue);
                generatedMessageLiteBuild = builderNewBuilder5.build();
            } else if (value instanceof Long) {
                PreferencesProto$Value.Builder builderNewBuilder6 = PreferencesProto$Value.newBuilder();
                long jLongValue = ((Number) value).longValue();
                builderNewBuilder6.copyOnWrite();
                PreferencesProto$Value.access$1100((PreferencesProto$Value) builderNewBuilder6.instance, jLongValue);
                generatedMessageLiteBuild = builderNewBuilder6.build();
            } else if (value instanceof String) {
                PreferencesProto$Value.Builder builderNewBuilder7 = PreferencesProto$Value.newBuilder();
                builderNewBuilder7.copyOnWrite();
                PreferencesProto$Value.access$1300((PreferencesProto$Value) builderNewBuilder7.instance, (String) value);
                generatedMessageLiteBuild = builderNewBuilder7.build();
            } else if (value instanceof Set) {
                PreferencesProto$Value.Builder builderNewBuilder8 = PreferencesProto$Value.newBuilder();
                PreferencesProto$StringSet.Builder builderNewBuilder9 = PreferencesProto$StringSet.newBuilder();
                builderNewBuilder9.copyOnWrite();
                PreferencesProto$StringSet.access$2700((PreferencesProto$StringSet) builderNewBuilder9.instance, (Set) value);
                builderNewBuilder8.copyOnWrite();
                PreferencesProto$Value.access$1600((PreferencesProto$Value) builderNewBuilder8.instance, (PreferencesProto$StringSet) builderNewBuilder9.build());
                generatedMessageLiteBuild = builderNewBuilder8.build();
            } else {
                if (!(value instanceof byte[])) {
                    throw new IllegalStateException("PreferencesSerializer does not support type: ".concat(value.getClass().getName()));
                }
                PreferencesProto$Value.Builder builderNewBuilder10 = PreferencesProto$Value.newBuilder();
                byte[] bArr = (byte[]) value;
                ByteString byteString = ByteString.EMPTY;
                ByteString byteStringCopyFrom = ByteString.copyFrom(0, bArr.length, bArr);
                builderNewBuilder10.copyOnWrite();
                PreferencesProto$Value.access$2100((PreferencesProto$Value) builderNewBuilder10.instance, byteStringCopyFrom);
                generatedMessageLiteBuild = builderNewBuilder10.build();
            }
            builderNewBuilder.getClass();
            str.getClass();
            builderNewBuilder.copyOnWrite();
            PreferencesProto$PreferenceMap.access$100((PreferencesProto$PreferenceMap) builderNewBuilder.instance).put(str, (PreferencesProto$Value) generatedMessageLiteBuild);
        }
        PreferencesProto$PreferenceMap preferencesProto$PreferenceMap = (PreferencesProto$PreferenceMap) builderNewBuilder.build();
        OutputStream outputStream = new OutputStream() { // from class: okio.RealBufferedSink$outputStream$1
            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public final void close() throws Throwable {
                realBufferedSink.close();
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public final void flush() {
                RealBufferedSink realBufferedSink2 = realBufferedSink;
                if (realBufferedSink2.closed) {
                    return;
                }
                realBufferedSink2.flush();
            }

            public final String toString() {
                return realBufferedSink + ".outputStream()";
            }

            @Override // java.io.OutputStream
            public final void write(int i) throws IOException {
                RealBufferedSink realBufferedSink2 = realBufferedSink;
                if (realBufferedSink2.closed) {
                    throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
                }
                realBufferedSink2.bufferField.writeByte((byte) i);
                realBufferedSink.emitCompleteSegments();
            }

            @Override // java.io.OutputStream
            public final void write(byte[] bArr2, int i, int i2) throws IOException {
                RealBufferedSink realBufferedSink2 = realBufferedSink;
                if (!realBufferedSink2.closed) {
                    realBufferedSink2.bufferField.write(bArr2, i, i2);
                    realBufferedSink.emitCompleteSegments();
                    return;
                }
                throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
        };
        int serializedSize = preferencesProto$PreferenceMap.getSerializedSize(null);
        Logger logger = CodedOutputStream.logger;
        if (serializedSize > 4096) {
            serializedSize = 4096;
        }
        CodedOutputStream.OutputStreamEncoder outputStreamEncoder = new CodedOutputStream.OutputStreamEncoder(outputStream, serializedSize);
        preferencesProto$PreferenceMap.writeTo(outputStreamEncoder);
        if (outputStreamEncoder.position > 0) {
            outputStreamEncoder.doFlush();
        }
        return Unit.INSTANCE;
    }
}
