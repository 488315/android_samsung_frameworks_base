package android.net.vcn.util;

import android.os.ParcelUuid;
import android.os.PersistableBundle;
import com.android.net.module.util.HexDump;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes3.dex */
public class PersistableBundleUtils {
    private static final String BYTE_ARRAY_KEY = "BYTE_ARRAY_KEY";
    private static final String COLLECTION_SIZE_KEY = "COLLECTION_LENGTH";
    private static final String INTEGER_KEY = "INTEGER_KEY";
    private static final String LIST_KEY_FORMAT = "LIST_ITEM_%d";
    private static final String MAP_KEY_FORMAT = "MAP_KEY_%d";
    private static final String MAP_VALUE_FORMAT = "MAP_VALUE_%d";
    private static final String PARCEL_UUID_KEY = "PARCEL_UUID";
    private static final String STRING_KEY = "STRING_KEY";
    public static final Serializer<Integer> INTEGER_SERIALIZER = new Serializer() { // from class: android.net.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda0
        @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
        public final PersistableBundle toPersistableBundle(Object obj) {
            return PersistableBundleUtils.lambda$static$0((Integer) obj);
        }
    };
    public static final Deserializer<Integer> INTEGER_DESERIALIZER = new Deserializer() { // from class: android.net.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda1
        @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
        public final Object fromPersistableBundle(PersistableBundle persistableBundle) {
            return PersistableBundleUtils.lambda$static$1(persistableBundle);
        }
    };
    public static final Serializer<String> STRING_SERIALIZER = new Serializer() { // from class: android.net.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda2
        @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
        public final PersistableBundle toPersistableBundle(Object obj) {
            return PersistableBundleUtils.lambda$static$2((String) obj);
        }
    };
    public static final Deserializer<String> STRING_DESERIALIZER = new Deserializer() { // from class: android.net.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda3
        @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
        public final Object fromPersistableBundle(PersistableBundle persistableBundle) {
            return PersistableBundleUtils.lambda$static$3(persistableBundle);
        }
    };

    public interface Deserializer<T> {
        T fromPersistableBundle(PersistableBundle persistableBundle);
    }

    public interface Serializer<T> {
        PersistableBundle toPersistableBundle(T t);
    }

