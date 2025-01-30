package com.android.systemui.animation;

import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.util.LruCache;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TypefaceVariantCacheImpl implements TypefaceVariantCache {
    public final Typeface baseTypeface;
    public final LruCache cache = new LruCache(5);

    public TypefaceVariantCacheImpl(Typeface typeface) {
        this.baseTypeface = typeface;
    }

    public final Typeface getTypefaceForVariant(String str) {
        final Typeface typeface = this.baseTypeface;
        if (str == null) {
            return typeface;
        }
        LruCache lruCache = this.cache;
        Typeface typeface2 = (Typeface) lruCache.get(str);
        if (typeface2 != null) {
            return typeface2;
        }
        TypefaceVariantCache.Companion.getClass();
        if (!(str.length() == 0)) {
            List mutableList = ArraysKt___ArraysKt.toMutableList(FontVariationAxis.fromFontVariationSettings(str));
            ArrayList arrayList = (ArrayList) mutableList;
            arrayList.removeIf(new Predicate() { // from class: com.android.systemui.animation.TypefaceVariantCache$Companion$createVariantTypeface$1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return !typeface.isSupportedAxes(((FontVariationAxis) obj).getOpenTypeTagValue());
                }
            });
            if (!arrayList.isEmpty()) {
                typeface = Typeface.createFromTypefaceWithVariation(typeface, mutableList);
            }
        }
        lruCache.put(str, typeface);
        return typeface;
    }
}
