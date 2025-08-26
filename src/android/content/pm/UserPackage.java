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
                String strIntern = str.intern();
                userPackage = new UserPackage(i, strIntern);
                sparseArrayMap.add(i, strIntern, userPackage);
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
            for (int iNumMaps = sCache.numMaps() - 1; iNumMaps >= 0; iNumMaps--) {
                SparseArrayMap<String, UserPackage> sparseArrayMap = sCache;
                if (!ArrayUtils.contains(iArr2, sparseArrayMap.keyAt(iNumMaps))) {
                    sparseArrayMap.deleteAt(iNumMaps);
                }
            }
        }
    }

    public static int numEntriesForUser(int i) {
        int iNumElementsForKey;
        synchronized (sCacheLock) {
            iNumElementsForKey = sCache.numElementsForKey(i);
        }
        return iNumElementsForKey;
    }

    private static void maybePurgeRandomEntriesLocked(int i) {
        int iNumElementsForKeyAt;
        SparseArrayMap<String, UserPackage> sparseArrayMap = sCache;
        int iIndexOfKey = sparseArrayMap.indexOfKey(i);
        if (iIndexOfKey >= 0 && (iNumElementsForKeyAt = sparseArrayMap.numElementsForKeyAt(iIndexOfKey)) >= 1000) {
            Random random = new Random();
            int iMax = Math.max(1, 10);
            int i2 = 0;
            for (iNumElementsForKeyAt = sparseArrayMap.numElementsForKeyAt(iIndexOfKey); i2 < iMax && iNumElementsForKeyAt > 0; iNumElementsForKeyAt--) {
                sCache.deleteAt(iIndexOfKey, random.nextInt(iNumElementsForKeyAt));
                i2++;
            }
        }
    }
}
