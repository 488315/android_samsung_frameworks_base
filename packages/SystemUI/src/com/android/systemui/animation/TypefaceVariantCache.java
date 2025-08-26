package com.android.systemui.animation;

import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import com.android.systemui.animation.TypefaceVariantCache;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public interface TypefaceVariantCache {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public static Typeface createVariantTypeface(final Typeface typeface, String str) {
            if (str == null || str.length() == 0) {
                return typeface;
            }
            FontVariationAxis[] fontVariationAxisArrFromFontVariationSettings = FontVariationAxis.fromFontVariationSettings(str);
            List mutableList = fontVariationAxisArrFromFontVariationSettings != null ? ArraysKt___ArraysKt.toMutableList(fontVariationAxisArrFromFontVariationSettings) : new ArrayList();
            final Function1 function1 = new Function1() { // from class: com.android.systemui.animation.TypefaceVariantCache$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    Typeface typeface2 = typeface;
                    TypefaceVariantCache.Companion companion = TypefaceVariantCache.Companion.$$INSTANCE;
                    return Boolean.valueOf(!typeface2.isSupportedAxes(((FontVariationAxis) obj).getOpenTypeTagValue()));
                }
            };
            mutableList.removeIf(new Predicate() { // from class: com.android.systemui.animation.TextAnimatorKt$sam$java_util_function_Predicate$0
                @Override // java.util.function.Predicate
                public final /* synthetic */ boolean test(Object obj) {
                    return ((Boolean) function1.mo781invoke(obj)).booleanValue();
                }
            });
            return mutableList.isEmpty() ? typeface : Typeface.createFromTypefaceWithVariation(typeface, mutableList);
        }
    }

    FontCacheImpl getFontCache();

    Typeface getTypefaceForVariant(String str);
}
