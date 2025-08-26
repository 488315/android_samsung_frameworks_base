package androidx.compose.ui.text.font;

import androidx.collection.LruCache;
import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.ui.text.platform.SynchronizedObject;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AsyncTypefaceCache {
    public final LruCache resultCache = new LruCache(16);
    public final MutableScatterMap permanentCache = ScatterMapKt.mutableScatterMapOf();
    public final SynchronizedObject cacheLock = new SynchronizedObject();

    public final class AsyncTypefaceResult {
        public final Object result;

        private /* synthetic */ AsyncTypefaceResult(Object obj) {
            this.result = obj;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ AsyncTypefaceResult m760boximpl(Object obj) {
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
            int iHashCode = this.font.hashCode() * 31;
            Object obj = this.loaderKey;
            return iHashCode + (obj == null ? 0 : obj.hashCode());
        }

        public final String toString() {
            return "Key(font=" + this.font + ", loaderKey=" + this.loaderKey + ')';
        }
    }

    /* renamed from: androidx.compose.ui.text.font.AsyncTypefaceCache$runCached$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AsyncTypefaceCache.this.runCached(null, null, null, this);
        }
    }

    public static void put$default(AsyncTypefaceCache asyncTypefaceCache, Font font, PlatformFontLoader platformFontLoader, Object obj) {
        asyncTypefaceCache.getClass();
        Key key = new Key(font, platformFontLoader.getCacheKey());
        synchronized (asyncTypefaceCache.cacheLock) {
            try {
                if (obj == null) {
                    asyncTypefaceCache.permanentCache.set(key, AsyncTypefaceResult.m760boximpl(null));
                    Unit unit = Unit.INSTANCE;
                } else {
                    asyncTypefaceCache.resultCache.put(key, AsyncTypefaceResult.m760boximpl(obj));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object runCached(Font font, PlatformFontLoader platformFontLoader, Function1 function1, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Key key;
        boolean z;
        Object objMo781invoke;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            key = new Key(font, platformFontLoader.getCacheKey());
            synchronized (this.cacheLock) {
                try {
                    AsyncTypefaceResult asyncTypefaceResult = (AsyncTypefaceResult) this.resultCache.get(key);
                    if (asyncTypefaceResult == null) {
                        asyncTypefaceResult = (AsyncTypefaceResult) this.permanentCache.get(key);
                    }
                    if (asyncTypefaceResult != null) {
                        return asyncTypefaceResult.result;
                    }
                    Unit unit = Unit.INSTANCE;
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = key;
                    z = false;
                    anonymousClass1.Z$0 = false;
                    anonymousClass1.label = 1;
                    objMo781invoke = ((AsyncFontListLoader$load$2$typeface$1) function1).mo781invoke(anonymousClass1);
                    if (objMo781invoke == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } finally {
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            boolean z2 = anonymousClass1.Z$0;
            Key key2 = (Key) anonymousClass1.L$1;
            AsyncTypefaceCache asyncTypefaceCache = (AsyncTypefaceCache) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            z = z2;
            this = asyncTypefaceCache;
            objMo781invoke = obj;
            key = key2;
        }
        synchronized (this.cacheLock) {
            try {
                if (objMo781invoke == null) {
                    this.permanentCache.set(key, AsyncTypefaceResult.m760boximpl(null));
                } else if (z) {
                    this.permanentCache.set(key, AsyncTypefaceResult.m760boximpl(objMo781invoke));
                } else {
                    this.resultCache.put(key, AsyncTypefaceResult.m760boximpl(objMo781invoke));
                }
                Unit unit2 = Unit.INSTANCE;
            } finally {
            }
        }
        return objMo781invoke;
    }
}
