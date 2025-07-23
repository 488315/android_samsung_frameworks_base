package android.content.pm;

import android.util.SparseArrayMap;
import com.android.internal.util.ArrayUtils;
import java.util.Objects;
import java.util.Random;
import libcore.util.EmptyArray;

/* loaded from: classes.dex */
public final class UserPackage {
    private static final boolean ENABLE_CACHING = true;
    static final int MAX_NUM_CACHED_ENTRIES_PER_USER = 1000;
    public final String packageName;
    public final int userId;
    private static final Object sCacheLock = new Object();
    private static final SparseArrayMap<String, UserPackage> sCache = new SparseArrayMap<>();
    private static int[] sUserIds = EmptyArray.INT;

    private UserPackage(int i, String str) {
        this.userId = i;
        this.packageName = str;
    }

    public String toString() {
        return "<" + this.userId + ">" + this.packageName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UserPackage) {
            UserPackage userPackage = (UserPackage) obj;
            if (this.userId == userPackage.userId && Objects.equals(this.packageName, userPackage.packageName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.userId * 31) + this.packageName.hashCode();
    }

    public static UserPackage of(int i, String str) {
        synchronized (sCacheLock) {
            if (!ArrayUtils.contains(sUserIds, i)) {
                return new UserPackage(i, str);
            }
            SparseArrayMap<String, UserPackage> sparseArrayMap = sCache;
            UserPackage userPackage = sparseArrayMap.get(i, str);
            if (userPackage == null) {
                maybePurgeRandomEntriesLocked(i);
                String intern = str.intern();
                userPackage = new UserPackage(i, intern);
                sparseArrayMap.add(i, intern, userPackage);
            }
            return userPackage;
        }
    }

    public static void removeFromCache(int i, String str) {
        synchronized (sCacheLock) {
            sCache.delete(i, str);
        }
    }

    public static void setValidUserIds(int[] iArr) {
        int[] iArr2 = (int[]) iArr.clone();
        synchronized (sCacheLock) {
            sUserIds = iArr2;
            for (int numMaps = sCache.numMaps() - 1; numMaps >= 0; numMaps--) {
                SparseArrayMap<String, UserPackage> sparseArrayMap = sCache;
                if (!ArrayUtils.contains(iArr2, sparseArrayMap.keyAt(numMaps))) {
                    sparseArrayMap.deleteAt(numMaps);
                }
            }
        }
    }

    public static int numEntriesForUser(int i) {
        int numElementsForKey;
        synchronized (sCacheLock) {
            numElementsForKey = sCache.numElementsForKey(i);
        }
        return numElementsForKey;
    }

    private static void maybePurgeRandomEntriesLocked(int i) {
        int numElementsForKeyAt;
        SparseArrayMap<String, UserPackage> sparseArrayMap = sCache;
        int indexOfKey = sparseArrayMap.indexOfKey(i);
        if (indexOfKey >= 0 && (numElementsForKeyAt = sparseArrayMap.numElementsForKeyAt(indexOfKey)) >= 1000) {
            Random random = new Random();
            int max = Math.max(1, 10);
            int i2 = 0;
            for (numElementsForKeyAt = sparseArrayMap.numElementsForKeyAt(indexOfKey); i2 < max && numElementsForKeyAt > 0; numElementsForKeyAt--) {
                sCache.deleteAt(indexOfKey, random.nextInt(numElementsForKeyAt));
                i2++;
            }
        }
    }
}
