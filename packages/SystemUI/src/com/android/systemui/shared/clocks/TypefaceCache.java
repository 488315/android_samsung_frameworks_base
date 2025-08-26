package com.android.systemui.shared.clocks;

import android.graphics.Typeface;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.animation.FontCacheImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import com.android.systemui.shared.clocks.TypefaceCache;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.ConstrainedOnceSequence;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.GeneratorSequence;
import kotlin.sequences.SequencesKt__SequencesKt$$ExternalSyntheticLambda1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public final class TypefaceCache {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Map cache;
    public final FontCacheImpl fontCache;
    public final Logger logger;
    public final ReferenceQueue queue;
    public int totalEvictions;
    public int totalMisses;
    public final Function1 typefaceFactory;

    public final class CacheKey {
        public final String fvar;
        public final String res;

        public CacheKey(String str, String str2) {
            this.res = str;
            this.fvar = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CacheKey)) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) obj;
            return Intrinsics.areEqual(this.res, cacheKey.res) && Intrinsics.areEqual(this.fvar, cacheKey.fvar);
        }

        public final int hashCode() {
            int iHashCode = this.res.hashCode() * 31;
            String str = this.fvar;
            return iHashCode + (str == null ? 0 : str.hashCode());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CacheKey(res=");
            sb.append(this.res);
            sb.append(", fvar=");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.fvar, ")");
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class WeakTypefaceRef extends WeakReference {
        public final CacheKey key;

        public WeakTypefaceRef(TypefaceCache typefaceCache, CacheKey cacheKey, Typeface typeface) {
            super(typeface, typefaceCache.queue);
            this.key = cacheKey;
        }
    }

    static {
        new Companion(null);
    }

    public TypefaceCache(MessageBuffer messageBuffer, int i, Function1 function1) {
        this.typefaceFactory = function1;
        String simpleName = Reflection.getOrCreateKotlinClass(TypefaceCache.class).getSimpleName();
        simpleName.getClass();
        this.logger = new Logger(messageBuffer, simpleName);
        this.cache = new LinkedHashMap();
        this.queue = new ReferenceQueue();
        this.fontCache = new FontCacheImpl(i);
    }

    public final void checkQueue() {
        Function0 function0 = new Function0() { // from class: com.android.systemui.shared.clocks.TypefaceCache$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.queue.poll();
            }
        };
        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(new ConstrainedOnceSequence(new GeneratorSequence(function0, new SequencesKt__SequencesKt$$ExternalSyntheticLambda1(function0))), new Function1() { // from class: com.android.systemui.shared.clocks.TypefaceCache$checkQueue$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(obj instanceof TypefaceCache.WeakTypefaceRef);
            }
        }).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            WeakTypefaceRef weakTypefaceRef = (WeakTypefaceRef) anonymousClass1.next();
            CacheKey cacheKey = weakTypefaceRef.key;
            this.totalEvictions++;
            Logger logger = this.logger;
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new TypefaceCache$$ExternalSyntheticLambda1(1), null);
            logMessageObtain.setStr1(cacheKey.toString());
            logMessageObtain.setInt1(this.totalEvictions);
            logger.getBuffer().commit(logMessageObtain);
            this.cache.remove(weakTypefaceRef.key);
        }
    }

    public final void logMiss(CacheKey cacheKey) {
        this.totalMisses++;
        Logger logger = this.logger;
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, new TypefaceCache$$ExternalSyntheticLambda1(0), null);
        logMessageObtain.setStr1(cacheKey.toString());
        logMessageObtain.setInt1(this.totalMisses);
        logger.getBuffer().commit(logMessageObtain);
    }
}
