package android.app;

import android.media.MediaMetrics;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SystemPropertySetter;
import com.android.internal.os.ApplicationSharedMemory;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import dalvik.annotation.optimization.NeverCompile;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class PropertyInvalidatedCache<Query, Result> {
    static final String BRIEF = "-brief";
    private static final String CACHE_KEY_PREFIX = "cache_key";
    private static final boolean DEBUG = false;
    private static final int MAX_RESERVED_NONCE = 3;
    public static final String MODULE_BLUETOOTH = "bluetooth";
    public static final String MODULE_SYSTEM = "system_server";
    public static final String MODULE_TEST = "test";
    static final String NAME_CONTAINS = "-name-has=";
    static final String NAME_LIKE = "-name-like=";
    private static final int NONCE_BYPASS = 3;
    private static final int NONCE_CORKED = 2;
    private static final int NONCE_DISABLED = 1;
    static final int NONCE_UNSET = 0;
    static final String PROPERTY_CONTAINS = "-property-has=";
    static final String PROPERTY_LIKE = "-property-like=";
    private static final String TAG = "PropertyInvalidatedCache";
    private static final boolean VERIFY = false;
    private final PropertyInvalidatedCache<Query, Result>.CacheMap<Query, Result> mCache;
    private final String mCacheName;
    private final boolean mCacheNullResults;
    private long mClears;
    private QueryHandler<Query, Result> mComputer;
    private boolean mDisabled;
    private long mHighWaterMark;
    private long mHits;
    private long mLastSeenNonce;
    private final Object mLock;
    private final int mMaxEntries;
    private long mMissOverflow;
    private long mMisses;
    private final NonceHandler mNonce;
    private long mNulls;
    private final String mPropertyName;
    private long[] mSkips;
    public static final String MODULE_TELEPHONY = "telephony";
    private static final String[] sValidModule = {"system_server", "bluetooth", MODULE_TELEPHONY, "test"};
    private static final String PREFIX_SYSTEM = "cache_key.system_server.";
    private static final String PREFIX_TEST = "cache_key.test.";
    private static final String[] sValidKeyPrefix = {PREFIX_SYSTEM, "cache_key.bluetooth.", "cache_key.telephony.", PREFIX_TEST};
    private static final String[] sNonceName = {"unset", "disabled", "corked", "bypass"};
    private static boolean sTestMode = false;
    private static final Object sCorkLock = new Object();
    private static final Object sGlobalLock = new Object();
    private static final HashSet<String> sDisabledKeys = new HashSet<>();
    private static final WeakHashMap<PropertyInvalidatedCache, Void> sCaches = new WeakHashMap<>();
    private static boolean sEnabled = true;
    private static final ConcurrentHashMap<String, NonceHandler> sHandlers = new ConcurrentHashMap<>();
    private static final boolean sSharedMemoryAvailable = isSharedMemoryAvailable();

    public static abstract class QueryHandler<Q, R> {
        public abstract R apply(Q q);

        public boolean shouldBypassCache(Q q) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isReservedNonce(long j) {
        return j >= 0 && j <= 3;
    }

    private static boolean isSharedMemoryAvailable$ravenwood() {
        return false;
    }

    private Result maybeCheckConsistency(Query query, Result result) {
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native int nativeGetByteBlock(long j, int i, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native int nativeGetByteBlockHash(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native int nativeGetMaxByte(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native int nativeGetMaxNonce(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nativeGetNonce(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void nativeSetByteBlock(long j, int i, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native boolean nativeSetNonce(long j, int i, long j2);

    private static void throwIfNotTest$ravenwood() {
    }

    protected Result refresh(Result result, Query query) {
        return result;
    }

    public static boolean separatePermissionNotificationsEnabled() {
        return isSharedMemoryAvailable() && Flags.picSeparatePermissionNotifications();
    }

    public static String createPropertyName(String str, String str2) {
        int i;
        char[] charArray = str2.toCharArray();
        int i2 = 0;
        for (int i3 = 1; i3 < charArray.length; i3++) {
            if (Character.isUpperCase(charArray[i3])) {
                i2++;
            }
        }
        char[] cArr = new char[charArray.length + i2];
        int i4 = 0;
        for (int i5 = 0; i5 < charArray.length; i5++) {
            if (Character.isJavaIdentifierPart(charArray[i5])) {
                if (Character.isUpperCase(charArray[i5])) {
                    if (i5 > 0) {
                        cArr[i4] = '_';
                        i4++;
                    }
                    i = i4 + 1;
                    cArr[i4] = Character.toLowerCase(charArray[i5]);
                } else {
                    i = i4 + 1;
                    cArr[i4] = charArray[i5];
                }
                i4 = i;
            } else {
                throw new IllegalArgumentException("invalid api name");
            }
        }
        return "cache_key." + str + MediaMetrics.SEPARATOR + new String(cArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void throwIfInvalidModule(String str) {
        int i = 0;
        while (true) {
            String[] strArr = sValidModule;
            if (i < strArr.length) {
                if (strArr[i].equals(str)) {
                    return;
                } else {
                    i++;
                }
            } else {
                throw new IllegalArgumentException("invalid module: " + str);
            }
        }
    }

    private static void throwIfInvalidCacheKey(String str) {
        int i = 0;
        while (true) {
            String[] strArr = sValidKeyPrefix;
            if (i < strArr.length) {
                if (str.startsWith(strArr[i])) {
                    return;
                } else {
                    i++;
                }
            } else {
                throw new IllegalArgumentException("invalid cache name: " + str);
            }
        }
    }

    public static String createSystemCacheKey(String str) {
        return createPropertyName("system_server", str);
    }

    private static class DefaultComputer<Query, Result> extends QueryHandler<Query, Result> {
        final PropertyInvalidatedCache<Query, Result> mCache;

        DefaultComputer(PropertyInvalidatedCache<Query, Result> propertyInvalidatedCache) {
            this.mCache = propertyInvalidatedCache;
        }

        @Override // android.app.PropertyInvalidatedCache.QueryHandler
        public Result apply(Query query) {
            return this.mCache.recompute(query);
        }
    }

    private class CacheMap<Query, Result> {
        private final SparseArray<LinkedHashMap<Query, Result>> mCache = new SparseArray<>();
        private final boolean mIsolated;
        private final int mSelfUid;
        private final ArraySet<Query> mShadowCache;
        private int mShadowHits;
        private int mShadowMisses;
        private int mShadowSelfHits;
        private final boolean mStatistics;
        private final boolean mTestMode;
        private final SparseBooleanArray mUidSeen;

        private LinkedHashMap<Query, Result> createMap() {
            return new LinkedHashMap<Query, Result>(2, 0.75f, true) { // from class: android.app.PropertyInvalidatedCache.CacheMap.1
                @Override // java.util.LinkedHashMap
                protected boolean removeEldestEntry(Map.Entry entry) {
                    int size = size();
                    long j = size;
                    if (j > PropertyInvalidatedCache.this.mHighWaterMark) {
                        PropertyInvalidatedCache.this.mHighWaterMark = j;
                    }
                    if (size <= PropertyInvalidatedCache.this.mMaxEntries) {
                        return false;
                    }
                    PropertyInvalidatedCache.this.mMissOverflow++;
                    return true;
                }
            };
        }

        CacheMap(boolean z, boolean z2) {
            boolean z3 = Flags.picIsolateCacheByUid() && z;
            this.mIsolated = z3;
            boolean z4 = Flags.picIsolatedCacheStatistics() && z3;
            this.mStatistics = z4;
            if (z4) {
                this.mUidSeen = new SparseBooleanArray();
                this.mShadowCache = new ArraySet<>();
            } else {
                this.mUidSeen = null;
                this.mShadowCache = null;
            }
            this.mSelfUid = Process.myUid();
            this.mTestMode = z2;
        }

        private int callerUid() {
            if (!this.mIsolated) {
                return 0;
            }
            if (this.mTestMode) {
                return Binder.getCallingWorkSourceUid();
            }
            return Binder.getCallingUid();
        }

        Result get(Query query) {
            int iCallerUid = callerUid();
            if (this.mStatistics) {
                if (this.mShadowCache.contains(query)) {
                    this.mShadowHits++;
                    if (iCallerUid == this.mSelfUid) {
                        this.mShadowSelfHits++;
                    }
                } else {
                    this.mShadowMisses++;
                }
            }
            LinkedHashMap<Query, Result> linkedHashMap = this.mCache.get(iCallerUid);
            if (linkedHashMap != null) {
                return linkedHashMap.get(query);
            }
            return null;
        }

        boolean containsKey(Query query) {
            LinkedHashMap<Query, Result> linkedHashMap = this.mCache.get(callerUid());
            if (linkedHashMap != null) {
                return linkedHashMap.containsKey(query);
            }
            return false;
        }

        void remove(Query query) {
            int iCallerUid = callerUid();
            if (this.mStatistics) {
                this.mShadowCache.remove(query);
            }
            LinkedHashMap<Query, Result> linkedHashMap = this.mCache.get(iCallerUid);
            if (linkedHashMap != null) {
                linkedHashMap.remove(query);
            }
        }

        void put(Query query, Result result) {
            int iCallerUid = callerUid();
            if (this.mStatistics) {
                this.mShadowCache.add(query);
                this.mUidSeen.put(iCallerUid, true);
            }
            LinkedHashMap<Query, Result> linkedHashMapCreateMap = this.mCache.get(iCallerUid);
            if (linkedHashMapCreateMap == null) {
                linkedHashMapCreateMap = createMap();
                this.mCache.put(iCallerUid, linkedHashMapCreateMap);
            }
            linkedHashMapCreateMap.put(query, result);
        }

        int size() {
            int size = 0;
            for (int i = 0; i < this.mCache.size(); i++) {
                size += this.mCache.valueAt(i).size();
            }
            return size;
        }

        void clear() {
            if (this.mStatistics) {
                this.mShadowCache.clear();
            }
            this.mCache.clear();
        }

        void dump(PrintWriter printWriter) {
            if (this.mStatistics) {
                printWriter.println(TextUtils.formatSimple("    ShadowHits: %d, ShadowMisses: %d, ShadowSize: %d", Integer.valueOf(this.mShadowHits), Integer.valueOf(this.mShadowMisses), Integer.valueOf(this.mShadowCache.size())));
                printWriter.println(TextUtils.formatSimple("    ShadowUids: %d, SelfUid: %d", Integer.valueOf(this.mUidSeen.size()), Integer.valueOf(this.mShadowSelfHits)));
            }
        }

        void dumpDetailed(PrintWriter printWriter) {
            for (int i = 0; i < this.mCache.size(); i++) {
                int iKeyAt = this.mCache.keyAt(i);
                Set<Map.Entry<Query, Result>> setEntrySet = this.mCache.valueAt(i).entrySet();
                if (setEntrySet.size() == 0) {
                    return;
                }
                printWriter.println("    Contents:");
                printWriter.println(TextUtils.formatSimple("      Uid: %d\n", Integer.valueOf(iKeyAt)));
                for (Map.Entry<Query, Result> entry : setEntrySet) {
                    printWriter.println(TextUtils.formatSimple("      Key: %s\n      Value: %s\n", Objects.toString(entry.getKey()), Objects.toString(entry.getValue())));
                }
            }
        }
    }

    private static abstract class NonceHandler {
        final String mName;
        private boolean mTestMode;
        private ArrayList<Semaphore> mWatchers;
        protected final Object mLock = new Object();
        private int mInvalidated = 0;
        private int mCorkedInvalidates = 0;
        private int mCorks = 0;
        protected long mShadowNonce = 0;

        abstract long getNonceInternal();

        abstract void setNonceInternal(long j);

        NonceHandler(String str) {
            this.mName = str;
            synchronized (PropertyInvalidatedCache.sGlobalLock) {
                this.mTestMode = PropertyInvalidatedCache.sTestMode;
            }
        }

        long getNonce() {
            synchronized (this.mLock) {
                if (!this.mTestMode) {
                    return getNonceInternal();
                }
                return this.mShadowNonce;
            }
        }

        void setNonce(long j) {
            synchronized (this.mLock) {
                this.mShadowNonce = j;
                if (!this.mTestMode) {
                    setNonceInternal(j);
                }
                wakeAllWatchersLocked();
            }
        }

        private void wakeAllWatchersLocked() {
            if (this.mWatchers != null) {
                for (int i = 0; i < this.mWatchers.size(); i++) {
                    this.mWatchers.get(i).release();
                }
            }
        }

        void registerWatcher(Semaphore semaphore) {
            synchronized (this.mLock) {
                if (this.mWatchers == null) {
                    this.mWatchers = new ArrayList<>();
                }
                this.mWatchers.add(semaphore);
            }
        }

        void unregisterWatcher(Semaphore semaphore) {
            synchronized (this.mLock) {
                ArrayList<Semaphore> arrayList = this.mWatchers;
                if (arrayList != null) {
                    arrayList.remove(semaphore);
                }
            }
        }

        void invalidate() {
            long next;
            if (PropertyInvalidatedCache.sEnabled) {
                synchronized (this.mLock) {
                    if (this.mCorks > 0) {
                        this.mCorkedInvalidates++;
                    } else {
                        if (getNonce() == 1) {
                            return;
                        }
                        do {
                            next = NoPreloadHolder.next();
                        } while (PropertyInvalidatedCache.isReservedNonce(next));
                        setNonce(next);
                        this.mInvalidated++;
                    }
                }
            }
        }

        void cork() {
            if (PropertyInvalidatedCache.sEnabled) {
                synchronized (this.mLock) {
                    if (this.mCorks == 0) {
                        long nonce = getNonce();
                        if (nonce != 0 && nonce != 1) {
                            setNonce(2L);
                        }
                    } else {
                        this.mCorkedInvalidates++;
                    }
                    this.mCorks++;
                }
            }
        }

        void uncork() {
            if (PropertyInvalidatedCache.sEnabled) {
                synchronized (this.mLock) {
                    int i = this.mCorks - 1;
                    this.mCorks = i;
                    if (i < 0) {
                        throw new AssertionError("cork underflow: " + this.mName);
                    }
                    if (i == 0) {
                        invalidate();
                    }
                }
            }
        }

        void disable() {
            if (PropertyInvalidatedCache.sEnabled) {
                synchronized (this.mLock) {
                    setNonce(1L);
                }
            }
        }

        void setTestMode(boolean z) {
            synchronized (this.mLock) {
                this.mTestMode = z;
                this.mShadowNonce = 0L;
            }
        }

        static final class Stats extends Record {
            private final int corkedInvalidates;
            private final int invalidated;

            private /* synthetic */ boolean $record$equals(Object obj) {
                if (!(obj instanceof Stats)) {
                    return false;
                }
                Stats stats = (Stats) obj;
                return this.invalidated == stats.invalidated && this.corkedInvalidates == stats.corkedInvalidates;
            }

            private /* synthetic */ Object[] $record$getFieldsAsObjects() {
                return new Object[]{Integer.valueOf(this.invalidated), Integer.valueOf(this.corkedInvalidates)};
            }

            Stats(int invalidated, int corkedInvalidates) {
                this.invalidated = invalidated;
                this.corkedInvalidates = corkedInvalidates;
            }

            public int corkedInvalidates() {
                return this.corkedInvalidates;
            }

            @Override // java.lang.Record
            public final boolean equals(Object obj) {
                return $record$equals(obj);
            }

            @Override // java.lang.Record
            public final int hashCode() {
                return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.invalidated, this.corkedInvalidates);
            }

            public int invalidated() {
                return this.invalidated;
            }

            @Override // java.lang.Record
            public final String toString() {
                return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), Stats.class, "invalidated;corkedInvalidates");
            }
        }

        Stats getStats() {
            Stats stats;
            synchronized (this.mLock) {
                stats = new Stats(this.mInvalidated, this.mCorkedInvalidates);
            }
            return stats;
        }
    }

    private static final class NonceSysprop extends NonceHandler {
        private volatile SystemProperties.Handle mHandle;

        NonceSysprop(String str) {
            super(str);
        }

        @Override // android.app.PropertyInvalidatedCache.NonceHandler
        long getNonceInternal() {
            if (this.mHandle == null) {
                synchronized (this.mLock) {
                    if (this.mHandle == null) {
                        this.mHandle = SystemProperties.find(this.mName);
                        if (this.mHandle == null) {
                            return 0L;
                        }
                    }
                }
            }
            return this.mHandle.getLong(0L);
        }

        @Override // android.app.PropertyInvalidatedCache.NonceHandler
        void setNonceInternal(long j) throws InterruptedException {
            SystemPropertySetter.setWithRetry(this.mName, Long.toString(j));
        }
    }

    private static final class NonceSharedMem extends NonceHandler {
        private volatile int mHandle;
        private final String mShortName;
        private volatile NonceStore mStore;

        NonceSharedMem(String str, String str2) {
            super(str);
            this.mHandle = -1;
            if (str2 != null && str.startsWith(str2)) {
                this.mShortName = str.substring(str2.length());
            } else {
                this.mShortName = str;
            }
        }

        private int initialize(boolean z) {
            synchronized (this.mLock) {
                int handleForName = this.mHandle;
                if (handleForName == -1) {
                    if (this.mStore == null) {
                        this.mStore = NonceStore.getInstance();
                        if (this.mStore == null) {
                            return -1;
                        }
                    }
                    if (z) {
                        this.mStore.storeName(this.mShortName);
                    }
                    handleForName = this.mStore.getHandleForName(this.mShortName);
                    if (handleForName == -1) {
                        return -1;
                    }
                    this.mHandle = handleForName;
                }
                return handleForName;
            }
        }

        @Override // android.app.PropertyInvalidatedCache.NonceHandler
        long getNonceInternal() {
            int iInitialize = this.mHandle;
            if (iInitialize == -1 && (iInitialize = initialize(false)) == -1) {
                return 0L;
            }
            return this.mStore.getNonce(iInitialize);
        }

        @Override // android.app.PropertyInvalidatedCache.NonceHandler
        void setNonceInternal(long j) {
            int iInitialize = this.mHandle;
            if (iInitialize == -1 && (iInitialize = initialize(true)) == -1) {
                throw new IllegalStateException("unable to assign nonce handle: " + this.mName);
            }
            this.mStore.setNonce(iInitialize, j);
        }
    }

    private static class NonceLocal extends NonceHandler {
        private long mValue;

        NonceLocal(String str) {
            super(str);
        }

        @Override // android.app.PropertyInvalidatedCache.NonceHandler
        long getNonceInternal() {
            return this.mShadowNonce;
        }

        @Override // android.app.PropertyInvalidatedCache.NonceHandler
        void setNonceInternal(long j) {
            this.mShadowNonce = j;
        }
    }

    public static class NonceWatcher implements AutoCloseable {
        private final NonceHandler mHandler;
        private long mLastSeen;
        private final Semaphore mSem;

        private NonceWatcher(NonceHandler nonceHandler) {
            this.mLastSeen = 0L;
            Semaphore semaphore = new Semaphore(0);
            this.mSem = semaphore;
            this.mHandler = nonceHandler;
            nonceHandler.registerWatcher(semaphore);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mHandler.unregisterWatcher(this.mSem);
        }

        public long lastSeen() {
            return this.mLastSeen;
        }

        public boolean isChanged() {
            long nonce = this.mHandler.getNonce();
            if (nonce == this.mLastSeen) {
                return false;
            }
            this.mLastSeen = nonce;
            return true;
        }

        public int waitForChange() throws InterruptedException {
            this.mSem.acquire(1);
            return this.mSem.drainPermits() + 1;
        }

        public int waitForChange(long j, TimeUnit timeUnit) throws InterruptedException {
            if (this.mSem.tryAcquire(1, j, timeUnit)) {
                return this.mSem.drainPermits() + 1;
            }
            return 0;
        }

        public void wakeUp() {
            this.mSem.release();
        }
    }

    public NonceWatcher getNonceWatcher() {
        return new NonceWatcher(this.mNonce);
    }

    public static NonceWatcher getNonceWatcher(String str) {
        return new NonceWatcher(getNonceHandler(str));
    }

    public long getNonce() {
        long nonce;
        synchronized (this.mLock) {
            nonce = this.mNonce.getNonce();
        }
        return nonce;
    }

    private static boolean isSharedMemoryAvailable() {
        return com.android.internal.os.Flags.applicationSharedMemoryEnabled() && Flags.picUsesSharedMemory();
    }

    private static boolean inSharedMemoryDenyList(String str) {
        return str.equals("cache_key.system_server.package_info");
    }

    private static boolean sharedMemoryOkay(String str) {
        return sSharedMemoryAvailable && str.startsWith(PREFIX_SYSTEM) && !inSharedMemoryDenyList(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NonceHandler getNonceHandler(String str) {
        NonceHandler nonceSysprop;
        ConcurrentHashMap<String, NonceHandler> concurrentHashMap = sHandlers;
        NonceHandler nonceHandler = concurrentHashMap.get(str);
        if (nonceHandler != null) {
            return nonceHandler;
        }
        synchronized (sGlobalLock) {
            throwIfInvalidCacheKey(str);
            nonceSysprop = concurrentHashMap.get(str);
            if (nonceSysprop == null) {
                if (sharedMemoryOkay(str)) {
                    nonceSysprop = new NonceSharedMem(str, PREFIX_SYSTEM);
                } else if (str.startsWith(PREFIX_TEST)) {
                    nonceSysprop = new NonceLocal(str);
                } else {
                    nonceSysprop = new NonceSysprop(str);
                }
                concurrentHashMap.put(str, nonceSysprop);
            }
        }
        return nonceSysprop;
    }

    public static final class Args extends Record {
        public static final boolean DEFAULT_CACHE_NULLS = false;
        public static final boolean DEFAULT_ISOLATE_UIDS = true;
        public static final int DEFAULT_MAX_ENTRIES = 32;
        private final String mApi;
        private final boolean mCacheNulls;
        private final boolean mIsolateUids;
        private final int mMaxEntries;
        private final String mModule;
        private final boolean mTestMode;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof Args)) {
                return false;
            }
            Args args = (Args) obj;
            return this.mIsolateUids == args.mIsolateUids && this.mTestMode == args.mTestMode && this.mCacheNulls == args.mCacheNulls && this.mMaxEntries == args.mMaxEntries && Objects.equals(this.mModule, args.mModule) && Objects.equals(this.mApi, args.mApi);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.mModule, this.mApi, Integer.valueOf(this.mMaxEntries), Boolean.valueOf(this.mIsolateUids), Boolean.valueOf(this.mTestMode), Boolean.valueOf(this.mCacheNulls)};
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.mIsolateUids, this.mTestMode, this.mCacheNulls, this.mMaxEntries, this.mModule, this.mApi);
        }

        public String mApi() {
            return this.mApi;
        }

        public boolean mCacheNulls() {
            return this.mCacheNulls;
        }

        public boolean mIsolateUids() {
            return this.mIsolateUids;
        }

        public int mMaxEntries() {
            return this.mMaxEntries;
        }

        public String mModule() {
            return this.mModule;
        }

        public boolean mTestMode() {
            return this.mTestMode;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), Args.class, "mModule;mApi;mMaxEntries;mIsolateUids;mTestMode;mCacheNulls");
        }

        public Args(String mModule, String mApi, int mMaxEntries, boolean mIsolateUids, boolean mTestMode, boolean mCacheNulls) {
            PropertyInvalidatedCache.throwIfInvalidModule(mModule);
            Preconditions.checkArgumentPositive(mMaxEntries, "max cache size must be positive");
            this.mModule = mModule;
            this.mApi = mApi;
            this.mMaxEntries = mMaxEntries;
            this.mIsolateUids = mIsolateUids;
            this.mTestMode = mTestMode;
            this.mCacheNulls = mCacheNulls;
        }

        public Args(String str) {
            this(str, null, 32, true, false, false);
        }

        public Args api(String str) {
            return new Args(this.mModule, str, this.mMaxEntries, this.mIsolateUids, this.mTestMode, this.mCacheNulls);
        }

        public Args maxEntries(int i) {
            return new Args(this.mModule, this.mApi, i, this.mIsolateUids, this.mTestMode, this.mCacheNulls);
        }

        public Args isolateUids(boolean z) {
            return new Args(this.mModule, this.mApi, this.mMaxEntries, z, this.mTestMode, this.mCacheNulls);
        }

        public Args testMode(boolean z) {
            return new Args(this.mModule, this.mApi, this.mMaxEntries, this.mIsolateUids, z, this.mCacheNulls);
        }

        public Args cacheNulls(boolean z) {
            return new Args(this.mModule, this.mApi, this.mMaxEntries, this.mIsolateUids, this.mTestMode, z);
        }
    }

    public PropertyInvalidatedCache(Args args, String str, QueryHandler<Query, Result> queryHandler) {
        this.mLock = new Object();
        this.mHits = 0L;
        this.mMisses = 0L;
        this.mNulls = 0L;
        this.mSkips = new long[4];
        this.mMissOverflow = 0L;
        this.mHighWaterMark = 0L;
        this.mClears = 0L;
        this.mLastSeenNonce = 0L;
        this.mDisabled = false;
        String strCreatePropertyName = createPropertyName(args.mModule, args.mApi);
        this.mPropertyName = strCreatePropertyName;
        this.mCacheName = str;
        this.mCacheNullResults = args.mCacheNulls;
        this.mNonce = getNonceHandler(strCreatePropertyName);
        this.mMaxEntries = args.mMaxEntries;
        this.mCache = new CacheMap<>(args.mIsolateUids, args.mTestMode);
        this.mComputer = queryHandler == null ? new DefaultComputer<>(this) : queryHandler;
        registerCache();
    }

    private static Args argsFromProperty(String str) {
        throwIfInvalidCacheKey(str);
        String strSubstring = str.substring(10);
        int iIndexOf = strSubstring.indexOf(MediaMetrics.SEPARATOR);
        String strSubstring2 = strSubstring.substring(0, iIndexOf);
        return new Args(strSubstring2).api(strSubstring.substring(iIndexOf + 1));
    }

    public static String apiFromProperty(String str) {
        return argsFromProperty(str).mApi;
    }

    @Deprecated
    public PropertyInvalidatedCache(int i, String str) {
        this(argsFromProperty(str).maxEntries(i), str, (QueryHandler) null);
    }

    @Deprecated
    public PropertyInvalidatedCache(int i, String str, String str2) {
        this(argsFromProperty(str).maxEntries(i), str2, (QueryHandler) null);
    }

    public PropertyInvalidatedCache(int i, String str, String str2, String str3, QueryHandler<Query, Result> queryHandler) {
        this(new Args(str).maxEntries(i).api(str2), str3, queryHandler);
    }

    private void registerCache() {
        synchronized (sGlobalLock) {
            if (sDisabledKeys.contains(this.mCacheName)) {
                disableInstance();
            }
            sCaches.put(this, null);
        }
    }

    private static void throwIfNotTest() {
        Instrumentation instrumentation;
        ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
        if (activityThreadCurrentActivityThread != null && (instrumentation = activityThreadCurrentActivityThread.getInstrumentation()) != null && !instrumentation.isInstrumenting() && Flags.enforcePicTestmodeProtocol()) {
            throw new IllegalStateException("Test-only API called not from a test.");
        }
    }

    public static void setTestMode(boolean z) {
        throwIfNotTest();
        synchronized (sGlobalLock) {
            if (sTestMode == z) {
                String str = "cannot set test mode redundantly: mode=" + z;
                if (Flags.enforcePicTestmodeProtocol()) {
                    throw new IllegalStateException(str);
                }
                Log.e(TAG, str);
            }
            sTestMode = z;
            if (Flags.picTestMode() || !z) {
                setTestModeLocked(z);
            }
        }
    }

    private static void setTestModeLocked(boolean z) {
        Iterator<String> itAsIterator = sHandlers.keys().asIterator();
        while (itAsIterator.hasNext()) {
            sHandlers.get(itAsIterator.next()).setTestMode(z);
        }
    }

    public void testPropertyName() {
        throwIfNotTest();
        synchronized (sGlobalLock) {
            if (!sTestMode) {
                throw new IllegalStateException("cannot test property name with test mode off");
            }
            this.mNonce.setTestMode(true);
        }
    }

    private long getCurrentNonce() {
        return this.mNonce.getNonce();
    }

    public final void clear() {
        synchronized (this.mLock) {
            this.mCache.clear();
            this.mClears++;
        }
    }

    public Result recompute(Query query) {
        return this.mComputer.apply(query);
    }

    public boolean bypass(Query query) {
        return this.mComputer.shouldBypassCache(query);
    }

    public boolean resultEquals(Result result, Result result2) {
        if (result2 != null) {
            return Objects.equals(result, result2);
        }
        return true;
    }

    public void disableInstance() {
        synchronized (this.mLock) {
            this.mDisabled = true;
            clear();
        }
    }

    private static final void disableLocal(String str) {
        synchronized (sGlobalLock) {
            if (sDisabledKeys.contains(str)) {
                return;
            }
            for (PropertyInvalidatedCache propertyInvalidatedCache : sCaches.keySet()) {
                if (str.equals(propertyInvalidatedCache.mCacheName)) {
                    propertyInvalidatedCache.disableInstance();
                }
            }
            sDisabledKeys.add(str);
        }
    }

    public void forgetDisableLocal() {
        synchronized (sGlobalLock) {
            sDisabledKeys.remove(this.mCacheName);
        }
    }

    public void disableLocal() {
        disableForCurrentProcess();
    }

    public void disableForCurrentProcess() {
        disableLocal(this.mCacheName);
    }

    public static void disableForCurrentProcess(String str) {
        disableLocal(str);
    }

    public boolean isDisabled() {
        return this.mDisabled || !sEnabled;
    }

    public Result query(Query query) {
        boolean zContainsKey;
        Result result;
        long currentNonce = !isDisabled() ? getCurrentNonce() : 1L;
        if (!isReservedNonce(currentNonce) && bypass(query)) {
            currentNonce = 3;
        }
        while (!isReservedNonce(currentNonce)) {
            synchronized (this.mLock) {
                zContainsKey = false;
                if (currentNonce == this.mLastSeenNonce) {
                    result = this.mCache.get(query);
                    if (result != null) {
                        zContainsKey = true;
                    } else if (this.mCacheNullResults) {
                        zContainsKey = this.mCache.containsKey(query);
                    }
                    if (zContainsKey) {
                        this.mHits++;
                    }
                } else {
                    clear();
                    this.mLastSeenNonce = currentNonce;
                    result = null;
                }
            }
            if (zContainsKey) {
                Result resultRefresh = refresh(result, query);
                if (resultRefresh != result) {
                    long currentNonce2 = getCurrentNonce();
                    if (currentNonce == currentNonce2) {
                        synchronized (this.mLock) {
                            if (currentNonce == this.mLastSeenNonce) {
                                if (resultRefresh == null) {
                                    this.mCache.remove(query);
                                } else {
                                    ((PropertyInvalidatedCache<Query, Result>.CacheMap<Query, Result>) this.mCache).put(query, resultRefresh);
                                }
                            }
                        }
                        return maybeCheckConsistency(query, resultRefresh);
                    }
                    currentNonce = currentNonce2;
                } else {
                    return maybeCheckConsistency(query, result);
                }
            } else {
                Result resultRecompute = recompute(query);
                synchronized (this.mLock) {
                    if (this.mLastSeenNonce == currentNonce) {
                        if (resultRecompute != null || this.mCacheNullResults) {
                            ((PropertyInvalidatedCache<Query, Result>.CacheMap<Query, Result>) this.mCache).put(query, resultRecompute);
                        } else if (resultRecompute == null) {
                            this.mNulls++;
                        }
                    }
                    this.mMisses++;
                }
                return maybeCheckConsistency(query, resultRecompute);
            }
        }
        if (!this.mDisabled) {
            synchronized (this.mLock) {
                long[] jArr = this.mSkips;
                int i = (int) currentNonce;
                jArr[i] = jArr[i] + 1;
            }
        }
        return recompute(query);
    }

    private static final class NoPreloadHolder {
        private static final AtomicLong sNextNonce = new AtomicLong(new Random().nextLong());

        private NoPreloadHolder() {
        }

        public static long next() {
            return sNextNonce.getAndIncrement();
        }
    }

    public void disableSystemWide() {
        throwIfNotTest();
        disableSystemWide(this.mPropertyName);
    }

    private static void disableSystemWide(String str) {
        getNonceHandler(str).disable();
    }

    public void invalidateCache() {
        this.mNonce.invalidate();
    }

    public void corkInvalidations() {
        this.mNonce.cork();
    }

    public void uncorkInvalidations() {
        this.mNonce.uncork();
    }

    public static void invalidateCache(String str, String str2) {
        invalidateCache(createPropertyName(str, str2));
    }

    public static void invalidateCache(Args args) {
        invalidateCache(createPropertyName(args.mModule, args.mApi));
    }

    public static void invalidateCache(String str) {
        getNonceHandler(str).invalidate();
    }

    public static void corkInvalidations(String str) {
        getNonceHandler(str).cork();
    }

    public static void uncorkInvalidations(String str) {
        getNonceHandler(str).uncork();
    }

    public static final class AutoCorker {
        public static final int DEFAULT_AUTO_CORK_DELAY_MS = 50;
        private final int mAutoCorkDelayMs;
        private Handler mHandler;
        private final Object mLock;
        private NonceHandler mNonce;
        private final String mPropertyName;
        private long mUncorkDeadlineMs;

        public AutoCorker(String str) {
            this(str, 50);
        }

        public AutoCorker(String str, int i) {
            this.mLock = new Object();
            this.mUncorkDeadlineMs = -1L;
            if (PropertyInvalidatedCache.separatePermissionNotificationsEnabled()) {
                throw new IllegalStateException("AutoCorking is unavailable");
            }
            this.mPropertyName = str;
            this.mAutoCorkDelayMs = i;
        }

        public void autoCork() {
            synchronized (this.mLock) {
                if (this.mNonce == null) {
                    this.mNonce = PropertyInvalidatedCache.getNonceHandler(this.mPropertyName);
                }
            }
            if (getLooper() == null) {
                this.mNonce.invalidate();
                return;
            }
            synchronized (this.mLock) {
                boolean z = this.mUncorkDeadlineMs >= 0;
                this.mUncorkDeadlineMs = SystemClock.uptimeMillis() + this.mAutoCorkDelayMs;
                if (!z) {
                    getHandlerLocked().sendEmptyMessageAtTime(0, this.mUncorkDeadlineMs);
                    this.mNonce.cork();
                } else {
                    this.mNonce.invalidate();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleMessage(Message message) {
            synchronized (this.mLock) {
                if (this.mUncorkDeadlineMs < 0) {
                    return;
                }
                long jUptimeMillis = SystemClock.uptimeMillis();
                if (this.mUncorkDeadlineMs > jUptimeMillis) {
                    this.mUncorkDeadlineMs = jUptimeMillis + this.mAutoCorkDelayMs;
                    getHandlerLocked().sendEmptyMessageAtTime(0, this.mUncorkDeadlineMs);
                } else {
                    this.mUncorkDeadlineMs = -1L;
                    this.mNonce.uncork();
                }
            }
        }

        private Handler getHandlerLocked() {
            if (this.mHandler == null) {
                this.mHandler = new Handler(getLooper()) { // from class: android.app.PropertyInvalidatedCache.AutoCorker.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        AutoCorker.this.handleMessage(message);
                    }
                };
            }
            return this.mHandler;
        }

        private static Looper getLooper() {
            return BackgroundThread.getHandler().getLooper();
        }
    }

    public final String cacheName() {
        return this.mCacheName;
    }

    public final String propertyName() {
        return this.mPropertyName;
    }

    protected String queryToString(Query query) {
        return Objects.toString(query);
    }

    public static void disableForTestMode() {
        Log.d(TAG, "disabling all caches in the process");
        sEnabled = false;
    }

    private boolean getDisabledState() {
        return isDisabled();
    }

    public int size() {
        int size;
        synchronized (this.mLock) {
            size = this.mCache.size();
        }
        return size;
    }

    private static ArrayList<PropertyInvalidatedCache> getActiveCaches() {
        ArrayList<PropertyInvalidatedCache> arrayList;
        synchronized (sGlobalLock) {
            arrayList = new ArrayList<>(sCaches.keySet());
        }
        return arrayList;
    }

    private static boolean anyDetailed(String[] strArr) {
        for (String str : strArr) {
            if (str.startsWith(NAME_CONTAINS) || str.startsWith(NAME_LIKE) || str.startsWith(PROPERTY_CONTAINS) || str.startsWith(PROPERTY_LIKE)) {
                return true;
            }
        }
        return false;
    }

    private static boolean chooses(String str, String str2, String str3, boolean z) {
        if (!str.startsWith(str2)) {
            return false;
        }
        String strSubstring = str.substring(str2.length());
        if (z) {
            return str3.contains(strSubstring);
        }
        return str3.matches(strSubstring);
    }

    private boolean showDetailed(String[] strArr) {
        for (String str : strArr) {
            if (chooses(str, NAME_CONTAINS, cacheName(), true) || chooses(str, NAME_LIKE, cacheName(), false) || chooses(str, PROPERTY_CONTAINS, this.mPropertyName, true) || chooses(str, PROPERTY_LIKE, this.mPropertyName, false)) {
                return true;
            }
        }
        return false;
    }

    private long getSkipsLocked() {
        int i = 0;
        int i2 = 0;
        while (true) {
            long[] jArr = this.mSkips;
            if (i >= jArr.length) {
                return i2;
            }
            i2 = (int) (i2 + jArr[i]);
            i++;
        }
    }

    private boolean isActive(NonceHandler.Stats stats) {
        boolean z;
        synchronized (this.mLock) {
            z = (((this.mHits + this.mMisses) + getSkipsLocked()) + ((long) stats.invalidated)) + ((long) stats.corkedInvalidates) > 0;
        }
        return z;
    }

    @NeverCompile
    private void dumpContents(PrintWriter printWriter, boolean z, String[] strArr) {
        if (!z || showDetailed(strArr)) {
            boolean zEquals = false;
            for (String str : strArr) {
                zEquals |= str.equals(BRIEF);
            }
            NonceHandler.Stats stats = this.mNonce.getStats();
            synchronized (this.mLock) {
                if (zEquals) {
                    if (!isActive(stats)) {
                        return;
                    }
                }
                printWriter.println(TextUtils.formatSimple("  Cache Name: %s", cacheName()));
                printWriter.println(TextUtils.formatSimple("    Property: %s", this.mPropertyName));
                printWriter.println(TextUtils.formatSimple("    Hits: %d, Misses: %d, Skips: %d, Clears: %d, Nulls: %d", Long.valueOf(this.mHits), Long.valueOf(this.mMisses), Long.valueOf(getSkipsLocked()), Long.valueOf(this.mClears), Long.valueOf(this.mNulls)));
                printWriter.format("    Skip-%s: %d", sNonceName[0], Long.valueOf(this.mSkips[0]));
                int i = 1;
                while (true) {
                    long[] jArr = this.mSkips;
                    if (i >= jArr.length) {
                        break;
                    }
                    printWriter.format(", Skip-%s: %d", sNonceName[i], Long.valueOf(jArr[i]));
                    i++;
                }
                printWriter.println();
                printWriter.println(TextUtils.formatSimple("    Nonce: 0x%016x, Invalidates: %d, Corked: %d", Long.valueOf(this.mLastSeenNonce), Integer.valueOf(stats.invalidated), Integer.valueOf(stats.corkedInvalidates)));
                printWriter.println(TextUtils.formatSimple("    Current Size: %d, Max Size: %d, HW Mark: %d, Overflows: %d", Integer.valueOf(this.mCache.size()), Integer.valueOf(this.mMaxEntries), Long.valueOf(this.mHighWaterMark), Long.valueOf(this.mMissOverflow)));
                this.mCache.dump(printWriter);
                printWriter.println(TextUtils.formatSimple("    Enabled: %s", this.mDisabled ? "false" : "true"));
                if (z) {
                    this.mCache.dumpDetailed(printWriter);
                }
                printWriter.println("");
            }
        }
    }

    @NeverCompile
    private static void dumpCacheInfo(PrintWriter printWriter, String[] strArr) {
        if (!sEnabled) {
            printWriter.println("  Caching is disabled in this process.");
            return;
        }
        boolean zAnyDetailed = anyDetailed(strArr);
        if (sSharedMemoryAvailable) {
            printWriter.println("  SharedMemory: enabled");
            NonceStore.getInstance().dump(printWriter, "    ", zAnyDetailed);
        } else {
            printWriter.println("  SharedMemory: disabled");
        }
        printWriter.println();
        ArrayList<PropertyInvalidatedCache> activeCaches = getActiveCaches();
        for (int i = 0; i < activeCaches.size(); i++) {
            activeCaches.get(i).dumpContents(printWriter, zAnyDetailed, strArr);
        }
    }

    @NeverCompile
    public static void dumpCacheInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        dumpCacheInfo(printWriter, strArr);
        printWriter.close();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            byteArrayOutputStream.writeTo(fileOutputStream);
            fileOutputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException unused) {
            Log.e(TAG, "Failed to dump PropertyInvalidatedCache instances");
        }
    }

    public void dumpCacheEntries(PrintWriter printWriter) {
        synchronized (this.mLock) {
            this.mCache.dumpDetailed(printWriter);
        }
    }

    public static class NonceStore {
        public static final int INVALID_NONCE_INDEX = -1;
        private static final int MAX_STRING_LENGTH = 63;
        private static NonceStore sInstance;
        private static Object sLock = new Object();
        public final int mMaxByte;
        public final int mMaxNonce;
        private final boolean mMutable;
        private final long mPtr;
        private final Object mLock = new Object();
        private int mBlockHash = 0;
        private int mHighestIndex = -1;
        private int mStringBytes = 0;
        private int mPartialReads = 0;
        private int mStringUpdated = 0;
        private final ArrayMap<String, Integer> mStringHandle = new ArrayMap<>();

        public NonceStore(long j, boolean z) {
            this.mPtr = j;
            this.mMutable = z;
            this.mMaxByte = PropertyInvalidatedCache.nativeGetMaxByte(j);
            this.mMaxNonce = PropertyInvalidatedCache.nativeGetMaxNonce(j);
            refreshStringBlockLocked();
        }

        static NonceStore getInstance() {
            NonceStore nonceStore;
            synchronized (sLock) {
                if (sInstance == null) {
                    try {
                        ApplicationSharedMemory applicationSharedMemory = ApplicationSharedMemory.getInstance();
                        sInstance = applicationSharedMemory == null ? null : new NonceStore(applicationSharedMemory.getSystemNonceBlock(), applicationSharedMemory.isMutable());
                    } catch (IllegalStateException unused) {
                    }
                }
                nonceStore = sInstance;
            }
            return nonceStore;
        }

        private void updateStringMapLocked(byte[] bArr) {
            int i = 0;
            int i2 = 0;
            while (i < bArr.length && bArr[i] != 0) {
                if (i2 > this.mHighestIndex) {
                    this.mStringHandle.put(new String(bArr, i + 1, (int) bArr[i]), Integer.valueOf(i2));
                    this.mHighestIndex = i2;
                }
                i += bArr[i] + 1;
                i2++;
            }
            this.mStringBytes = i;
        }

        private void appendStringToMapLocked(String str, byte[] bArr) {
            byte b;
            int i = 0;
            while (i < bArr.length && (b = bArr[i]) != 0) {
                i += b + 1;
            }
            byte[] bytes = str.getBytes();
            if (bytes.length + i >= bArr.length) {
                return;
            }
            bArr[i] = (byte) bytes.length;
            System.arraycopy(bytes, 0, bArr, i + 1, bytes.length);
            this.mBlockHash = Arrays.hashCode(bArr);
        }

        private void refreshStringBlockLocked() {
            if (this.mBlockHash == PropertyInvalidatedCache.nativeGetByteBlockHash(this.mPtr)) {
                return;
            }
            byte[] bArr = new byte[this.mMaxByte];
            int iNativeGetByteBlock = PropertyInvalidatedCache.nativeGetByteBlock(this.mPtr, this.mBlockHash, bArr);
            if (iNativeGetByteBlock != Arrays.hashCode(bArr)) {
                this.mBlockHash = 0;
                this.mPartialReads++;
            } else {
                this.mStringUpdated++;
                this.mBlockHash = iNativeGetByteBlock;
                updateStringMapLocked(bArr);
            }
        }

        private static void throwIfBadString(String str) {
            if (str.length() == 0) {
                throw new IllegalArgumentException("cannot store an empty string");
            }
            if (str.length() > 63) {
                throw new IllegalArgumentException("cannot store a string longer than 63");
            }
        }

        private void throwIfBadHandle(int i) {
            if (i < 0 || i > this.mHighestIndex) {
                throw new IllegalArgumentException("invalid nonce handle: " + i);
            }
        }

        private void throwIfImmutable() {
            if (!this.mMutable) {
                throw new RuntimeException("write permission denied");
            }
        }

        public int storeName(String str) {
            int iIntValue;
            synchronized (this.mLock) {
                Integer num = this.mStringHandle.get(str);
                if (num == null) {
                    throwIfImmutable();
                    throwIfBadString(str);
                    if (this.mHighestIndex + 1 >= this.mMaxNonce) {
                        throw new RuntimeException("nonce limit exceeded");
                    }
                    byte[] bArr = new byte[this.mMaxByte];
                    PropertyInvalidatedCache.nativeGetByteBlock(this.mPtr, 0, bArr);
                    appendStringToMapLocked(str, bArr);
                    PropertyInvalidatedCache.nativeSetByteBlock(this.mPtr, this.mBlockHash, bArr);
                    updateStringMapLocked(bArr);
                    num = this.mStringHandle.get(str);
                }
                iIntValue = num.intValue();
            }
            return iIntValue;
        }

        public int getHandleForName(String str) {
            int iIntValue;
            synchronized (this.mLock) {
                Integer num = this.mStringHandle.get(str);
                if (num == null) {
                    refreshStringBlockLocked();
                    num = this.mStringHandle.get(str);
                }
                iIntValue = num != null ? num.intValue() : -1;
            }
            return iIntValue;
        }

        public boolean setNonce(int i, long j) {
            boolean zNativeSetNonce;
            synchronized (this.mLock) {
                throwIfBadHandle(i);
                throwIfImmutable();
                zNativeSetNonce = PropertyInvalidatedCache.nativeSetNonce(this.mPtr, i, j);
            }
            return zNativeSetNonce;
        }

        public long getNonce(int i) {
            long jNativeGetNonce;
            synchronized (this.mLock) {
                throwIfBadHandle(i);
                jNativeGetNonce = PropertyInvalidatedCache.nativeGetNonce(this.mPtr, i);
            }
            return jNativeGetNonce;
        }

        public void dump(PrintWriter printWriter, String str, boolean z) {
            synchronized (this.mLock) {
                printWriter.println(TextUtils.formatSimple("%sStringsMapped: %d, BytesUsed: %d", str, Integer.valueOf(this.mHighestIndex), Integer.valueOf(this.mStringBytes)));
                printWriter.println(TextUtils.formatSimple("%sPartialReads: %d, StringUpdates: %d", str, Integer.valueOf(this.mPartialReads), Integer.valueOf(this.mStringUpdated)));
                if (z) {
                    for (String str2 : this.mStringHandle.keySet()) {
                        Integer num = this.mStringHandle.get(str2);
                        num.intValue();
                        printWriter.println(TextUtils.formatSimple("%sHandle:%d Name:%s", str, num, str2));
                    }
                }
            }
        }
    }
}
