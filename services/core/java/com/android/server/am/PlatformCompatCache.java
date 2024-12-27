package com.android.server.am;

import android.content.pm.ApplicationInfo;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import android.util.Pair;

import com.android.internal.compat.IPlatformCompat;
import com.android.server.compat.CompatChange;
import com.android.server.compat.PlatformCompat;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PlatformCompatCache {
    public static final long[] CACHED_COMPAT_CHANGE_IDS_MAPPING = {136274596, 136219221, 183972877};
    public static PlatformCompatCache sPlatformCompatCache;
    public final boolean mCacheEnabled;
    public final LongSparseArray mCaches = new LongSparseArray();
    public final IPlatformCompat mIPlatformCompatProxy;
    public final PlatformCompat mPlatformCompat;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CacheItem implements CompatChange.ChangeListener {
        public final long mChangeId;
        public final PlatformCompat mPlatformCompat;
        public final Object mLock = new Object();
        public final ArrayMap mCache = new ArrayMap();

        public CacheItem(PlatformCompat platformCompat, long j) {
            this.mPlatformCompat = platformCompat;
            this.mChangeId = j;
            platformCompat.registerListener(j, this);
        }

        public final boolean fetchLocked(ApplicationInfo applicationInfo, int i) {
            Pair pair =
                    new Pair(
                            Boolean.valueOf(
                                    this.mPlatformCompat.isChangeEnabledInternalNoLogging(
                                            this.mChangeId, applicationInfo)),
                            new WeakReference(applicationInfo));
            if (i >= 0) {
                this.mCache.setValueAt(i, pair);
            } else {
                this.mCache.put(applicationInfo.packageName, pair);
            }
            return ((Boolean) pair.first).booleanValue();
        }

        public final boolean isChangeEnabled(ApplicationInfo applicationInfo) {
            synchronized (this.mLock) {
                try {
                    int indexOfKey = this.mCache.indexOfKey(applicationInfo.packageName);
                    if (indexOfKey < 0) {
                        return fetchLocked(applicationInfo, indexOfKey);
                    }
                    Pair pair = (Pair) this.mCache.valueAt(indexOfKey);
                    if (((WeakReference) pair.second).get() == applicationInfo) {
                        return ((Boolean) pair.first).booleanValue();
                    }
                    return fetchLocked(applicationInfo, indexOfKey);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.compat.CompatChange.ChangeListener
        public final void onCompatChange(String str) {
            synchronized (this.mLock) {
                try {
                    int indexOfKey = this.mCache.indexOfKey(str);
                    if (indexOfKey >= 0) {
                        ApplicationInfo applicationInfo =
                                (ApplicationInfo)
                                        ((WeakReference)
                                                        ((Pair) this.mCache.valueAt(indexOfKey))
                                                                .second)
                                                .get();
                        if (applicationInfo != null) {
                            fetchLocked(applicationInfo, indexOfKey);
                        } else {
                            this.mCache.removeAt(indexOfKey);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public PlatformCompatCache(long[] jArr) {
        IBinder service = ServiceManager.getService("platform_compat");
        if (!(service instanceof PlatformCompat)) {
            this.mIPlatformCompatProxy = IPlatformCompat.Stub.asInterface(service);
            this.mPlatformCompat = null;
            this.mCacheEnabled = false;
            return;
        }
        this.mPlatformCompat = (PlatformCompat) ServiceManager.getService("platform_compat");
        for (long j : jArr) {
            this.mCaches.put(j, new CacheItem(this.mPlatformCompat, j));
        }
        this.mIPlatformCompatProxy = null;
        this.mCacheEnabled = true;
    }

    public static PlatformCompatCache getInstance() {
        if (sPlatformCompatCache == null) {
            sPlatformCompatCache =
                    new PlatformCompatCache(new long[] {136274596, 136219221, 183972877});
        }
        return sPlatformCompatCache;
    }
}
