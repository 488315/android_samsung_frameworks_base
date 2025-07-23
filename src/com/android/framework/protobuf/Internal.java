package com.android.framework.protobuf;

import com.android.internal.logging.nano.MetricsProto;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

/* loaded from: classes5.dex */
public final class Internal {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final ByteBuffer EMPTY_BYTE_BUFFER;
    public static final CodedInputStream EMPTY_CODED_INPUT_STREAM;
    static final Charset US_ASCII = Charset.forName("US-ASCII");
    static final Charset UTF_8 = Charset.forName("UTF-8");
    static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    public interface BooleanList extends ProtobufList<Boolean> {
        void addBoolean(boolean z);

        boolean getBoolean(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity, reason: merged with bridge method [inline-methods] */
        ProtobufList<Boolean> mutableCopyWithCapacity2(int i);

        boolean setBoolean(int i, boolean z);
    }

    public interface DoubleList extends ProtobufList<Double> {
        void addDouble(double d);

        double getDouble(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity */
        ProtobufList<Double> mutableCopyWithCapacity2(int i);

        double setDouble(int i, double d);
    }

    public interface EnumLite {
        int getNumber();
    }

    public interface EnumLiteMap<T extends EnumLite> {
        T findValueByNumber(int i);
    }

    public interface EnumVerifier {
        boolean isInRange(int i);
    }

    public interface FloatList extends ProtobufList<Float> {
        void addFloat(float f);

        float getFloat(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity */
        ProtobufList<Float> mutableCopyWithCapacity2(int i);

        float setFloat(int i, float f);
    }

    public interface IntList extends ProtobufList<Integer> {
        void addInt(int i);

        int getInt(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity */
        ProtobufList<Integer> mutableCopyWithCapacity2(int i);

        int setInt(int i, int i2);
    }

    public interface LongList extends ProtobufList<Long> {
        void addLong(long j);

        long getLong(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity */
        ProtobufList<Long> mutableCopyWithCapacity2(int i);

        long setLong(int i, long j);
    }

    public interface ProtobufList<E> extends List<E>, RandomAccess {
        boolean isModifiable();

        void makeImmutable();

        /* renamed from: mutableCopyWithCapacity */
        ProtobufList<E> mutableCopyWithCapacity2(int i);
    }

    public static int hashBoolean(boolean z) {
        return z ? MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT;
    }

    public static int hashLong(long j) {
        return (int) (j ^ (j >>> 32));
    }

    private Internal() {
    }

    static {
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        EMPTY_BYTE_BUFFER = ByteBuffer.wrap(bArr);
        EMPTY_CODED_INPUT_STREAM = CodedInputStream.newInstance(bArr);
    }

    static <T> T checkNotNull(T t) {
        t.getClass();
        return t;
    }

    static <T> T checkNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static String stringDefaultValue(String str) {
        return new String(str.getBytes(ISO_8859_1), UTF_8);
    }

    public static ByteString bytesDefaultValue(String str) {
        return ByteString.copyFrom(str.getBytes(ISO_8859_1));
    }

    public static byte[] byteArrayDefaultValue(String str) {
        return str.getBytes(ISO_8859_1);
    }

    public static ByteBuffer byteBufferDefaultValue(String str) {
        return ByteBuffer.wrap(byteArrayDefaultValue(str));
    }

    public static ByteBuffer copyByteBuffer(ByteBuffer byteBuffer) {
        ByteBuffer duplicate = byteBuffer.duplicate();
        duplicate.clear();
        ByteBuffer allocate = ByteBuffer.allocate(duplicate.capacity());
        allocate.put(duplicate);
        allocate.clear();
        return allocate;
    }

    public static boolean isValidUtf8(ByteString byteString) {
        return byteString.isValidUtf8();
    }

    public static boolean isValidUtf8(byte[] bArr) {
        return Utf8.isValidUtf8(bArr);
    }

    public static byte[] toByteArray(String str) {
        return str.getBytes(UTF_8);
    }

    public static String toStringUtf8(byte[] bArr) {
        return new String(bArr, UTF_8);
    }

    public static int hashEnum(EnumLite enumLite) {
        return enumLite.getNumber();
    }

    public static int hashEnumList(List<? extends EnumLite> list) {
        Iterator<? extends EnumLite> it = list.iterator();
        int i = 1;
        while (it.hasNext()) {
            i = (i * 31) + hashEnum(it.next());
        }
        return i;
    }

    public static boolean equals(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(List<byte[]> list) {
        Iterator<byte[]> it = list.iterator();
        int i = 1;
        while (it.hasNext()) {
            i = (i * 31) + hashCode(it.next());
        }
        return i;
    }

    public static int hashCode(byte[] bArr) {
        return hashCode(bArr, 0, bArr.length);
    }

    static int hashCode(byte[] bArr, int i, int i2) {
        int partialHash = partialHash(i2, bArr, i, i2);
        if (partialHash == 0) {
            return 1;
        }
        return partialHash;
    }

    static int partialHash(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    public static boolean equalsByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        if (byteBuffer.capacity() != byteBuffer2.capacity()) {
            return false;
        }
        return byteBuffer.duplicate().clear().equals(byteBuffer2.duplicate().clear());
    }

    public static boolean equalsByteBuffer(List<ByteBuffer> list, List<ByteBuffer> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!equalsByteBuffer(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static int hashCodeByteBuffer(List<ByteBuffer> list) {
        Iterator<ByteBuffer> it = list.iterator();
        int i = 1;
        while (it.hasNext()) {
            i = (i * 31) + hashCodeByteBuffer(it.next());
        }
        return i;
    }

    public static int hashCodeByteBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            int partialHash = partialHash(byteBuffer.capacity(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            if (partialHash == 0) {
                return 1;
            }
            return partialHash;
        }
        int capacity = byteBuffer.capacity() <= 4096 ? byteBuffer.capacity() : 4096;
        byte[] bArr = new byte[capacity];
        ByteBuffer duplicate = byteBuffer.duplicate();
        duplicate.clear();
        int capacity2 = byteBuffer.capacity();
        while (duplicate.remaining() > 0) {
            int remaining = duplicate.remaining() <= capacity ? duplicate.remaining() : capacity;
            duplicate.get(bArr, 0, remaining);
            capacity2 = partialHash(capacity2, bArr, 0, remaining);
        }
        if (capacity2 == 0) {
            return 1;
        }
        return capacity2;
    }

    public static <T extends MessageLite> T getDefaultInstance(Class<T> cls) {
        try {
            Class[] clsArr = new Class[0];
            Method method = cls.getMethod("getDefaultInstance", null);
            return (T) method.invoke(method, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get default instance for " + cls, e);
        }
    }

    static Object mergeMessage(Object obj, Object obj2) {
        return ((MessageLite) obj).toBuilder().mergeFrom((MessageLite) obj2).buildPartial();
    }

    public static class ListAdapter<F, T> extends AbstractList<T> {
        private final Converter<F, T> converter;
        private final List<F> fromList;

        public interface Converter<F, T> {
            T convert(F f);
        }

        public ListAdapter(List<F> list, Converter<F, T> converter) {
            this.fromList = list;
            this.converter = converter;
        }

        @Override // java.util.AbstractList, java.util.List
        public T get(int i) {
            return (T) this.converter.convert(this.fromList.get(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.fromList.size();
        }
    }

    public static class MapAdapter<K, V, RealValue> extends AbstractMap<K, V> {
        private final Map<K, RealValue> realMap;
        private final Converter<RealValue, V> valueConverter;

        public interface Converter<A, B> {
            A doBackward(B b);

            B doForward(A a);
        }

        public static <T extends EnumLite> Converter<Integer, T> newEnumConverter(final EnumLiteMap<T> enumLiteMap, final T t) {
            return (Converter<Integer, T>) new Converter<Integer, T>() { // from class: com.android.framework.protobuf.Internal.MapAdapter.1
                /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/Integer;)TT; */
                @Override // com.android.framework.protobuf.Internal.MapAdapter.Converter
                public EnumLite doForward(Integer num) {
                    EnumLite findValueByNumber = EnumLiteMap.this.findValueByNumber(num.intValue());
                    return findValueByNumber == null ? t : findValueByNumber;
                }

                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Integer; */
                @Override // com.android.framework.protobuf.Internal.MapAdapter.Converter
                public Integer doBackward(EnumLite enumLite) {
                    return Integer.valueOf(enumLite.getNumber());
                }
            };
        }

        public MapAdapter(Map<K, RealValue> map, Converter<RealValue, V> converter) {
            this.realMap = map;
            this.valueConverter = converter;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            RealValue realvalue = this.realMap.get(obj);
            if (realvalue == null) {
                return null;
            }
            return this.valueConverter.doForward(realvalue);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k, V v) {
            Object put = this.realMap.put(k, this.valueConverter.doBackward(v));
            if (put == null) {
                return null;
            }
            return (V) this.valueConverter.doForward(put);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            return new SetAdapter(this.realMap.entrySet());
        }

        private class SetAdapter extends AbstractSet<Map.Entry<K, V>> {
            private final Set<Map.Entry<K, RealValue>> realSet;

            public SetAdapter(Set<Map.Entry<K, RealValue>> set) {
                this.realSet = set;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<K, V>> iterator() {
                return new IteratorAdapter(this.realSet.iterator());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return this.realSet.size();
            }
        }

        private class IteratorAdapter implements Iterator<Map.Entry<K, V>> {
            private final Iterator<Map.Entry<K, RealValue>> realIterator;

            public IteratorAdapter(Iterator<Map.Entry<K, RealValue>> it) {
                this.realIterator = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.realIterator.hasNext();
            }

            @Override // java.util.Iterator
            public Map.Entry<K, V> next() {
                return new EntryAdapter(this.realIterator.next());
            }

            @Override // java.util.Iterator
            public void remove() {
                this.realIterator.remove();
            }
        }

        private class EntryAdapter implements Map.Entry<K, V> {
            private final Map.Entry<K, RealValue> realEntry;

            public EntryAdapter(Map.Entry<K, RealValue> entry) {
                this.realEntry = entry;
            }

            @Override // java.util.Map.Entry
            public K getKey() {
                return this.realEntry.getKey();
            }

            @Override // java.util.Map.Entry
            public V getValue() {
                return (V) MapAdapter.this.valueConverter.doForward(this.realEntry.getValue());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Map.Entry
            public V setValue(V v) {
                Object value = this.realEntry.setValue(MapAdapter.this.valueConverter.doBackward(v));
                if (value == null) {
                    return null;
                }
                return (V) MapAdapter.this.valueConverter.doForward(value);
            }

            @Override // java.util.Map.Entry
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                return (obj instanceof Map.Entry) && getKey().equals(((Map.Entry) obj).getKey()) && getValue().equals(getValue());
            }

            @Override // java.util.Map.Entry
            public int hashCode() {
                return this.realEntry.hashCode();
            }
        }
    }
}
