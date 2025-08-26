package com.android.systemui.shared.clocks;

import android.graphics.Typeface;
import com.android.systemui.animation.FontCacheImpl;
import com.android.systemui.animation.TypefaceVariantCache;
import com.android.systemui.shared.clocks.TypefaceCache;
import java.util.LinkedHashMap;

/* loaded from: classes3.dex */
public final class TypefaceCache$getVariantCache$1 implements TypefaceVariantCache {
    public final /* synthetic */ Typeface $baseTypeface;
    public final /* synthetic */ String $res;
    public final FontCacheImpl fontCache;
    public final /* synthetic */ TypefaceCache this$0;

    public TypefaceCache$getVariantCache$1(TypefaceCache typefaceCache, String str, Typeface typeface) {
        this.this$0 = typefaceCache;
        this.$res = str;
        this.$baseTypeface = typeface;
        this.fontCache = typefaceCache.fontCache;
    }

    @Override // com.android.systemui.animation.TypefaceVariantCache
    public final FontCacheImpl getFontCache() {
        return this.fontCache;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.animation.TypefaceVariantCache
    public final Typeface getTypefaceForVariant(String str) {
        Typeface typeface;
        int i = TypefaceCache.$r8$clinit;
        TypefaceCache typefaceCache = this.this$0;
        typefaceCache.checkQueue();
        TypefaceCache.CacheKey cacheKey = new TypefaceCache.CacheKey(this.$res, str);
        TypefaceCache.WeakTypefaceRef weakTypefaceRef = (TypefaceCache.WeakTypefaceRef) ((LinkedHashMap) typefaceCache.cache).get(cacheKey);
        if (weakTypefaceRef != null && (typeface = (Typeface) weakTypefaceRef.get()) != null) {
            return typeface;
        }
        typefaceCache.logMiss(cacheKey);
        Typeface typeface2 = this.$baseTypeface;
        TypefaceVariantCache.Companion.getClass();
        Typeface typefaceCreateVariantTypeface = TypefaceVariantCache.Companion.createVariantTypeface(typeface2, str);
        typefaceCache.cache.put(cacheKey, new TypefaceCache.WeakTypefaceRef(typefaceCache, cacheKey, typefaceCreateVariantTypeface));
        return typefaceCreateVariantTypeface;
    }
}
