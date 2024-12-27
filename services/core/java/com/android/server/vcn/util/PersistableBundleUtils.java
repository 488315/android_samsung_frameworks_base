package com.android.server.vcn.util;

import android.net.vcn.VcnConfig;
import android.os.ParcelUuid;
import android.os.PersistableBundle;

import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PersistableBundleUtils {
    public static final VcnManagementService$$ExternalSyntheticLambda10 STRING_DESERIALIZER = null;
    public static final VcnManagementService$$ExternalSyntheticLambda10 STRING_SERIALIZER = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LockingReadWriteHelper {
        public final ReadWriteLock mDiskLock = new ReentrantReadWriteLock();
        public final String mPath;

        public LockingReadWriteHelper(String str) {
            Objects.requireNonNull(str, "fileName was null");
            this.mPath = str;
        }

        public final PersistableBundle readFromDisk() {
            try {
                this.mDiskLock.readLock().lock();
                File file = new File(this.mPath);
                if (!file.exists()) {
                    this.mDiskLock.readLock().unlock();
                    return null;
                }
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    PersistableBundle readFromStream =
                            PersistableBundle.readFromStream(fileInputStream);
                    fileInputStream.close();
                    return readFromStream;
                } finally {
                }
            } finally {
                this.mDiskLock.readLock().unlock();
            }
        }

        public final void writeToDisk(PersistableBundle persistableBundle) {
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
                    this.mDiskLock.writeLock().unlock();
                } finally {
                }
            } catch (Throwable th) {
                this.mDiskLock.writeLock().unlock();
                throw th;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PersistableBundleWrapper {
        public final PersistableBundle mBundle;

        public PersistableBundleWrapper(PersistableBundle persistableBundle) {
            this.mBundle = persistableBundle;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof PersistableBundleWrapper) {
                return PersistableBundleUtils.isEqual(
                        this.mBundle, ((PersistableBundleWrapper) obj).mBundle);
            }
            return false;
        }

        public final int hashCode() {
            return PersistableBundleUtils.getHashCode(this.mBundle);
        }

        public final String toString() {
            return this.mBundle.toString();
        }
    }

    public static PersistableBundle fromMap(
            Map map,
            VcnManagementService$$ExternalSyntheticLambda10
                    vcnManagementService$$ExternalSyntheticLambda10,
            VcnManagementService$$ExternalSyntheticLambda10
                    vcnManagementService$$ExternalSyntheticLambda102) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt("COLLECTION_LENGTH", map.size());
        int i = 0;
        for (Map.Entry entry : map.entrySet()) {
            String format = String.format("MAP_KEY_%d", Integer.valueOf(i));
            String format2 = String.format("MAP_VALUE_%d", Integer.valueOf(i));
            Object key = entry.getKey();
            vcnManagementService$$ExternalSyntheticLambda10.getClass();
            PersistableBundle persistableBundle2 = new PersistableBundle();
            persistableBundle2.putString("PARCEL_UUID", ((ParcelUuid) key).toString());
            persistableBundle.putPersistableBundle(format, persistableBundle2);
            persistableBundle.putPersistableBundle(
                    format2, ((VcnConfig) entry.getValue()).toPersistableBundle());
            i++;
        }
        return persistableBundle;
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
            i =
                    obj instanceof PersistableBundle
                            ? Objects.hash(
                                    Integer.valueOf(i),
                                    str,
                                    Integer.valueOf(getHashCode((PersistableBundle) obj)))
                            : Objects.hash(Integer.valueOf(i), str, obj);
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
                } else if (!obj.getClass().isArray()) {
                    if (!obj.equals(obj2)) {
                        return false;
                    }
                } else if (obj instanceof boolean[]) {
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
            }
        }
        return true;
    }
}
