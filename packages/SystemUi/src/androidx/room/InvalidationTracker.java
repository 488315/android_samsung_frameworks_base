package androidx.room;

import android.database.sqlite.SQLiteException;
import android.util.Log;
import androidx.arch.core.internal.SafeIterableMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InvalidationTracker {
    public final RoomDatabase mDatabase;
    public final HashMap mTableIdLookup;
    public final String[] mTableNames;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ObservedTableTracker {
        public ObservedTableTracker(int i) {
            long[] jArr = new long[i];
            boolean[] zArr = new boolean[i];
            int[] iArr = new int[i];
            Arrays.fill(jArr, 0L);
            Arrays.fill(zArr, false);
        }
    }

    public InvalidationTracker(RoomDatabase roomDatabase, String... strArr) {
        this(roomDatabase, new HashMap(), Collections.emptyMap(), strArr);
    }

    public InvalidationTracker(RoomDatabase roomDatabase, Map<String, String> map, Map<String, Set<String>> map2, String... strArr) {
        new AtomicBoolean(false);
        new SafeIterableMap();
        new Runnable() { // from class: androidx.room.InvalidationTracker.1
            @Override // java.lang.Runnable
            public final void run() {
                ReentrantReadWriteLock.ReadLock readLock = InvalidationTracker.this.mDatabase.mCloseLock.readLock();
                readLock.lock();
                try {
                    InvalidationTracker.this.mDatabase.getClass();
                } catch (SQLiteException | IllegalStateException e) {
                    Log.e("ROOM", "Cannot run invalidation tracker. Is the db closed?", e);
                } finally {
                    readLock.unlock();
                    InvalidationTracker.this.getClass();
                }
            }
        };
        this.mDatabase = roomDatabase;
        new ObservedTableTracker(strArr.length);
        this.mTableIdLookup = new HashMap();
        new InvalidationLiveDataContainer(roomDatabase);
        int length = strArr.length;
        this.mTableNames = new String[length];
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            Locale locale = Locale.US;
            String lowerCase = str.toLowerCase(locale);
            this.mTableIdLookup.put(lowerCase, Integer.valueOf(i));
            String str2 = map.get(strArr[i]);
            if (str2 != null) {
                this.mTableNames[i] = str2.toLowerCase(locale);
            } else {
                this.mTableNames[i] = lowerCase;
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            Locale locale2 = Locale.US;
            String lowerCase2 = value.toLowerCase(locale2);
            if (this.mTableIdLookup.containsKey(lowerCase2)) {
                String lowerCase3 = entry.getKey().toLowerCase(locale2);
                HashMap hashMap = this.mTableIdLookup;
                hashMap.put(lowerCase3, (Integer) hashMap.get(lowerCase2));
            }
        }
    }
}
