package com.android.systemui.animation;

import android.graphics.Typeface;
import android.util.LruCache;
import com.android.systemui.animation.TypefaceVariantCache;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TypefaceVariantCacheImpl implements TypefaceVariantCache {
    public final Typeface baseTypeface;
    public final LruCache cache = new LruCache(5);
    public final FontCacheImpl fontCache;

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
        Typeface typefaceCreateVariantTypeface = TypefaceVariantCache.Companion.createVariantTypeface(typeface2, str);
        this.cache.put(str, typefaceCreateVariantTypeface);
        return typefaceCreateVariantTypeface;
    }
}
