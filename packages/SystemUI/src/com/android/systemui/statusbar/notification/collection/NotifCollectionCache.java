package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.Dumpable;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifCollectionCache implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ConcurrentHashMap cache;
    public final AtomicInteger hits;
    public final AtomicInteger misses;
    public final long purgeTimeoutMillis;
    public final int retainCount;
    public final SystemClock systemClock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CacheEntry {
        public final String key;
        public long lastValidPurge;
        public int lives;
        public final Object value;

        public CacheEntry(String str, Object obj) {
            this.key = str;
            this.value = obj;
            this.lives = NotifCollectionCache.this.retainCount + 1;
            this.lastValidPurge = -NotifCollectionCache.this.purgeTimeoutMillis;
        }

        public final int getLives() {
            return this.lives;
        }

        public final void resetLives() {
            NotifCollectionCache notifCollectionCache = NotifCollectionCache.this;
            if (notifCollectionCache.retainCount == 0) {
                return;
            }
            synchronized (this.key) {
                this.lives = notifCollectionCache.retainCount + 1;
                this.lastValidPurge = -notifCollectionCache.purgeTimeoutMillis;
                Unit unit = Unit.INSTANCE;
            }
            NotifCollectionCache.this.getCache().put(this.key, this);
        }

        public final String toString() {
            return this.key + " = " + this.value;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public NotifCollectionCache() {
        this(0, 0L, null, 7, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00bc, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00c5, code lost:
    
        throw r3;
     */
    @Override // com.android.systemui.Dumpable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r4, java.lang.String[] r5) {
        /*
            r3 = this;
            android.util.IndentingPrintWriter r4 = com.android.systemui.util.DumpUtilsKt.asIndenting(r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "NotifCollectionCache(retainCount = "
            r5.<init>(r0)
            int r0 = r3.retainCount
            r5.append(r0)
            java.lang.String r0 = ", purgeTimeoutMillis = "
            r5.append(r0)
            long r0 = r3.purgeTimeoutMillis
            r5.append(r0)
            java.lang.String r0 = ")"
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            r4.println(r5)
            r4.increaseIndent()
            java.lang.String r5 = "entries present in cache"
            java.util.concurrent.ConcurrentHashMap r0 = r3.cache     // Catch: java.lang.Throwable -> Lbc
            java.util.Collection r0 = r0.values()     // Catch: java.lang.Throwable -> Lbc
            java.util.stream.Stream r0 = r0.stream()     // Catch: java.lang.Throwable -> Lbc
            com.android.systemui.statusbar.notification.collection.NotifCollectionCache$$ExternalSyntheticLambda0 r1 = new com.android.systemui.statusbar.notification.collection.NotifCollectionCache$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> Lbc
            r1.<init>()     // Catch: java.lang.Throwable -> Lbc
            com.android.systemui.statusbar.notification.collection.NotifCollectionCache$sam$java_util_function_Function$0 r2 = new com.android.systemui.statusbar.notification.collection.NotifCollectionCache$sam$java_util_function_Function$0     // Catch: java.lang.Throwable -> Lbc
            r2.<init>()     // Catch: java.lang.Throwable -> Lbc
            java.util.stream.Stream r0 = r0.map(r2)     // Catch: java.lang.Throwable -> Lbc
            java.util.stream.Stream r0 = r0.sorted()     // Catch: java.lang.Throwable -> Lbc
            java.util.List r0 = r0.toList()     // Catch: java.lang.Throwable -> Lbc
            java.util.Collection r0 = (java.util.Collection) r0     // Catch: java.lang.Throwable -> Lbc
            java.io.PrintWriter r5 = r4.append(r5)     // Catch: java.lang.Throwable -> Lbc
            java.lang.String r1 = ": "
            java.io.PrintWriter r5 = r5.append(r1)     // Catch: java.lang.Throwable -> Lbc
            int r1 = r0.size()     // Catch: java.lang.Throwable -> Lbc
            r5.println(r1)     // Catch: java.lang.Throwable -> Lbc
            r4.increaseIndent()     // Catch: java.lang.Throwable -> Lbc
            java.lang.Iterable r0 = (java.lang.Iterable) r0     // Catch: java.lang.Throwable -> L75
            java.util.Iterator r5 = r0.iterator()     // Catch: java.lang.Throwable -> L75
        L67:
            boolean r0 = r5.hasNext()     // Catch: java.lang.Throwable -> L75
            if (r0 == 0) goto L77
            java.lang.Object r0 = r5.next()     // Catch: java.lang.Throwable -> L75
            r4.println(r0)     // Catch: java.lang.Throwable -> L75
            goto L67
        L75:
            r3 = move-exception
            goto Lbe
        L77:
            r4.decreaseIndent()     // Catch: java.lang.Throwable -> Lbc
            java.util.concurrent.atomic.AtomicInteger r5 = r3.misses     // Catch: java.lang.Throwable -> Lbc
            int r5 = r5.get()     // Catch: java.lang.Throwable -> Lbc
            java.util.concurrent.atomic.AtomicInteger r3 = r3.hits     // Catch: java.lang.Throwable -> Lbc
            int r3 = r3.get()     // Catch: java.lang.Throwable -> Lbc
            float r0 = (float) r3     // Catch: java.lang.Throwable -> Lbc
            int r1 = r3 + r5
            float r1 = (float) r1     // Catch: java.lang.Throwable -> Lbc
            float r0 = r0 / r1
            r1 = 100
            float r1 = (float) r1     // Catch: java.lang.Throwable -> Lbc
            float r0 = r0 * r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbc
            r1.<init>()     // Catch: java.lang.Throwable -> Lbc
            java.lang.String r2 = "cache hit ratio = "
            r1.append(r2)     // Catch: java.lang.Throwable -> Lbc
            r1.append(r0)     // Catch: java.lang.Throwable -> Lbc
            java.lang.String r0 = "% ("
            r1.append(r0)     // Catch: java.lang.Throwable -> Lbc
            r1.append(r3)     // Catch: java.lang.Throwable -> Lbc
            java.lang.String r3 = " hits, "
            r1.append(r3)     // Catch: java.lang.Throwable -> Lbc
            r1.append(r5)     // Catch: java.lang.Throwable -> Lbc
            java.lang.String r3 = " misses)"
            r1.append(r3)     // Catch: java.lang.Throwable -> Lbc
            java.lang.String r3 = r1.toString()     // Catch: java.lang.Throwable -> Lbc
            r4.println(r3)     // Catch: java.lang.Throwable -> Lbc
            r4.decreaseIndent()
            return
        Lbc:
            r3 = move-exception
            goto Lc2
        Lbe:
            r4.decreaseIndent()     // Catch: java.lang.Throwable -> Lbc
            throw r3     // Catch: java.lang.Throwable -> Lbc
        Lc2:
            r4.decreaseIndent()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.NotifCollectionCache.dump(java.io.PrintWriter, java.lang.String[]):void");
    }

    public final ConcurrentHashMap<String, CacheEntry> getCache() {
        return this.cache;
    }

    public final AtomicInteger getHits() {
        return this.hits;
    }

    public final AtomicInteger getMisses() {
        return this.misses;
    }

    public final Object getOrFetch(String str, Function1 function1) {
        CacheEntry cacheEntry = (CacheEntry) this.cache.get(str);
        if (cacheEntry != null) {
            this.hits.incrementAndGet();
            cacheEntry.resetLives();
            return cacheEntry.value;
        }
        this.misses.incrementAndGet();
        Object mo779invoke = function1.mo779invoke(str);
        this.cache.put(str, new CacheEntry(str, mo779invoke));
        return mo779invoke;
    }

    public final void purge(Collection collection) {
        for (Map.Entry entry : this.cache.entrySet()) {
            String str = (String) entry.getKey();
            CacheEntry cacheEntry = (CacheEntry) entry.getValue();
            if (collection.contains(str)) {
                cacheEntry.resetLives();
            } else {
                NotifCollectionCache notifCollectionCache = NotifCollectionCache.this;
                if (notifCollectionCache.retainCount != 0) {
                    SystemClock systemClock = notifCollectionCache.systemClock;
                    int i = UseElapsedRealtimeForCreationTime.$r8$clinit;
                    long uptimeMillis = systemClock.uptimeMillis();
                    String str2 = cacheEntry.key;
                    NotifCollectionCache notifCollectionCache2 = NotifCollectionCache.this;
                    synchronized (str2) {
                        if (uptimeMillis - cacheEntry.lastValidPurge < notifCollectionCache2.purgeTimeoutMillis) {
                            r5 = false;
                        } else {
                            cacheEntry.lastValidPurge = uptimeMillis;
                            int i2 = cacheEntry.lives - 1;
                            cacheEntry.lives = i2;
                            r5 = i2 <= 0;
                        }
                    }
                }
                if (r5) {
                    this.cache.remove(str);
                }
            }
        }
    }

    public NotifCollectionCache(int i, long j, SystemClock systemClock) {
        this.retainCount = i;
        this.purgeTimeoutMillis = j;
        this.systemClock = systemClock;
        this.cache = new ConcurrentHashMap();
        this.misses = new AtomicInteger(0);
        this.hits = new AtomicInteger(0);
        if (i < 0) {
            throw new IllegalArgumentException("retainCount cannot be negative");
        }
    }

    public /* synthetic */ NotifCollectionCache(int i, long j, SystemClock systemClock, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 1 : i, (i2 & 2) != 0 ? 1000L : j, (i2 & 4) != 0 ? new SystemClockImpl() : systemClock);
    }
}
