package com.android.systemui.animation;

import android.graphics.Typeface;
import android.util.LruCache;
import com.android.systemui.animation.TypefaceVariantCache;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TypefaceVariantCacheImpl implements TypefaceVariantCache {
    public final Typeface baseTypeface;
    public final LruCache cache = new LruCache(5);
    public final FontCacheImpl fontCache;

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

    public TypefaceVariantCacheImpl(Typeface typeface, int i) {
        this.baseTypeface = typeface;
        this.fontCache = new FontCacheImpl(i);
    }

    @Override // com.android.systemui.animation.TypefaceVariantCache
    public final FontCacheImpl getFontCache() {
        return this.fontCache;
    }

    @Override // com.android.systemui.animation.TypefaceVariantCache
    public final Typeface getTypefaceForVariant(String str) {
        if (str == null) {
            return this.baseTypeface;
        }
        Typeface typeface = (Typeface) this.cache.get(str);
        if (typeface != null) {
            return typeface;
        }
        Typeface typeface2 = this.baseTypeface;
        TypefaceVariantCache.Companion.getClass();
        Typeface createVariantTypeface = TypefaceVariantCache.Companion.createVariantTypeface(typeface2, str);
        this.cache.put(str, createVariantTypeface);
        return createVariantTypeface;
    }
}
