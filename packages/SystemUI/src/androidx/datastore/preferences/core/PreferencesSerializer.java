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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PreferencesSerializer implements OkioSerializer {
    public static final PreferencesSerializer INSTANCE = new PreferencesSerializer();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final MutablePreferences readFrom(RealBufferedSource realBufferedSource) {
        byte[] bArr;
        PreferencesMapCompat.Companion companion = PreferencesMapCompat.Companion;
        RealBufferedSource$inputStream$1 realBufferedSource$inputStream$1 = new RealBufferedSource$inputStream$1(realBufferedSource);
        companion.getClass();
        try {
            PreferencesProto$PreferenceMap parseFrom = PreferencesProto$PreferenceMap.parseFrom(realBufferedSource$inputStream$1);
            MutablePreferences mutablePreferences = new MutablePreferences(null, false, 1, null);
            Preferences.Pair[] pairArr = (Preferences.Pair[]) Arrays.copyOf(new Preferences.Pair[0], 0);
            mutablePreferences.checkNotFrozen$datastore_preferences_core();
            for (Preferences.Pair pair : pairArr) {
                mutablePreferences.setUnchecked$datastore_preferences_core(pair.key, pair.value);
            }
            for (Map.Entry entry : parseFrom.getPreferencesMap().entrySet()) {
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

    public final Unit writeTo(Object obj, final RealBufferedSink realBufferedSink) {
        GeneratedMessageLite build;
        Map asMap = ((Preferences) obj).asMap();
        PreferencesProto$PreferenceMap.Builder newBuilder = PreferencesProto$PreferenceMap.newBuilder();
        for (Map.Entry entry : asMap.entrySet()) {
            Preferences.Key key = (Preferences.Key) entry.getKey();
            Object value = entry.getValue();
            String str = key.name;
            if (value instanceof Boolean) {
                PreferencesProto$Value.Builder newBuilder2 = PreferencesProto$Value.newBuilder();
                boolean booleanValue = ((Boolean) value).booleanValue();
                newBuilder2.copyOnWrite();
                PreferencesProto$Value.access$500((PreferencesProto$Value) newBuilder2.instance, booleanValue);
                build = newBuilder2.build();
            } else if (value instanceof Float) {
                PreferencesProto$Value.Builder newBuilder3 = PreferencesProto$Value.newBuilder();
                float floatValue = ((Number) value).floatValue();
                newBuilder3.copyOnWrite();
                PreferencesProto$Value.access$700((PreferencesProto$Value) newBuilder3.instance, floatValue);
                build = newBuilder3.build();
            } else if (value instanceof Double) {
                PreferencesProto$Value.Builder newBuilder4 = PreferencesProto$Value.newBuilder();
                double doubleValue = ((Number) value).doubleValue();
                newBuilder4.copyOnWrite();
                PreferencesProto$Value.access$1900((PreferencesProto$Value) newBuilder4.instance, doubleValue);
                build = newBuilder4.build();
            } else if (value instanceof Integer) {
                PreferencesProto$Value.Builder newBuilder5 = PreferencesProto$Value.newBuilder();
                int intValue = ((Number) value).intValue();
                newBuilder5.copyOnWrite();
                PreferencesProto$Value.access$900((PreferencesProto$Value) newBuilder5.instance, intValue);
                build = newBuilder5.build();
            } else if (value instanceof Long) {
                PreferencesProto$Value.Builder newBuilder6 = PreferencesProto$Value.newBuilder();
                long longValue = ((Number) value).longValue();
                newBuilder6.copyOnWrite();
                PreferencesProto$Value.access$1100((PreferencesProto$Value) newBuilder6.instance, longValue);
                build = newBuilder6.build();
            } else if (value instanceof String) {
                PreferencesProto$Value.Builder newBuilder7 = PreferencesProto$Value.newBuilder();
                newBuilder7.copyOnWrite();
                PreferencesProto$Value.access$1300((PreferencesProto$Value) newBuilder7.instance, (String) value);
                build = newBuilder7.build();
            } else if (value instanceof Set) {
                PreferencesProto$Value.Builder newBuilder8 = PreferencesProto$Value.newBuilder();
                PreferencesProto$StringSet.Builder newBuilder9 = PreferencesProto$StringSet.newBuilder();
                newBuilder9.copyOnWrite();
                PreferencesProto$StringSet.access$2700((PreferencesProto$StringSet) newBuilder9.instance, (Set) value);
                newBuilder8.copyOnWrite();
                PreferencesProto$Value.access$1600((PreferencesProto$Value) newBuilder8.instance, (PreferencesProto$StringSet) newBuilder9.build());
                build = newBuilder8.build();
            } else {
                if (!(value instanceof byte[])) {
                    throw new IllegalStateException("PreferencesSerializer does not support type: ".concat(value.getClass().getName()));
                }
                PreferencesProto$Value.Builder newBuilder10 = PreferencesProto$Value.newBuilder();
                byte[] bArr = (byte[]) value;
                ByteString byteString = ByteString.EMPTY;
                ByteString copyFrom = ByteString.copyFrom(0, bArr.length, bArr);
                newBuilder10.copyOnWrite();
                PreferencesProto$Value.access$2100((PreferencesProto$Value) newBuilder10.instance, copyFrom);
                build = newBuilder10.build();
            }
            newBuilder.getClass();
            str.getClass();
            newBuilder.copyOnWrite();
            PreferencesProto$PreferenceMap.access$100((PreferencesProto$PreferenceMap) newBuilder.instance).put(str, (PreferencesProto$Value) build);
        }
        PreferencesProto$PreferenceMap preferencesProto$PreferenceMap = (PreferencesProto$PreferenceMap) newBuilder.build();
        OutputStream outputStream = new OutputStream() { // from class: okio.RealBufferedSink$outputStream$1
            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                RealBufferedSink.this.close();
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public final void flush() {
                RealBufferedSink realBufferedSink2 = RealBufferedSink.this;
                if (realBufferedSink2.closed) {
                    return;
                }
                realBufferedSink2.flush();
            }

            public final String toString() {
                return RealBufferedSink.this + ".outputStream()";
            }

            @Override // java.io.OutputStream
            public final void write(int i) {
                RealBufferedSink realBufferedSink2 = RealBufferedSink.this;
                if (realBufferedSink2.closed) {
                    throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
                }
                realBufferedSink2.bufferField.writeByte((byte) i);
                RealBufferedSink.this.emitCompleteSegments();
            }

            @Override // java.io.OutputStream
            public final void write(byte[] bArr2, int i, int i2) {
                RealBufferedSink realBufferedSink2 = RealBufferedSink.this;
                if (!realBufferedSink2.closed) {
                    realBufferedSink2.bufferField.write(bArr2, i, i2);
                    RealBufferedSink.this.emitCompleteSegments();
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