    static /* synthetic */ PersistableBundle lambda$static$0(Integer num) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt(INTEGER_KEY, num.intValue());
        return persistableBundle;
    }

    static /* synthetic */ Integer lambda$static$1(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        return Integer.valueOf(persistableBundle.getInt(INTEGER_KEY));
    }

    static /* synthetic */ PersistableBundle lambda$static$2(String str) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(STRING_KEY, str);
        return persistableBundle;
    }

    static /* synthetic */ String lambda$static$3(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        return persistableBundle.getString(STRING_KEY);
    }

    public static PersistableBundle fromParcelUuid(ParcelUuid parcelUuid) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(PARCEL_UUID_KEY, parcelUuid.toString());
        return persistableBundle;
    }

    public static ParcelUuid toParcelUuid(PersistableBundle persistableBundle) {
        return ParcelUuid.fromString(persistableBundle.getString(PARCEL_UUID_KEY));
    }

    public static <T> PersistableBundle fromList(List<T> list, Serializer<T> serializer) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt(COLLECTION_SIZE_KEY, list.size());
        for (int i = 0; i < list.size(); i++) {
            persistableBundle.putPersistableBundle(String.format(LIST_KEY_FORMAT, Integer.valueOf(i)), serializer.toPersistableBundle(list.get(i)));
        }
        return persistableBundle;
    }

    public static <T> List<T> toList(PersistableBundle persistableBundle, Deserializer<T> deserializer) {
        int i = persistableBundle.getInt(COLLECTION_SIZE_KEY);
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(deserializer.fromPersistableBundle(persistableBundle.getPersistableBundle(String.format(LIST_KEY_FORMAT, Integer.valueOf(i2)))));
        }
        return arrayList;
    }

    public static PersistableBundle fromByteArray(byte[] bArr) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(BYTE_ARRAY_KEY, HexDump.toHexString(bArr));
        return persistableBundle;
    }

    public static byte[] toByteArray(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        String string = persistableBundle.getString(BYTE_ARRAY_KEY);
        if (string == null || string.length() % 2 != 0) {
            throw new IllegalArgumentException("PersistableBundle contains invalid byte array");
        }
        return HexDump.hexStringToByteArray(string);
    }

    public static <K, V> PersistableBundle fromMap(Map<K, V> map, Serializer<K> serializer, Serializer<V> serializer2) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt(COLLECTION_SIZE_KEY, map.size());
        int i = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            String format = String.format(MAP_KEY_FORMAT, Integer.valueOf(i));
            String format2 = String.format(MAP_VALUE_FORMAT, Integer.valueOf(i));
            persistableBundle.putPersistableBundle(format, serializer.toPersistableBundle(entry.getKey()));
            persistableBundle.putPersistableBundle(format2, serializer2.toPersistableBundle(entry.getValue()));
            i++;
        }
        return persistableBundle;
    }

    public static <K, V> LinkedHashMap<K, V> toMap(PersistableBundle persistableBundle, Deserializer<K> deserializer, Deserializer<V> deserializer2) {
        int i = persistableBundle.getInt(COLLECTION_SIZE_KEY);
        LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<>(i);
        for (int i2 = 0; i2 < i; i2++) {
            String format = String.format(MAP_KEY_FORMAT, Integer.valueOf(i2));
            String format2 = String.format(MAP_VALUE_FORMAT, Integer.valueOf(i2));
            linkedHashMap.put(deserializer.fromPersistableBundle(persistableBundle.getPersistableBundle(format)), deserializer2.fromPersistableBundle(persistableBundle.getPersistableBundle(format2)));
        }
        return linkedHashMap;
    }

    public static byte[] toDiskStableBytes(PersistableBundle persistableBundle) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        persistableBundle.writeToStream(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static PersistableBundle fromDiskStableBytes(byte[] bArr) throws IOException {
        return PersistableBundle.readFromStream(new ByteArrayInputStream(bArr));
    }

    public static class LockingReadWriteHelper {
        private final ReadWriteLock mDiskLock = new ReentrantReadWriteLock();
        private final String mPath;

        public LockingReadWriteHelper(String str) {
            this.mPath = (String) Objects.requireNonNull(str, "fileName was null");
        }

        public PersistableBundle readFromDisk() throws IOException {
            try {
                this.mDiskLock.readLock().lock();
                File file = new File(this.mPath);
                if (file.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        PersistableBundle readFromStream = PersistableBundle.readFromStream(fileInputStream);
                        fileInputStream.close();
                        return readFromStream;
                    } finally {
                    }
                }
                this.mDiskLock.readLock().unlock();
                return null;
            } finally {
                this.mDiskLock.readLock().unlock();
            }
        }

        public void writeToDisk(PersistableBundle persistableBundle) throws IOException {
            Objects.requireNonNull(persistableBundle, "bundle was null");
            try {
                this.mDiskLock.writeLock().lock();
                File file = new File(this.mPath);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    persistableBundle.writeToStream(fileOutputStream);
                    fileOutputStream.close();
                } finally {
                }
            } finally {
                this.mDiskLock.writeLock().unlock();
            }
        }
    }

    public static PersistableBundle minimizeBundle(PersistableBundle persistableBundle, String... strArr) {
        Object obj;
        PersistableBundle persistableBundle2 = new PersistableBundle();
        if (persistableBundle != null) {
            for (String str : strArr) {
                if (persistableBundle.containsKey(str) && (obj = persistableBundle.get(str)) != null) {
                    if (obj instanceof Boolean) {
                        persistableBundle2.putBoolean(str, ((Boolean) obj).booleanValue());
                    } else if (obj instanceof boolean[]) {
                        persistableBundle2.putBooleanArray(str, (boolean[]) obj);
                    } else if (obj instanceof Double) {
                        persistableBundle2.putDouble(str, ((Double) obj).doubleValue());
                    } else if (obj instanceof double[]) {
                        persistableBundle2.putDoubleArray(str, (double[]) obj);
                    } else if (obj instanceof Integer) {
                        persistableBundle2.putInt(str, ((Integer) obj).intValue());
                    } else if (obj instanceof int[]) {
                        persistableBundle2.putIntArray(str, (int[]) obj);
                    } else if (obj instanceof Long) {
                        persistableBundle2.putLong(str, ((Long) obj).longValue());
                    } else if (obj instanceof long[]) {
                        persistableBundle2.putLongArray(str, (long[]) obj);
                    } else if (obj instanceof String) {
                        persistableBundle2.putString(str, (String) obj);
                    } else if (obj instanceof String[]) {
                        persistableBundle2.putStringArray(str, (String[]) obj);
                    } else if (obj instanceof PersistableBundle) {
                        persistableBundle2.putPersistableBundle(str, (PersistableBundle) obj);
                    }
                }
            }
        }
        return persistableBundle2;
    }

    public static int getHashCode(PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            return -1;
        }
        Iterator it = new TreeSet(persistableBundle.keySet()).iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            Object obj = persistableBundle.get(str);
            if (obj instanceof PersistableBundle) {
                i = Objects.hash(Integer.valueOf(i), str, Integer.valueOf(getHashCode((PersistableBundle) obj)));
            } else {
                i = Objects.hash(Integer.valueOf(i), str, obj);
            }
        }
        return i;
    }

    public static boolean isEqual(PersistableBundle persistableBundle, PersistableBundle persistableBundle2) {
        if (Objects.equals(persistableBundle, persistableBundle2)) {
            return true;
        }
        if (Objects.isNull(persistableBundle) != Objects.isNull(persistableBundle2) || !persistableBundle.keySet().equals(persistableBundle2.keySet())) {
            return false;
        }
        for (String str : persistableBundle.keySet()) {
            Object obj = persistableBundle.get(str);
            Object obj2 = persistableBundle2.get(str);
            if (!Objects.equals(obj, obj2)) {
                if (Objects.isNull(obj) != Objects.isNull(obj2) || !Objects.equals(obj.getClass(), obj2.getClass())) {
                    return false;
                }
                if (obj instanceof PersistableBundle) {
                    if (!isEqual((PersistableBundle) obj, (PersistableBundle) obj2)) {
                        return false;
                    }
                } else if (obj.getClass().isArray()) {
                    if (obj instanceof boolean[]) {
                        if (!Arrays.equals((boolean[]) obj, (boolean[]) obj2)) {
                            return false;
                        }
                    } else if (obj instanceof double[]) {
                        if (!Arrays.equals((double[]) obj, (double[]) obj2)) {
                            return false;
                        }
                    } else if (obj instanceof int[]) {
                        if (!Arrays.equals((int[]) obj, (int[]) obj2)) {
                            return false;
                        }
                    } else if (obj instanceof long[]) {
                        if (!Arrays.equals((long[]) obj, (long[]) obj2)) {
                            return false;
                        }
                    } else if (!Arrays.equals((Object[]) obj, (Object[]) obj2)) {
                        return false;
                    }
                } else if (!Objects.equals(obj, obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static class PersistableBundleWrapper {
        private final PersistableBundle mBundle;

        public PersistableBundleWrapper(PersistableBundle persistableBundle) {
            this.mBundle = (PersistableBundle) Objects.requireNonNull(persistableBundle, "Bundle was null");
        }

        public int getInt(String str, int i) {
            return this.mBundle.getInt(str, i);
        }

        public int[] getIntArray(String str, int[] iArr) {
            int[] intArray = this.mBundle.getIntArray(str);
            return intArray == null ? iArr : intArray;
        }

        public int hashCode() {
            return PersistableBundleUtils.getHashCode(this.mBundle);
        }

        public boolean equals(Object obj) {
            if (obj instanceof PersistableBundleWrapper) {
                return PersistableBundleUtils.isEqual(this.mBundle, ((PersistableBundleWrapper) obj).mBundle);
            }
            return false;
        }

        public String toString() {
            return this.mBundle.toString();
        }
    }
}
