package android.media;

import android.util.Log;
import android.util.Pair;
import java.lang.reflect.ParameterizedType;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class AudioMetadata {
    private static final int AUDIO_METADATA_OBJ_TYPE_BASEMAP = 6;
    private static final int AUDIO_METADATA_OBJ_TYPE_DOUBLE = 4;
    private static final int AUDIO_METADATA_OBJ_TYPE_FLOAT = 3;
    private static final int AUDIO_METADATA_OBJ_TYPE_INT = 1;
    private static final int AUDIO_METADATA_OBJ_TYPE_LONG = 2;
    private static final int AUDIO_METADATA_OBJ_TYPE_NONE = 0;
    private static final int AUDIO_METADATA_OBJ_TYPE_STRING = 5;
    private static final Map<Integer, DataPackage<?>> DATA_PACKAGES;
    private static final ObjectPackage OBJECT_PACKAGE;
    private static final String TAG = "AudioMetadata";
    private static final Map<Class, Integer> AUDIO_METADATA_OBJ_TYPES = Map.of(Integer.class, 1, Long.class, 2, Float.class, 3, Double.class, 4, String.class, 5, BaseMap.class, 6);
    private static final Charset AUDIO_METADATA_CHARSET = StandardCharsets.UTF_8;

    public interface Key<T> {
        String getName();

        Class<T> getValueClass();
    }

    public static AudioMetadataMap createMap() {
        return new BaseMap();
    }

    public static class Format {
        public static final Key<Integer> KEY_BIT_RATE = AudioMetadata.createKey(MediaFormat.KEY_BIT_RATE, Integer.class);
        public static final Key<Integer> KEY_CHANNEL_MASK = AudioMetadata.createKey(MediaFormat.KEY_CHANNEL_MASK, Integer.class);
        public static final Key<String> KEY_MIME = AudioMetadata.createKey("mime", String.class);
        public static final Key<Integer> KEY_SAMPLE_RATE = AudioMetadata.createKey(MediaFormat.KEY_SAMPLE_RATE, Integer.class);
        public static final Key<Integer> KEY_BIT_WIDTH = AudioMetadata.createKey("bit-width", Integer.class);
        public static final Key<Boolean> KEY_ATMOS_PRESENT = AudioMetadata.createKey("atmos-present", Boolean.class);
        public static final Key<Integer> KEY_HAS_ATMOS = AudioMetadata.createKey("has-atmos", Integer.class);
        public static final Key<Integer> KEY_AUDIO_ENCODING = AudioMetadata.createKey("audio-encoding", Integer.class);
        public static final Key<Integer> KEY_PRESENTATION_ID = AudioMetadata.createKey("presentation-id", Integer.class);
        public static final Key<Integer> KEY_PROGRAM_ID = AudioMetadata.createKey("program-id", Integer.class);
        public static final Key<Integer> KEY_PRESENTATION_CONTENT_CLASSIFIER = AudioMetadata.createKey("presentation-content-classifier", Integer.class);
        public static final Key<String> KEY_PRESENTATION_LANGUAGE = AudioMetadata.createKey("presentation-language", String.class);

        private Format() {
        }
    }

    public static <T> Key<T> createKey(String str, Class<T> cls) {
        return new Key<T>(str, cls) { // from class: android.media.AudioMetadata.1
            private final String mName;
            private final Class<T> mType;
            final /* synthetic */ String val$name;
            final /* synthetic */ Class val$type;

            {
                this.val$name = str;
                this.val$type = cls;
                this.mName = str;
                this.mType = cls;
            }

            @Override // android.media.AudioMetadata.Key
            public String getName() {
                return this.mName;
            }

            @Override // android.media.AudioMetadata.Key
            public Class<T> getValueClass() {
                return this.mType;
            }

            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Key)) {
                    return false;
                }
                Key key = (Key) obj;
                return this.mName.equals(key.getName()) && this.mType.equals(key.getValueClass());
            }

            public int hashCode() {
                return Objects.hash(this.mName, this.mType);
            }
        };
    }

    public static class BaseMap implements AudioMetadataMap {
        private final HashMap<Pair<String, Class<?>>, Pair<Key<?>, Object>> mHashMap = new HashMap<>();

        @Override // android.media.AudioMetadataReadMap
        public <T> boolean containsKey(Key<T> key) {
            return this.mHashMap.get(pairFromKey(key)) != null;
        }

        @Override // android.media.AudioMetadataReadMap
        public AudioMetadataMap dup() {
            BaseMap baseMap = new BaseMap();
            baseMap.mHashMap.putAll(this.mHashMap);
            return baseMap;
        }

        @Override // android.media.AudioMetadataReadMap
        public <T> T get(Key<T> key) {
            return (T) getValueFromValuePair(this.mHashMap.get(pairFromKey(key)));
        }

        @Override // android.media.AudioMetadataReadMap
        public Set<Key<?>> keySet() {
            HashSet hashSet = new HashSet();
            Iterator<Pair<Key<?>, Object>> it = this.mHashMap.values().iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().first);
            }
            return hashSet;
        }

        @Override // android.media.AudioMetadataMap
        public <T> T remove(Key<T> key) {
            return (T) getValueFromValuePair(this.mHashMap.remove(pairFromKey(key)));
        }

        @Override // android.media.AudioMetadataMap
        public <T> T set(Key<T> key, T t) {
            Objects.requireNonNull(t);
            return (T) getValueFromValuePair(this.mHashMap.put(pairFromKey(key), new Pair<>(key, t)));
        }

        @Override // android.media.AudioMetadataReadMap
        public int size() {
            return this.mHashMap.size();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof BaseMap) {
                return this.mHashMap.equals(((BaseMap) obj).mHashMap);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mHashMap);
        }

        private static <T> Pair<String, Class<?>> pairFromKey(Key<T> key) {
            Objects.requireNonNull(key);
            return new Pair<>(key.getName(), key.getValueClass());
        }

        private static Object getValueFromValuePair(Pair<Key<?>, Object> pair) {
            if (pair == null) {
                return null;
            }
            return pair.second;
        }
    }

    static {
        DATA_PACKAGES = Map.of(1, new DataPackage<Integer>() { // from class: android.media.AudioMetadata.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.media.AudioMetadata.DataPackage
            public Integer unpack(ByteBuffer byteBuffer) {
                return Integer.valueOf(byteBuffer.getInt());
            }

            @Override // android.media.AudioMetadata.DataPackage
            public boolean pack(AutoGrowByteBuffer autoGrowByteBuffer, Integer num) {
                autoGrowByteBuffer.putInt(num.intValue());
                return true;
            }
        }, 2, new DataPackage<Long>() { // from class: android.media.AudioMetadata.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.media.AudioMetadata.DataPackage
            public Long unpack(ByteBuffer byteBuffer) {
                return Long.valueOf(byteBuffer.getLong());
            }

            @Override // android.media.AudioMetadata.DataPackage
            public boolean pack(AutoGrowByteBuffer autoGrowByteBuffer, Long l) {
                autoGrowByteBuffer.putLong(l.longValue());
                return true;
            }
        }, 3, new DataPackage<Float>() { // from class: android.media.AudioMetadata.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.media.AudioMetadata.DataPackage
            public Float unpack(ByteBuffer byteBuffer) {
                return Float.valueOf(byteBuffer.getFloat());
            }

            @Override // android.media.AudioMetadata.DataPackage
            public boolean pack(AutoGrowByteBuffer autoGrowByteBuffer, Float f) {
                autoGrowByteBuffer.putFloat(f.floatValue());
                return true;
            }
        }, 4, new DataPackage<Double>() { // from class: android.media.AudioMetadata.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.media.AudioMetadata.DataPackage
            public Double unpack(ByteBuffer byteBuffer) {
                return Double.valueOf(byteBuffer.getDouble());
            }

            @Override // android.media.AudioMetadata.DataPackage
            public boolean pack(AutoGrowByteBuffer autoGrowByteBuffer, Double d) {
                autoGrowByteBuffer.putDouble(d.doubleValue());
                return true;
            }
        }, 5, new DataPackage<String>() { // from class: android.media.AudioMetadata.6
            @Override // android.media.AudioMetadata.DataPackage
            public String unpack(ByteBuffer byteBuffer) {
                int i = byteBuffer.getInt();
                if (byteBuffer.position() + i > byteBuffer.limit()) {
                    return null;
                }
                byte[] bArr = new byte[i];
                byteBuffer.get(bArr);
                return new String(bArr, AudioMetadata.AUDIO_METADATA_CHARSET);
            }

            @Override // android.media.AudioMetadata.DataPackage
            public boolean pack(AutoGrowByteBuffer autoGrowByteBuffer, String str) {
                byte[] bytes = str.getBytes(AudioMetadata.AUDIO_METADATA_CHARSET);
                autoGrowByteBuffer.putInt(bytes.length);
                autoGrowByteBuffer.put(bytes);
                return true;
            }
        }, 6, new BaseMapPackage());
        OBJECT_PACKAGE = new ObjectPackage();
    }

    private static class AutoGrowByteBuffer {
        private static final int DOUBLE_BYTE_COUNT = 8;
        private static final int FLOAT_BYTE_COUNT = 4;
        private static final int INTEGER_BYTE_COUNT = 4;
        private static final int LONG_BYTE_COUNT = 8;
        private ByteBuffer mBuffer;

        AutoGrowByteBuffer() {
            this(1024);
        }

        AutoGrowByteBuffer(int i) {
            this.mBuffer = ByteBuffer.allocateDirect(i);
        }

        public ByteBuffer getRawByteBuffer() {
            int limit = this.mBuffer.limit();
            int position = this.mBuffer.position();
            this.mBuffer.limit(position);
            this.mBuffer.position(0);
            ByteBuffer slice = this.mBuffer.slice();
            this.mBuffer.limit(limit);
            this.mBuffer.position(position);
            return slice;
        }

        public ByteOrder order() {
            return this.mBuffer.order();
        }

        public int position() {
            return this.mBuffer.position();
        }

        public AutoGrowByteBuffer position(int i) {
            this.mBuffer.position(i);
            return this;
        }

        public AutoGrowByteBuffer order(ByteOrder byteOrder) {
            this.mBuffer.order(byteOrder);
            return this;
        }

        public AutoGrowByteBuffer putInt(int i) {
            ensureCapacity(4);
            this.mBuffer.putInt(i);
            return this;
        }

        public AutoGrowByteBuffer putLong(long j) {
            ensureCapacity(8);
            this.mBuffer.putLong(j);
            return this;
        }

        public AutoGrowByteBuffer putFloat(float f) {
            ensureCapacity(4);
            this.mBuffer.putFloat(f);
            return this;
        }

        public AutoGrowByteBuffer putDouble(double d) {
            ensureCapacity(8);
            this.mBuffer.putDouble(d);
            return this;
        }

        public AutoGrowByteBuffer put(byte[] bArr) {
            ensureCapacity(bArr.length);
            this.mBuffer.put(bArr);
            return this;
        }

        private void ensureCapacity(int i) {
            if (this.mBuffer.remaining() < i) {
                int position = this.mBuffer.position() + i;
                if (position > 1073741823) {
                    throw new IllegalStateException("Item memory requirements too large: " + position);
                }
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(position << 1);
                allocateDirect.order(this.mBuffer.order());
                this.mBuffer.flip();
                allocateDirect.put(this.mBuffer);
                this.mBuffer = allocateDirect;
            }
        }
    }

    private interface DataPackage<T> {
        boolean pack(AutoGrowByteBuffer autoGrowByteBuffer, T t);

        T unpack(ByteBuffer byteBuffer);

        default Class getMyType() {
            return (Class) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        }
    }

    private static class ObjectPackage implements DataPackage<Pair<Class, Object>> {
        private ObjectPackage() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.media.AudioMetadata.DataPackage
        public Pair<Class, Object> unpack(ByteBuffer byteBuffer) {
            int i = byteBuffer.getInt();
            DataPackage dataPackage = (DataPackage) AudioMetadata.DATA_PACKAGES.get(Integer.valueOf(i));
            if (dataPackage == null) {
                Log.e(AudioMetadata.TAG, "Cannot find DataPackage for type:" + i);
                return null;
            }
            int i2 = byteBuffer.getInt();
            int position = byteBuffer.position();
            Object unpack = dataPackage.unpack(byteBuffer);
            if (byteBuffer.position() - position != i2) {
                Log.e(AudioMetadata.TAG, "Broken data package");
                return null;
            }
            return new Pair<>(dataPackage.getMyType(), unpack);
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(AutoGrowByteBuffer autoGrowByteBuffer, Pair<Class, Object> pair) {
            Integer num = (Integer) AudioMetadata.AUDIO_METADATA_OBJ_TYPES.get(pair.first);
            if (num == null) {
                Log.e(AudioMetadata.TAG, "Cannot find data type for " + pair.first);
                return false;
            }
            DataPackage dataPackage = (DataPackage) AudioMetadata.DATA_PACKAGES.get(num);
            if (dataPackage == null) {
                Log.e(AudioMetadata.TAG, "Cannot find DataPackage for type:" + num);
                return false;
            }
            autoGrowByteBuffer.putInt(num.intValue());
            int position = autoGrowByteBuffer.position();
            autoGrowByteBuffer.putInt(0);
            int position2 = autoGrowByteBuffer.position();
            if (!dataPackage.pack(autoGrowByteBuffer, pair.second)) {
                Log.i(AudioMetadata.TAG, "Failed to pack object: " + pair.second);
                return false;
            }
            int position3 = autoGrowByteBuffer.position();
            autoGrowByteBuffer.position(position);
            autoGrowByteBuffer.putInt(position3 - position2);
            autoGrowByteBuffer.position(position3);
            return true;
        }
    }

    private static class BaseMapPackage implements DataPackage<BaseMap> {
        private BaseMapPackage() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.media.AudioMetadata.DataPackage
        public BaseMap unpack(ByteBuffer byteBuffer) {
            BaseMap baseMap = new BaseMap();
            int i = byteBuffer.getInt();
            DataPackage dataPackage = (DataPackage) AudioMetadata.DATA_PACKAGES.get(5);
            if (dataPackage == null) {
                Log.e(AudioMetadata.TAG, "Cannot find DataPackage for String");
                return null;
            }
            for (int i2 = 0; i2 < i; i2++) {
                String str = (String) dataPackage.unpack(byteBuffer);
                if (str == null) {
                    Log.e(AudioMetadata.TAG, "Failed to unpack key for map");
                    return null;
                }
                Pair<Class, Object> unpack = AudioMetadata.OBJECT_PACKAGE.unpack(byteBuffer);
                if (unpack == null) {
                    Log.e(AudioMetadata.TAG, "Failed to unpack value for map");
                    return null;
                }
                if (str.equals(Format.KEY_HAS_ATMOS.getName()) && unpack.first == Format.KEY_HAS_ATMOS.getValueClass()) {
                    baseMap.set(Format.KEY_ATMOS_PRESENT, Boolean.valueOf(((Integer) unpack.second).intValue() != 0));
                } else {
                    baseMap.set(AudioMetadata.createKey(str, unpack.first), unpack.first.cast(unpack.second));
                }
            }
            return baseMap;
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(AutoGrowByteBuffer autoGrowByteBuffer, BaseMap baseMap) {
            autoGrowByteBuffer.putInt(baseMap.size());
            DataPackage dataPackage = (DataPackage) AudioMetadata.DATA_PACKAGES.get(5);
            if (dataPackage == null) {
                Log.e(AudioMetadata.TAG, "Cannot find DataPackage for String");
                return false;
            }
            Iterator<Key<?>> it = baseMap.keySet().iterator();
            while (it.hasNext()) {
                Key<?> next = it.next();
                Object obj = baseMap.get(next);
                if (next == Format.KEY_ATMOS_PRESENT) {
                    next = Format.KEY_HAS_ATMOS;
                    obj = Integer.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
                }
                if (!dataPackage.pack(autoGrowByteBuffer, next.getName())) {
                    Log.i(AudioMetadata.TAG, "Failed to pack key: " + next.getName());
                    return false;
                }
                if (!AudioMetadata.OBJECT_PACKAGE.pack(autoGrowByteBuffer, new Pair<>(next.getValueClass(), obj))) {
                    Log.i(AudioMetadata.TAG, "Failed to pack value: " + baseMap.get(next));
                    return false;
                }
            }
            return true;
        }
    }

    public static BaseMap fromByteBuffer(ByteBuffer byteBuffer) {
        DataPackage<?> dataPackage = DATA_PACKAGES.get(6);
        if (dataPackage == null) {
            Log.e(TAG, "Cannot find DataPackage for BaseMap");
            return null;
        }
        try {
            return (BaseMap) dataPackage.unpack(byteBuffer);
        } catch (BufferUnderflowException unused) {
            Log.e(TAG, "No enough data to unpack");
            return null;
        }
    }

    public static ByteBuffer toByteBuffer(BaseMap baseMap, ByteOrder byteOrder) {
        DataPackage<?> dataPackage = DATA_PACKAGES.get(6);
        if (dataPackage == null) {
            Log.e(TAG, "Cannot find DataPackage for BaseMap");
            return null;
        }
        AutoGrowByteBuffer autoGrowByteBuffer = new AutoGrowByteBuffer();
        autoGrowByteBuffer.order(byteOrder);
        if (dataPackage.pack(autoGrowByteBuffer, baseMap)) {
            return autoGrowByteBuffer.getRawByteBuffer();
        }
        return null;
    }

    private AudioMetadata() {
    }
}
