package com.android.server.vcn.util;

import android.os.ParcelUuid;
import android.os.PersistableBundle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public abstract class PersistableBundleUtils {
  public static final Serializer INTEGER_SERIALIZER =
      new Serializer() { // from class:
                         // com.android.server.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda0
        @Override // com.android.server.vcn.util.PersistableBundleUtils.Serializer
        public final PersistableBundle toPersistableBundle(Object obj) {
          PersistableBundle lambda$static$0;
          lambda$static$0 = PersistableBundleUtils.lambda$static$0((Integer) obj);
          return lambda$static$0;
        }
      };
  public static final Deserializer INTEGER_DESERIALIZER =
      new Deserializer() { // from class:
                           // com.android.server.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda1
        @Override // com.android.server.vcn.util.PersistableBundleUtils.Deserializer
        public final Object fromPersistableBundle(PersistableBundle persistableBundle) {
          Integer lambda$static$1;
          lambda$static$1 = PersistableBundleUtils.lambda$static$1(persistableBundle);
          return lambda$static$1;
        }
      };
  public static final Serializer STRING_SERIALIZER =
      new Serializer() { // from class:
                         // com.android.server.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda2
        @Override // com.android.server.vcn.util.PersistableBundleUtils.Serializer
        public final PersistableBundle toPersistableBundle(Object obj) {
          PersistableBundle lambda$static$2;
          lambda$static$2 = PersistableBundleUtils.lambda$static$2((String) obj);
          return lambda$static$2;
        }
      };
  public static final Deserializer STRING_DESERIALIZER =
      new Deserializer() { // from class:
                           // com.android.server.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda3
        @Override // com.android.server.vcn.util.PersistableBundleUtils.Deserializer
        public final Object fromPersistableBundle(PersistableBundle persistableBundle) {
          String lambda$static$3;
          lambda$static$3 = PersistableBundleUtils.lambda$static$3(persistableBundle);
          return lambda$static$3;
        }
      };

  public interface Deserializer {
    Object fromPersistableBundle(PersistableBundle persistableBundle);
  }

  public interface Serializer {
    PersistableBundle toPersistableBundle(Object obj);
  }

  public static /* synthetic */ PersistableBundle lambda$static$0(Integer num) {
    PersistableBundle persistableBundle = new PersistableBundle();
    persistableBundle.putInt("INTEGER_KEY", num.intValue());
    return persistableBundle;
  }

  public static /* synthetic */ Integer lambda$static$1(PersistableBundle persistableBundle) {
    Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
    return Integer.valueOf(persistableBundle.getInt("INTEGER_KEY"));
  }

  public static /* synthetic */ PersistableBundle lambda$static$2(String str) {
    PersistableBundle persistableBundle = new PersistableBundle();
    persistableBundle.putString("STRING_KEY", str);
    return persistableBundle;
  }

  public static /* synthetic */ String lambda$static$3(PersistableBundle persistableBundle) {
    Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
    return persistableBundle.getString("STRING_KEY");
  }

  public static PersistableBundle fromParcelUuid(ParcelUuid parcelUuid) {
    PersistableBundle persistableBundle = new PersistableBundle();
    persistableBundle.putString("PARCEL_UUID", parcelUuid.toString());
    return persistableBundle;
  }

  public static ParcelUuid toParcelUuid(PersistableBundle persistableBundle) {
    return ParcelUuid.fromString(persistableBundle.getString("PARCEL_UUID"));
  }

  public static PersistableBundle fromList(List list, Serializer serializer) {
    PersistableBundle persistableBundle = new PersistableBundle();
    persistableBundle.putInt("COLLECTION_LENGTH", list.size());
    for (int i = 0; i < list.size(); i++) {
      persistableBundle.putPersistableBundle(
          String.format("LIST_ITEM_%d", Integer.valueOf(i)),
          serializer.toPersistableBundle(list.get(i)));
    }
    return persistableBundle;
  }

  public static List toList(PersistableBundle persistableBundle, Deserializer deserializer) {
    int i = persistableBundle.getInt("COLLECTION_LENGTH");
    ArrayList arrayList = new ArrayList(i);
    for (int i2 = 0; i2 < i; i2++) {
      arrayList.add(
          deserializer.fromPersistableBundle(
              persistableBundle.getPersistableBundle(
                  String.format("LIST_ITEM_%d", Integer.valueOf(i2)))));
    }
    return arrayList;
  }

  public static PersistableBundle fromMap(Map map, Serializer serializer, Serializer serializer2) {
    PersistableBundle persistableBundle = new PersistableBundle();
    persistableBundle.putInt("COLLECTION_LENGTH", map.size());
    int i = 0;
    for (Map.Entry entry : map.entrySet()) {
      String format = String.format("MAP_KEY_%d", Integer.valueOf(i));
      String format2 = String.format("MAP_VALUE_%d", Integer.valueOf(i));
      persistableBundle.putPersistableBundle(
          format, serializer.toPersistableBundle(entry.getKey()));
      persistableBundle.putPersistableBundle(
          format2, serializer2.toPersistableBundle(entry.getValue()));
      i++;
    }
    return persistableBundle;
  }

  public static LinkedHashMap toMap(
      PersistableBundle persistableBundle, Deserializer deserializer, Deserializer deserializer2) {
    int i = persistableBundle.getInt("COLLECTION_LENGTH");
    LinkedHashMap linkedHashMap = new LinkedHashMap(i);
    for (int i2 = 0; i2 < i; i2++) {
      String format = String.format("MAP_KEY_%d", Integer.valueOf(i2));
      String format2 = String.format("MAP_VALUE_%d", Integer.valueOf(i2));
      linkedHashMap.put(
          deserializer.fromPersistableBundle(persistableBundle.getPersistableBundle(format)),
          deserializer2.fromPersistableBundle(persistableBundle.getPersistableBundle(format2)));
    }
    return linkedHashMap;
  }

  public static byte[] toDiskStableBytes(PersistableBundle persistableBundle) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    persistableBundle.writeToStream(byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }

  public static PersistableBundle fromDiskStableBytes(byte[] bArr) {
    return PersistableBundle.readFromStream(new ByteArrayInputStream(bArr));
  }

  public class LockingReadWriteHelper {
    public final ReadWriteLock mDiskLock = new ReentrantReadWriteLock();
    public final String mPath;

    public LockingReadWriteHelper(String str) {
      Objects.requireNonNull(str, "fileName was null");
      this.mPath = str;
    }

    public PersistableBundle readFromDisk() {
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

    public void writeToDisk(PersistableBundle persistableBundle) {
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
        i =
            Objects.hash(
                Integer.valueOf(i), str, Integer.valueOf(getHashCode((PersistableBundle) obj)));
      } else {
        i = Objects.hash(Integer.valueOf(i), str, obj);
      }
    }
    return i;
  }

  public static boolean isEqual(
      PersistableBundle persistableBundle, PersistableBundle persistableBundle2) {
    if (Objects.equals(persistableBundle, persistableBundle2)) {
      return true;
    }
    if (Objects.isNull(persistableBundle) != Objects.isNull(persistableBundle2)
        || !persistableBundle.keySet().equals(persistableBundle2.keySet())) {
      return false;
    }
    for (String str : persistableBundle.keySet()) {
      Object obj = persistableBundle.get(str);
      Object obj2 = persistableBundle2.get(str);
      if (!Objects.equals(obj, obj2)) {
        if (Objects.isNull(obj) != Objects.isNull(obj2)
            || !obj.getClass().equals(obj2.getClass())) {
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
        } else if (!obj.equals(obj2)) {
          return false;
        }
      }
    }
    return true;
  }

  public class PersistableBundleWrapper {
    public final PersistableBundle mBundle;

    public PersistableBundleWrapper(PersistableBundle persistableBundle) {
      Objects.requireNonNull(persistableBundle, "Bundle was null");
      this.mBundle = persistableBundle;
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
        return PersistableBundleUtils.isEqual(
            this.mBundle, ((PersistableBundleWrapper) obj).mBundle);
      }
      return false;
    }

    public String toString() {
      return this.mBundle.toString();
    }
  }
}
