package com.android.systemui.statusbar.notification.collection;

import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotifCollectionCache implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ConcurrentHashMap cache;
    public final AtomicInteger hits;
    public final AtomicInteger misses;
    public final long purgeTimeoutMillis;
    public final int retainCount;
    public final SystemClock systemClock;

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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x00bc, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00c5, code lost:
    
        throw r3;
     */
    @Override // com.android.systemui.Dumpable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.println("NotifCollectionCache(retainCount = " + this.retainCount + ", purgeTimeoutMillis = " + this.purgeTimeoutMillis + ")");
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            Stream stream = this.cache.values().stream();
            final NotifCollectionCache$$ExternalSyntheticLambda0 notifCollectionCache$$ExternalSyntheticLambda0 = new NotifCollectionCache$$ExternalSyntheticLambda0();
            List list = stream.map(new Function() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollectionCache$sam$java_util_function_Function$0
                @Override // java.util.function.Function
                public final /* synthetic */ Object apply(Object obj) {
                    return notifCollectionCache$$ExternalSyntheticLambda0.mo781invoke(obj);
                }
            }).sorted().toList();
            indentingPrintWriterAsIndenting.append("entries present in cache").append((CharSequence) ": ").println(list.size());
            indentingPrintWriterAsIndenting.increaseIndent();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                indentingPrintWriterAsIndenting.println(it.next());
            }
            indentingPrintWriterAsIndenting.decreaseIndent();
            int i = this.misses.get();
            int i2 = this.hits.get();
            indentingPrintWriterAsIndenting.println("cache hit ratio = " + ((i2 / (i2 + i)) * 100) + "% (" + i2 + " hits, " + i + " misses)");
        } finally {
        }
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
        Object objMo781invoke = function1.mo781invoke(str);
        this.cache.put(str, new CacheEntry(str, objMo781invoke));
        return objMo781invoke;
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
                    long jUptimeMillis = systemClock.uptimeMillis();
                    String str2 = cacheEntry.key;
                    NotifCollectionCache notifCollectionCache2 = NotifCollectionCache.this;
                    synchronized (str2) {
                        if (jUptimeMillis - cacheEntry.lastValidPurge < notifCollectionCache2.purgeTimeoutMillis) {
                            z = false;
                        } else {
                            cacheEntry.lastValidPurge = jUptimeMillis;
                            int i2 = cacheEntry.lives - 1;
                            cacheEntry.lives = i2;
                            z = i2 <= 0;
                        }
                    }
                }
                if (z) {
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
