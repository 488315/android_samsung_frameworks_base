package com.android.systemui.animation;

import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import android.util.Log;
import android.util.LruCache;
import android.util.MathUtils;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import com.android.systemui.animation.FontInterpolator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class FontInterpolator {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = Log.isLoggable("FontInterpolator", 3);
    public static final FontVariationAxis[] EMPTY_AXES = new FontVariationAxis[0];
    public final LruCache interpCache;
    public final InterpKey tmpInterpKey;
    public final VarFontKey tmpVarFontKey;
    public final LruCache verFontCache;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static boolean canInterpolate(Font font, Font font2) {
            return font.getTtcIndex() == font2.getTtcIndex() && font.getSourceIdentifier() == font2.getSourceIdentifier();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class InterpKey {
        public Font l;
        public float progress;
        public Font r;

        public InterpKey(Font font, Font font2, float f) {
            this.l = font;
            this.r = font2;
            this.progress = f;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InterpKey)) {
                return false;
            }
            InterpKey interpKey = (InterpKey) obj;
            return Intrinsics.areEqual(this.l, interpKey.l) && Intrinsics.areEqual(this.r, interpKey.r) && Float.compare(this.progress, interpKey.progress) == 0;
        }

        public final int hashCode() {
            Font font = this.l;
            int hashCode = (font == null ? 0 : font.hashCode()) * 31;
            Font font2 = this.r;
            return Float.hashCode(this.progress) + ((hashCode + (font2 != null ? font2.hashCode() : 0)) * 31);
        }

        public final String toString() {
            Font font = this.l;
            Font font2 = this.r;
            float f = this.progress;
            StringBuilder sb = new StringBuilder("InterpKey(l=");
            sb.append(font);
            sb.append(", r=");
            sb.append(font2);
            sb.append(", progress=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(sb, f, ")");
        }
    }

    public FontInterpolator() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final Font lerp(Font font, Font font2, final float f) {
        int i;
        FontVariationAxis fontVariationAxis;
        final FontInterpolator fontInterpolator = this;
        if (f == 0.0f) {
            return font;
        }
        if (f == 1.0f) {
            return font2;
        }
        FontVariationAxis[] axes = font.getAxes();
        if (axes == null) {
            axes = EMPTY_AXES;
        }
        FontVariationAxis[] axes2 = font2.getAxes();
        if (axes2 == null) {
            axes2 = EMPTY_AXES;
        }
        if (axes.length == 0 && axes2.length == 0) {
            return font;
        }
        InterpKey interpKey = fontInterpolator.tmpInterpKey;
        interpKey.l = font;
        interpKey.r = font2;
        interpKey.progress = f;
        Font font3 = (Font) fontInterpolator.interpCache.get(interpKey);
        boolean z = DEBUG;
        if (font3 != null) {
            if (z) {
                Log.d("FontInterpolator", "[" + f + "] Interp. cache hit for " + interpKey);
            }
            return font3;
        }
        Function3 function3 = new Function3() { // from class: com.android.systemui.animation.FontInterpolator$lerp$newAxes$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                float lerp;
                String str = (String) obj;
                Float f2 = (Float) obj2;
                Float f3 = (Float) obj3;
                if (str.equals("wght")) {
                    lerp = MathUtils.lerp(f2 != null ? f2.floatValue() : 400.0f, f3 != null ? f3.floatValue() : 400.0f, f);
                } else if (str.equals("ital")) {
                    FontInterpolator fontInterpolator2 = fontInterpolator;
                    float lerp2 = MathUtils.lerp(f2 != null ? f2.floatValue() : 0.0f, f3 != null ? f3.floatValue() : 0.0f, f);
                    FontInterpolator.Companion companion = FontInterpolator.Companion;
                    fontInterpolator2.getClass();
                    lerp = ((int) (RangesKt___RangesKt.coerceIn(lerp2, 0.0f, 1.0f) / 0.1f)) * 0.1f;
                } else {
                    if (f2 == null || f3 == null) {
                        throw new IllegalArgumentException("Unable to interpolate due to unknown default axes value : ".concat(str).toString());
                    }
                    lerp = MathUtils.lerp(f2.floatValue(), f3.floatValue(), f);
                }
                return Float.valueOf(lerp);
            }
        };
        int i2 = 1;
        if (axes.length > 1) {
            Comparator comparator = new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$lerp$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) obj).getTag(), ((FontVariationAxis) obj2).getTag());
                }
            };
            if (axes.length > 1) {
                Arrays.sort(axes, comparator);
            }
        }
        if (axes2.length > 1) {
            Comparator comparator2 = new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$lerp$$inlined$sortBy$2
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) obj).getTag(), ((FontVariationAxis) obj2).getTag());
                }
            };
            if (axes2.length > 1) {
                Arrays.sort(axes2, comparator2);
            }
        }
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= axes.length && i4 >= axes2.length) {
                break;
            }
            String tag = i3 < axes.length ? axes[i3].getTag() : null;
            String tag2 = i4 < axes2.length ? axes2[i4].getTag() : null;
            int compareTo = tag == null ? 1 : tag2 == null ? -1 : tag.compareTo(tag2);
            if (compareTo == 0) {
                Intrinsics.checkNotNull(tag);
                int i5 = i4 + 1;
                fontVariationAxis = new FontVariationAxis(tag, ((Number) function3.invoke(tag, Float.valueOf(axes[i3].getStyleValue()), Float.valueOf(axes2[i4].getStyleValue()))).floatValue());
                i3++;
                i = i5;
            } else if (compareTo < 0) {
                Intrinsics.checkNotNull(tag);
                i = i4;
                fontVariationAxis = new FontVariationAxis(tag, ((Number) function3.invoke(tag, Float.valueOf(axes[i3].getStyleValue()), null)).floatValue());
                i3++;
            } else {
                Intrinsics.checkNotNull(tag2);
                i = i4 + 1;
                fontVariationAxis = new FontVariationAxis(tag2, ((Number) function3.invoke(tag2, null, Float.valueOf(axes2[i4].getStyleValue()))).floatValue());
            }
            arrayList.add(fontVariationAxis);
            i4 = i;
            i2 = 1;
            fontInterpolator = this;
        }
        VarFontKey varFontKey = fontInterpolator.tmpVarFontKey;
        varFontKey.getClass();
        varFontKey.sourceId = font.getSourceIdentifier();
        varFontKey.index = font.getTtcIndex();
        varFontKey.sortedAxes.clear();
        varFontKey.sortedAxes.addAll(arrayList);
        List list = varFontKey.sortedAxes;
        if (list.size() > i2) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(list, new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$VarFontKey$set$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) obj).getTag(), ((FontVariationAxis) obj2).getTag());
                }
            });
        }
        Font font4 = (Font) fontInterpolator.verFontCache.get(varFontKey);
        if (font4 != null) {
            fontInterpolator.interpCache.put(new InterpKey(font, font2, f), font4);
            if (z) {
                Log.d("FontInterpolator", "[" + f + "] Axis cache hit for " + varFontKey);
            }
            return font4;
        }
        Font build = new Font.Builder(font).setFontVariationSettings((FontVariationAxis[]) arrayList.toArray(new FontVariationAxis[0])).build();
        fontInterpolator.interpCache.put(new InterpKey(font, font2, f), build);
        fontInterpolator.verFontCache.put(new VarFontKey(font, arrayList), build);
        Log.e("FontInterpolator", "[" + f + "] Cache MISS for " + interpKey + " / " + varFontKey);
        return build;
    }

    public FontInterpolator(Integer num) {
        int intValue = num != null ? num.intValue() * 2 : 10;
        this.interpCache = new LruCache(intValue);
        this.verFontCache = new LruCache(intValue);
        this.tmpInterpKey = new InterpKey(null, null, 0.0f);
        this.tmpVarFontKey = new VarFontKey(0, 0, new ArrayList());
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class VarFontKey {
        public int index;
        public final List sortedAxes;
        public int sourceId;

        public VarFontKey(int i, int i2, List<FontVariationAxis> list) {
            this.sourceId = i;
            this.index = i2;
            this.sortedAxes = list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof VarFontKey)) {
                return false;
            }
            VarFontKey varFontKey = (VarFontKey) obj;
            return this.sourceId == varFontKey.sourceId && this.index == varFontKey.index && Intrinsics.areEqual(this.sortedAxes, varFontKey.sortedAxes);
        }

        public final int hashCode() {
            return this.sortedAxes.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.index, Integer.hashCode(this.sourceId) * 31, 31);
        }

        public final String toString() {
            int i = this.sourceId;
            int i2 = this.index;
            List list = this.sortedAxes;
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "VarFontKey(sourceId=", ", index=", ", sortedAxes=");
            m.append(list);
            m.append(")");
            return m.toString();
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public VarFontKey(android.graphics.fonts.Font r4, java.util.List<android.graphics.fonts.FontVariationAxis> r5) {
            /*
                r3 = this;
                int r0 = r4.getSourceIdentifier()
                int r4 = r4.getTtcIndex()
                java.util.Collection r5 = (java.util.Collection) r5
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>(r5)
                int r5 = r1.size()
                r2 = 1
                if (r5 <= r2) goto L1e
                com.android.systemui.animation.FontInterpolator$VarFontKey$_init_$lambda$1$$inlined$sortBy$1 r5 = new com.android.systemui.animation.FontInterpolator$VarFontKey$_init_$lambda$1$$inlined$sortBy$1
                r5.<init>()
                kotlin.collections.CollectionsKt__MutableCollectionsJVMKt.sortWith(r1, r5)
            L1e:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                r3.<init>(r0, r4, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.FontInterpolator.VarFontKey.<init>(android.graphics.fonts.Font, java.util.List):void");
        }
    }

    public /* synthetic */ FontInterpolator(Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : num);
    }
}
