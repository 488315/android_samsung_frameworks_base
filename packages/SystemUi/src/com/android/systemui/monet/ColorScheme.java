package com.android.systemui.monet;

import android.app.WallpaperColors;
import android.graphics.Color;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.internal.graphics.cam.Cam;
import com.android.internal.graphics.cam.CamUtils;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ColorScheme {
    public static final Companion Companion = new Companion(null);
    public final TonalPalette accent1;
    public final TonalPalette accent2;
    public final TonalPalette accent3;
    public final boolean darkTheme;
    public final TonalPalette neutral1;
    public final TonalPalette neutral2;
    public final int seed;
    public final Style style;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final String access$humanReadable(Companion companion, String str, List list) {
            companion.getClass();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                int intValue = ((Number) it.next()).intValue();
                ColorScheme.Companion.getClass();
                arrayList.add(stringForColor(intValue));
            }
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, "\n", CollectionsKt___CollectionsKt.joinToString$default(arrayList, "\n", null, null, new Function1() { // from class: com.android.systemui.monet.ColorScheme$Companion$humanReadable$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return (String) obj;
                }
            }, 30));
        }

        /* JADX WARN: Code restructure failed: missing block: B:136:0x0380, code lost:
        
            if (r3 == 15) goto L146;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static List getSeedColors(WallpaperColors wallpaperColors, boolean z) {
            LinkedHashMap linkedHashMap;
            Object obj;
            double chroma;
            double d;
            Iterator it = wallpaperColors.getAllColors().values().iterator();
            if (!it.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next = it.next();
            while (it.hasNext()) {
                next = Integer.valueOf(((Integer) it.next()).intValue() + ((Integer) next).intValue());
            }
            double intValue = ((Number) next).intValue();
            boolean z2 = intValue == 0.0d;
            if (z2) {
                List mainColors = wallpaperColors.getMainColors();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(mainColors, 10));
                Iterator it2 = mainColors.iterator();
                while (it2.hasNext()) {
                    arrayList.add(Integer.valueOf(((Color) it2.next()).toArgb()));
                }
                List distinct = CollectionsKt___CollectionsKt.distinct(arrayList);
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : distinct) {
                    if (!z || Cam.fromInt(((Number) obj2).intValue()).getChroma() >= 5.0f) {
                        arrayList2.add(obj2);
                    }
                }
                List list = CollectionsKt___CollectionsKt.toList(arrayList2);
                return list.isEmpty() ? Collections.singletonList(-14979341) : list;
            }
            Map allColors = wallpaperColors.getAllColors();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(allColors.size()));
            Iterator it3 = allColors.entrySet().iterator();
            while (it3.hasNext()) {
                linkedHashMap2.put(((Map.Entry) it3.next()).getKey(), Double.valueOf(((Number) r11.getValue()).intValue() / intValue));
            }
            Map allColors2 = wallpaperColors.getAllColors();
            LinkedHashMap linkedHashMap3 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(allColors2.size()));
            for (Map.Entry entry : allColors2.entrySet()) {
                linkedHashMap3.put(entry.getKey(), Cam.fromInt(((Number) entry.getKey()).intValue()));
            }
            ArrayList arrayList3 = new ArrayList(360);
            for (int i = 0; i < 360; i++) {
                arrayList3.add(Double.valueOf(0.0d));
            }
            ArrayList arrayList4 = new ArrayList(arrayList3);
            for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
                Object obj3 = linkedHashMap2.get(entry2.getKey());
                Intrinsics.checkNotNull(obj3);
                double doubleValue = ((Number) obj3).doubleValue();
                Object obj4 = linkedHashMap3.get(entry2.getKey());
                Intrinsics.checkNotNull(obj4);
                Cam cam = (Cam) obj4;
                int roundToInt = MathKt__MathJVMKt.roundToInt(cam.getHue()) % 360;
                if (!z || cam.getChroma() > 5.0f) {
                    arrayList4.set(roundToInt, Double.valueOf(((Number) arrayList4.get(roundToInt)).doubleValue() + doubleValue));
                }
            }
            Map allColors3 = wallpaperColors.getAllColors();
            LinkedHashMap linkedHashMap4 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(allColors3.size()));
            for (Map.Entry entry3 : allColors3.entrySet()) {
                Object key = entry3.getKey();
                Object obj5 = linkedHashMap3.get(entry3.getKey());
                Intrinsics.checkNotNull(obj5);
                int roundToInt2 = MathKt__MathJVMKt.roundToInt(((Cam) obj5).getHue());
                int i2 = roundToInt2 - 15;
                int i3 = roundToInt2 + 15;
                double d2 = 0.0d;
                if (i2 <= i3) {
                    while (true) {
                        ColorScheme.Companion.getClass();
                        d2 = ((Number) arrayList4.get(i2 < 0 ? (i2 % 360) + 360 : i2 >= 360 ? i2 % 360 : i2)).doubleValue() + d2;
                        if (i2 != i3) {
                            i2++;
                        }
                    }
                }
                linkedHashMap4.put(key, Double.valueOf(d2));
            }
            if (z) {
                linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry4 : linkedHashMap3.entrySet()) {
                    Cam cam2 = (Cam) entry4.getValue();
                    Object obj6 = linkedHashMap4.get(entry4.getKey());
                    Intrinsics.checkNotNull(obj6);
                    if (cam2.getChroma() >= 5.0f && (z2 || ((Number) obj6).doubleValue() > 0.01d)) {
                        linkedHashMap.put(entry4.getKey(), entry4.getValue());
                    }
                }
            } else {
                linkedHashMap = linkedHashMap3;
            }
            LinkedHashMap linkedHashMap5 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(linkedHashMap.size()));
            for (Map.Entry entry5 : linkedHashMap.entrySet()) {
                Object key2 = entry5.getKey();
                Companion companion = ColorScheme.Companion;
                Cam cam3 = (Cam) entry5.getValue();
                Object obj7 = linkedHashMap4.get(entry5.getKey());
                Intrinsics.checkNotNull(obj7);
                double doubleValue2 = ((Number) obj7).doubleValue();
                companion.getClass();
                double d3 = doubleValue2 * 70.0d;
                if (cam3.getChroma() < 48.0f) {
                    chroma = cam3.getChroma() - 48.0f;
                    d = 0.1d;
                } else {
                    chroma = cam3.getChroma() - 48.0f;
                    d = 0.3d;
                }
                linkedHashMap5.put(key2, Double.valueOf((chroma * d) + d3));
            }
            ArrayList arrayList5 = new ArrayList(linkedHashMap5.entrySet());
            if (arrayList5.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList5, new Comparator() { // from class: com.android.systemui.monet.ColorScheme$Companion$getSeedColors$$inlined$sortByDescending$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj8, Object obj9) {
                        return ComparisonsKt__ComparisonsKt.compareValues((Double) ((Map.Entry) obj9).getValue(), (Double) ((Map.Entry) obj8).getValue());
                    }
                });
            }
            ArrayList arrayList6 = new ArrayList();
            int i4 = 90;
            loop10: while (true) {
                arrayList6.clear();
                Iterator it4 = arrayList5.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        break;
                    }
                    Integer num = (Integer) ((Map.Entry) it4.next()).getKey();
                    Iterator it5 = arrayList6.iterator();
                    while (true) {
                        if (!it5.hasNext()) {
                            obj = null;
                            break;
                        }
                        obj = it5.next();
                        int intValue2 = ((Number) obj).intValue();
                        Object obj8 = linkedHashMap3.get(num);
                        Intrinsics.checkNotNull(obj8);
                        float hue = ((Cam) obj8).getHue();
                        Object obj9 = linkedHashMap3.get(Integer.valueOf(intValue2));
                        Intrinsics.checkNotNull(obj9);
                        float hue2 = ((Cam) obj9).getHue();
                        ColorScheme.Companion.getClass();
                        if (180.0f - Math.abs(Math.abs(hue - hue2) - 180.0f) < ((float) i4)) {
                            break;
                        }
                    }
                    if (!(obj != null)) {
                        arrayList6.add(num);
                        if (arrayList6.size() >= 4) {
                            break loop10;
                        }
                    }
                }
                i4--;
            }
            if (arrayList6.isEmpty()) {
                arrayList6.add(-14979341);
            }
            return arrayList6;
        }

        public static String stringForColor(int i) {
            Cam fromInt = Cam.fromInt(i);
            return KeyAttributes$$ExternalSyntheticOutline0.m21m(ImsProfile.TIMER_NAME_H, StringsKt__StringsKt.padEnd$default(String.valueOf(MathKt__MathJVMKt.roundToInt(fromInt.getHue())))) + KeyAttributes$$ExternalSyntheticOutline0.m21m(ImsProfile.TIMER_NAME_C, StringsKt__StringsKt.padEnd$default(String.valueOf(MathKt__MathJVMKt.roundToInt(fromInt.getChroma())))) + KeyAttributes$$ExternalSyntheticOutline0.m21m("T", StringsKt__StringsKt.padEnd$default(String.valueOf(MathKt__MathJVMKt.roundToInt(CamUtils.lstarFromInt(i))))) + " = #" + StringsKt__StringsKt.padStart(Integer.toHexString(i & 16777215), 6).toUpperCase(Locale.ROOT);
        }

        public static double wrapDegreesDouble(double d) {
            if (d >= 0.0d) {
                return d >= 360.0d ? d % 360 : d;
            }
            double d2 = 360;
            return (d % d2) + d2;
        }
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean z) {
        this(wallpaperColors, z, (Style) null, 4, (DefaultConstructorMarker) null);
    }

    public final String toString() {
        Companion companion = Companion;
        companion.getClass();
        String stringForColor = Companion.stringForColor(this.seed);
        String access$humanReadable = Companion.access$humanReadable(companion, "PRIMARY", this.accent1.allShades);
        String access$humanReadable2 = Companion.access$humanReadable(companion, "SECONDARY", this.accent2.allShades);
        String access$humanReadable3 = Companion.access$humanReadable(companion, "TERTIARY", this.accent3.allShades);
        String access$humanReadable4 = Companion.access$humanReadable(companion, "NEUTRAL", this.neutral1.allShades);
        String access$humanReadable5 = Companion.access$humanReadable(companion, "NEUTRAL VARIANT", this.neutral2.allShades);
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("ColorScheme {\n  seed color: ", stringForColor, "\n  style: ");
        m4m.append(this.style);
        m4m.append("\n  palettes: \n  ");
        m4m.append(access$humanReadable);
        m4m.append("\n  ");
        AppOpItem$$ExternalSyntheticOutline0.m97m(m4m, access$humanReadable2, "\n  ", access$humanReadable3, "\n  ");
        m4m.append(access$humanReadable4);
        m4m.append("\n  ");
        m4m.append(access$humanReadable5);
        m4m.append("\n}");
        return m4m.toString();
    }

    public ColorScheme(int i, boolean z, Style style) {
        this.seed = i;
        this.darkTheme = z;
        this.style = style;
        Cam fromInt = Cam.fromInt(i);
        if (i == 0 || (style != Style.CONTENT && fromInt.getChroma() < 5.0f)) {
            i = -14979341;
        }
        this.accent1 = new TonalPalette(style.m163x334ec08f().f313a1, i);
        this.accent2 = new TonalPalette(style.m163x334ec08f().f314a2, i);
        this.accent3 = new TonalPalette(style.m163x334ec08f().f315a3, i);
        this.neutral1 = new TonalPalette(style.m163x334ec08f().f316n1, i);
        this.neutral2 = new TonalPalette(style.m163x334ec08f().f317n2, i);
    }

    public /* synthetic */ ColorScheme(int i, boolean z, Style style, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z, (i2 & 4) != 0 ? Style.TONAL_SPOT : style);
    }

    public ColorScheme(int i, boolean z) {
        this(i, z, Style.TONAL_SPOT);
    }

    public /* synthetic */ ColorScheme(WallpaperColors wallpaperColors, boolean z, Style style, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(wallpaperColors, z, (i & 4) != 0 ? Style.TONAL_SPOT : style);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ColorScheme(WallpaperColors wallpaperColors, boolean z, Style style) {
        this(((Number) CollectionsKt___CollectionsKt.first(Companion.getSeedColors(wallpaperColors, r0))).intValue(), z, style);
        boolean z2 = style != Style.CONTENT;
        Companion.getClass();
    }
}
