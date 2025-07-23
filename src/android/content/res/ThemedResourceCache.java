package android.content.res;

import android.content.res.Resources;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
abstract class ThemedResourceCache<T> {
    public static final int UNDEFINED_GENERATION = -1;
    private int mGeneration;
    private LongSparseArray<WeakReference<T>> mNullThemedEntries;
    private ArrayMap<Resources.ThemeKey, LongSparseArray<WeakReference<T>>> mThemedEntries;
    private LongSparseArray<WeakReference<T>> mUnthemedEntries;

    protected abstract boolean shouldInvalidateEntry(T t, int i);

    ThemedResourceCache() {
    }

    public void put(long j, Resources.Theme theme, T t, int i) {
        put(j, theme, t, i, true);
    }

    public void put(long j, Resources.Theme theme, T t, int i, boolean z) {
        LongSparseArray<WeakReference<T>> themedLocked;
        if (t == null) {
            return;
        }
        synchronized (this) {
            if (!z) {
                themedLocked = getUnthemedLocked(true);
            } else {
                themedLocked = getThemedLocked(theme, true);
            }
            if (themedLocked != null && (i == this.mGeneration || i == -1)) {
                themedLocked.put(j, new WeakReference<>(t));
            }
        }
    }

    public int getGeneration() {
        return this.mGeneration;
    }

    public T get(long j, Resources.Theme theme) {
        WeakReference<T> weakReference;
        WeakReference<T> weakReference2;
        synchronized (this) {
            LongSparseArray<WeakReference<T>> themedLocked = getThemedLocked(theme, false);
            if (themedLocked != null && (weakReference2 = themedLocked.get(j)) != null) {
                return weakReference2.get();
            }
            LongSparseArray<WeakReference<T>> unthemedLocked = getUnthemedLocked(false);
            if (unthemedLocked == null || (weakReference = unthemedLocked.get(j)) == null) {
                return null;
            }
            return weakReference.get();
        }
    }

    public void onConfigurationChange(int i) {
        synchronized (this) {
            pruneLocked(i);
            this.mGeneration++;
        }
    }

    private LongSparseArray<WeakReference<T>> getThemedLocked(Resources.Theme theme, boolean z) {
        if (theme == null) {
            if (this.mNullThemedEntries == null && z) {
                this.mNullThemedEntries = new LongSparseArray<>(1);
            }
            return this.mNullThemedEntries;
        }
        if (this.mThemedEntries == null) {
            if (!z) {
                return null;
            }
            this.mThemedEntries = new ArrayMap<>(1);
        }
        Resources.ThemeKey key = theme.getKey();
        LongSparseArray<WeakReference<T>> longSparseArray = this.mThemedEntries.get(key);
        if (longSparseArray != null || !z) {
            return longSparseArray;
        }
        LongSparseArray<WeakReference<T>> longSparseArray2 = new LongSparseArray<>(1);
        this.mThemedEntries.put(key.m1103clone(), longSparseArray2);
        return longSparseArray2;
    }

    private LongSparseArray<WeakReference<T>> getUnthemedLocked(boolean z) {
        if (this.mUnthemedEntries == null && z) {
            this.mUnthemedEntries = new LongSparseArray<>(1);
        }
        return this.mUnthemedEntries;
    }

    private boolean pruneLocked(int i) {
        ArrayMap<Resources.ThemeKey, LongSparseArray<WeakReference<T>>> arrayMap = this.mThemedEntries;
        if (arrayMap != null) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                if (pruneEntriesLocked(this.mThemedEntries.valueAt(size), i)) {
                    this.mThemedEntries.removeAt(size);
                }
            }
        }
        pruneEntriesLocked(this.mNullThemedEntries, i);
        pruneEntriesLocked(this.mUnthemedEntries, i);
        return this.mThemedEntries == null && this.mNullThemedEntries == null && this.mUnthemedEntries == null;
    }

    private boolean pruneEntriesLocked(LongSparseArray<WeakReference<T>> longSparseArray, int i) {
        if (longSparseArray == null) {
            return true;
        }
        for (int size = longSparseArray.size() - 1; size >= 0; size--) {
            WeakReference<T> valueAt = longSparseArray.valueAt(size);
            if (valueAt == null || pruneEntryLocked(valueAt.get(), i)) {
                longSparseArray.removeAt(size);
            }
        }
        return longSparseArray.size() == 0;
    }

    private boolean pruneEntryLocked(T t, int i) {
        if (t != null) {
            return i != 0 && shouldInvalidateEntry(t, i);
        }
        return true;
    }

    public synchronized void clear() {
        ArrayMap<Resources.ThemeKey, LongSparseArray<WeakReference<T>>> arrayMap = this.mThemedEntries;
        if (arrayMap != null) {
            arrayMap.clear();
        }
        LongSparseArray<WeakReference<T>> longSparseArray = this.mUnthemedEntries;
        if (longSparseArray != null) {
            longSparseArray.clear();
        }
        LongSparseArray<WeakReference<T>> longSparseArray2 = this.mNullThemedEntries;
        if (longSparseArray2 != null) {
            longSparseArray2.clear();
        }
    }
}
