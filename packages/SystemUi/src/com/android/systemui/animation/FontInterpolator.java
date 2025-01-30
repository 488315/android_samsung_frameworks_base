package com.android.systemui.animation;

import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import android.util.Log;
import android.util.LruCache;
import android.util.MathUtils;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.animation.FontInterpolator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FontInterpolator {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = Log.isLoggable("FontInterpolator", 3);
    public static final FontVariationAxis[] EMPTY_AXES = new FontVariationAxis[0];
    public final LruCache interpCache;
    public final InterpKey tmpInterpKey;
    public final VarFontKey tmpVarFontKey;
    public final LruCache verFontCache;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean canInterpolate(Font font, Font font2) {
            return font.getTtcIndex() == font2.getTtcIndex() && font.getSourceIdentifier() == font2.getSourceIdentifier();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InterpKey {

        /* renamed from: l */
        public Font f225l;
        public float progress;

        /* renamed from: r */
        public Font f226r;

        public InterpKey(Font font, Font font2, float f) {
            this.f225l = font;
            this.f226r = font2;
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
            return Intrinsics.areEqual(this.f225l, interpKey.f225l) && Intrinsics.areEqual(this.f226r, interpKey.f226r) && Float.compare(this.progress, interpKey.progress) == 0;
        }

        public final int hashCode() {
            Font font = this.f225l;
            int hashCode = (font == null ? 0 : font.hashCode()) * 31;
            Font font2 = this.f226r;
            return Float.hashCode(this.progress) + ((hashCode + (font2 != null ? font2.hashCode() : 0)) * 31);
        }

        public final String toString() {
            return "InterpKey(l=" + this.f225l + ", r=" + this.f226r + ", progress=" + this.progress + ")";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FontInterpolator() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final Font lerp(Font font, Font font2, final float f) {
        int i;
        FontVariationAxis fontVariationAxis;
        final FontInterpolator fontInterpolator = this;
        Font font3 = font;
        if (f == 0.0f) {
            return font3;
        }
        if (f == 1.0f) {
            return font2;
        }
        FontVariationAxis[] axes = font.getAxes();
        FontVariationAxis[] fontVariationAxisArr = EMPTY_AXES;
        if (axes == null) {
            axes = fontVariationAxisArr;
        }
        FontVariationAxis[] axes2 = font2.getAxes();
        if (axes2 != null) {
            fontVariationAxisArr = axes2;
        }
        if (axes.length == 0) {
            if (fontVariationAxisArr.length == 0) {
                return font3;
            }
        }
        InterpKey interpKey = fontInterpolator.tmpInterpKey;
        interpKey.f225l = font3;
        interpKey.f226r = font2;
        interpKey.progress = f;
        LruCache lruCache = fontInterpolator.interpCache;
        Font font4 = (Font) lruCache.get(interpKey);
        boolean z = DEBUG;
        if (font4 != null) {
            if (z) {
                Log.d("FontInterpolator", "[" + f + "] Interp. cache hit for " + interpKey);
            }
            return font4;
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
                if (Intrinsics.areEqual(str, "wght")) {
                    lerp = MathUtils.lerp(f2 != null ? f2.floatValue() : 400.0f, f3 != null ? f3.floatValue() : 400.0f, f);
                } else if (Intrinsics.areEqual(str, "ital")) {
                    FontInterpolator fontInterpolator2 = fontInterpolator;
                    float lerp2 = MathUtils.lerp(f2 != null ? f2.floatValue() : 0.0f, f3 != null ? f3.floatValue() : 0.0f, f);
                    FontInterpolator.Companion companion = FontInterpolator.Companion;
                    fontInterpolator2.getClass();
                    lerp = ((int) (RangesKt___RangesKt.coerceIn(lerp2) / 0.1f)) * 0.1f;
                } else {
                    if (!((f2 == null || f3 == null) ? false : true)) {
                        throw new IllegalArgumentException("Unable to interpolate due to unknown default axes value : ".concat(str).toString());
                    }
                    lerp = MathUtils.lerp(f2.floatValue(), f3.floatValue(), f);
                }
                return Float.valueOf(lerp);
            }
        };
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
        if (fontVariationAxisArr.length > 1) {
            Comparator comparator2 = new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$lerp$$inlined$sortBy$2
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) obj).getTag(), ((FontVariationAxis) obj2).getTag());
                }
            };
            if (fontVariationAxisArr.length > 1) {
                Arrays.sort(fontVariationAxisArr, comparator2);
            }
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= axes.length && i2 >= fontVariationAxisArr.length) {
                break;
            }
            String tag = i3 < axes.length ? axes[i3].getTag() : null;
            String tag2 = i2 < fontVariationAxisArr.length ? fontVariationAxisArr[i2].getTag() : null;
            int compareTo = tag == null ? 1 : tag2 == null ? -1 : tag.compareTo(tag2);
            if (compareTo == 0) {
                Intrinsics.checkNotNull(tag);
                i = i3 + 1;
                fontVariationAxis = new FontVariationAxis(tag, ((Number) function3.invoke(tag, Float.valueOf(axes[i3].getStyleValue()), Float.valueOf(fontVariationAxisArr[i2].getStyleValue()))).floatValue());
                i2++;
            } else if (compareTo < 0) {
                Intrinsics.checkNotNull(tag);
                int i4 = i3 + 1;
                fontVariationAxis = new FontVariationAxis(tag, ((Number) function3.invoke(tag, Float.valueOf(axes[i3].getStyleValue()), null)).floatValue());
                i = i4;
            } else {
                Intrinsics.checkNotNull(tag2);
                int i5 = i2 + 1;
                FontVariationAxis fontVariationAxis2 = new FontVariationAxis(tag2, ((Number) function3.invoke(tag2, null, Float.valueOf(fontVariationAxisArr[i2].getStyleValue()))).floatValue());
                i = i3;
                fontVariationAxis = fontVariationAxis2;
                i2 = i5;
            }
            arrayList.add(fontVariationAxis);
            font3 = font;
            i3 = i;
            fontInterpolator = this;
        }
        VarFontKey varFontKey = fontInterpolator.tmpVarFontKey;
        varFontKey.getClass();
        varFontKey.sourceId = font.getSourceIdentifier();
        varFontKey.index = font.getTtcIndex();
        List list = varFontKey.sortedAxes;
        list.clear();
        list.addAll(arrayList);
        if (list.size() > 1) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(list, new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$VarFontKey$set$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) obj).getTag(), ((FontVariationAxis) obj2).getTag());
                }
            });
        }
        LruCache lruCache2 = fontInterpolator.verFontCache;
        Font font5 = (Font) lruCache2.get(varFontKey);
        if (font5 != null) {
            lruCache.put(new InterpKey(font3, font2, f), font5);
            if (z) {
                Log.d("FontInterpolator", "[" + f + "] Axis cache hit for " + varFontKey);
            }
            return font5;
        }
        Font build = new Font.Builder(font3).setFontVariationSettings((FontVariationAxis[]) arrayList.toArray(new FontVariationAxis[0])).build();
        lruCache.put(new InterpKey(font3, font2, f), build);
        lruCache2.put(new VarFontKey(font3, arrayList), build);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return this.sortedAxes.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.index, Integer.hashCode(this.sourceId) * 31, 31);
        }

        public final String toString() {
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("VarFontKey(sourceId=", this.sourceId, ", index=", this.index, ", sortedAxes=");
            m45m.append(this.sortedAxes);
            m45m.append(")");
            return m45m.toString();
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public VarFontKey(Font font, List<FontVariationAxis> list) {
            this(r0, r4, r1);
            int sourceIdentifier = font.getSourceIdentifier();
            int ttcIndex = font.getTtcIndex();
            ArrayList arrayList = new ArrayList(list);
            if (arrayList.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$VarFontKey$_init_$lambda$1$$inlined$sortBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) obj).getTag(), ((FontVariationAxis) obj2).getTag());
                    }
                });
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public /* synthetic */ FontInterpolator(Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : num);
    }
}
