package com.android.systemui.animation;

import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.util.LruCache;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class TypefaceVariantCacheImpl implements TypefaceVariantCache {
    public final Typeface baseTypeface;
    public final LruCache cache = new LruCache(5);

    public TypefaceVariantCacheImpl(Typeface typeface) {
        this.baseTypeface = typeface;
    }

    public final Typeface getTypefaceForVariant(String str) {
        if (str == null) {
            return this.baseTypeface;
        }
        Typeface typeface = (Typeface) this.cache.get(str);
        if (typeface != null) {
            return typeface;
        }
        final Typeface typeface2 = this.baseTypeface;
        TypefaceVariantCache.Companion.getClass();
        if (str.length() != 0) {
            FontVariationAxis[] fromFontVariationSettings = FontVariationAxis.fromFontVariationSettings(str);
            List mutableList = fromFontVariationSettings != null ? ArraysKt___ArraysKt.toMutableList(fromFontVariationSettings) : new ArrayList();
            mutableList.removeIf(new Predicate() { // from class: com.android.systemui.animation.TypefaceVariantCache$Companion$createVariantTypeface$1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return !typeface2.isSupportedAxes(((FontVariationAxis) obj).getOpenTypeTagValue());
                }
            });
            if (!mutableList.isEmpty()) {
                typeface2 = Typeface.createFromTypefaceWithVariation(typeface2, mutableList);
            }
        }
        this.cache.put(str, typeface2);
        return typeface2;
    }
}
