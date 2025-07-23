package androidx.compose.ui.text.font;

import androidx.collection.LruCache;
import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.ui.text.platform.SynchronizedObject;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AsyncTypefaceCache {
    public final LruCache resultCache = new LruCache(16);
    public final MutableScatterMap permanentCache = ScatterMapKt.mutableScatterMapOf();
    public final SynchronizedObject cacheLock = new SynchronizedObject();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AsyncTypefaceResult {
        public final Object result;

        private /* synthetic */ AsyncTypefaceResult(Object obj) {
            this.result = obj;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ AsyncTypefaceResult m758boximpl(Object obj) {
            return new AsyncTypefaceResult(obj);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof AsyncTypefaceResult) {
                return Intrinsics.areEqual(this.result, ((AsyncTypefaceResult) obj).result);
            }
            return false;
        }

        public final int hashCode() {
            Object obj = this.result;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public final String toString() {
            return "AsyncTypefaceResult(result=" + this.result + ')';
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Key {
        public final Font font;
        public final Object loaderKey;

        public Key(Font font, Object obj) {
            this.font = font;
            this.loaderKey = obj;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return Intrinsics.areEqual(this.font, key.font) && Intrinsics.areEqual(this.loaderKey, key.loaderKey);
        }

        public final int hashCode() {
            int hashCode = this.font.hashCode() * 31;
            Object obj = this.loaderKey;
            return hashCode + (obj == null ? 0 : obj.hashCode());
        }

        public final String toString() {
            return "Key(font=" + this.font + ", loaderKey=" + this.loaderKey + ')';
        }
    }

    public static void put$default(AsyncTypefaceCache asyncTypefaceCache, Font font, PlatformFontLoader platformFontLoader, Object obj) {
        asyncTypefaceCache.getClass();
        Key key = new Key(font, platformFontLoader.getCacheKey());
        synchronized (asyncTypefaceCache.cacheLock) {
            try {
                if (obj == null) {
                    asyncTypefaceCache.permanentCache.set(key, AsyncTypefaceResult.m758boximpl(null));
                    Unit unit = Unit.INSTANCE;
                } else {
                    asyncTypefaceCache.resultCache.put(key, AsyncTypefaceResult.m758boximpl(obj));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0080 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object runCached(androidx.compose.ui.text.font.Font r6, androidx.compose.ui.text.font.PlatformFontLoader r7, kotlin.jvm.functions.Function1 r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r5 = this;
            boolean r0 = r9 instanceof androidx.compose.ui.text.font.AsyncTypefaceCache$runCached$1
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.ui.text.font.AsyncTypefaceCache$runCached$1 r0 = (androidx.compose.ui.text.font.AsyncTypefaceCache$runCached$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.text.font.AsyncTypefaceCache$runCached$1 r0 = new androidx.compose.ui.text.font.AsyncTypefaceCache$runCached$1
            r0.<init>(r5, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3e
            if (r2 != r3) goto L36
            boolean r5 = r0.Z$0
            java.lang.Object r6 = r0.L$1
            androidx.compose.ui.text.font.AsyncTypefaceCache$Key r6 = (androidx.compose.ui.text.font.AsyncTypefaceCache.Key) r6
            java.lang.Object r7 = r0.L$0
            androidx.compose.ui.text.font.AsyncTypefaceCache r7 = (androidx.compose.ui.text.font.AsyncTypefaceCache) r7
            kotlin.ResultKt.throwOnFailure(r9)
            r4 = r6
            r6 = r5
            r5 = r7
            r7 = r9
            r9 = r4
            goto L7d
        L36:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3e:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.compose.ui.text.font.AsyncTypefaceCache$Key r9 = new androidx.compose.ui.text.font.AsyncTypefaceCache$Key
            java.lang.Object r7 = r7.getCacheKey()
            r9.<init>(r6, r7)
            androidx.compose.ui.text.platform.SynchronizedObject r6 = r5.cacheLock
            monitor-enter(r6)
            androidx.collection.LruCache r7 = r5.resultCache     // Catch: java.lang.Throwable -> L60
            java.lang.Object r7 = r7.get(r9)     // Catch: java.lang.Throwable -> L60
            androidx.compose.ui.text.font.AsyncTypefaceCache$AsyncTypefaceResult r7 = (androidx.compose.ui.text.font.AsyncTypefaceCache.AsyncTypefaceResult) r7     // Catch: java.lang.Throwable -> L60
            if (r7 != 0) goto L62
            androidx.collection.MutableScatterMap r7 = r5.permanentCache     // Catch: java.lang.Throwable -> L60
            java.lang.Object r7 = r7.get(r9)     // Catch: java.lang.Throwable -> L60
            androidx.compose.ui.text.font.AsyncTypefaceCache$AsyncTypefaceResult r7 = (androidx.compose.ui.text.font.AsyncTypefaceCache.AsyncTypefaceResult) r7     // Catch: java.lang.Throwable -> L60
            goto L62
        L60:
            r5 = move-exception
            goto Laa
        L62:
            if (r7 == 0) goto L68
            java.lang.Object r5 = r7.result     // Catch: java.lang.Throwable -> L60
            monitor-exit(r6)
            return r5
        L68:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L60
            monitor-exit(r6)
            r0.L$0 = r5
            r0.L$1 = r9
            r6 = 0
            r0.Z$0 = r6
            r0.label = r3
            androidx.compose.ui.text.font.AsyncFontListLoader$load$2$typeface$1 r8 = (androidx.compose.ui.text.font.AsyncFontListLoader$load$2$typeface$1) r8
            java.lang.Object r7 = r8.mo779invoke(r0)
            if (r7 != r1) goto L7d
            return r1
        L7d:
            androidx.compose.ui.text.platform.SynchronizedObject r8 = r5.cacheLock
            monitor-enter(r8)
            if (r7 != 0) goto L8f
            androidx.collection.MutableScatterMap r5 = r5.permanentCache     // Catch: java.lang.Throwable -> L8d
            r6 = 0
            androidx.compose.ui.text.font.AsyncTypefaceCache$AsyncTypefaceResult r6 = androidx.compose.ui.text.font.AsyncTypefaceCache.AsyncTypefaceResult.m758boximpl(r6)     // Catch: java.lang.Throwable -> L8d
            r5.set(r9, r6)     // Catch: java.lang.Throwable -> L8d
            goto La4
        L8d:
            r5 = move-exception
            goto La8
        L8f:
            if (r6 == 0) goto L9b
            androidx.collection.MutableScatterMap r5 = r5.permanentCache     // Catch: java.lang.Throwable -> L8d
            androidx.compose.ui.text.font.AsyncTypefaceCache$AsyncTypefaceResult r6 = androidx.compose.ui.text.font.AsyncTypefaceCache.AsyncTypefaceResult.m758boximpl(r7)     // Catch: java.lang.Throwable -> L8d
            r5.set(r9, r6)     // Catch: java.lang.Throwable -> L8d
            goto La4
        L9b:
            androidx.collection.LruCache r5 = r5.resultCache     // Catch: java.lang.Throwable -> L8d
            androidx.compose.ui.text.font.AsyncTypefaceCache$AsyncTypefaceResult r6 = androidx.compose.ui.text.font.AsyncTypefaceCache.AsyncTypefaceResult.m758boximpl(r7)     // Catch: java.lang.Throwable -> L8d
            r5.put(r9, r6)     // Catch: java.lang.Throwable -> L8d
        La4:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L8d
            monitor-exit(r8)
            return r7
        La8:
            monitor-exit(r8)
            throw r5
        Laa:
            monitor-exit(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.font.AsyncTypefaceCache.runCached(androidx.compose.ui.text.font.Font, androidx.compose.ui.text.font.PlatformFontLoader, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
