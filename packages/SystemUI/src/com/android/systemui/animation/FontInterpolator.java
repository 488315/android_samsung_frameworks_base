package com.android.systemui.animation;

import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import android.util.Log;
import android.util.MathUtils;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public final class FontInterpolator {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = Log.isLoggable("FontInterpolator", 3);
    public static final FontVariationAxis[] EMPTY_AXES = new FontVariationAxis[0];
    public final FontCache fontCache;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean canInterpolate(Font font, Font font2) {
            return font.getTtcIndex() == font2.getTtcIndex() && font.getSourceIdentifier() == font2.getSourceIdentifier();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FontInterpolator() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final Font lerp(Font font, Font font2, float f, float f2) throws IOException {
        float fLerp;
        int i;
        Font font3 = font;
        float f3 = f2;
        if (f <= 0.0f) {
            return font3;
        }
        if (f >= 1.0f) {
            return font2;
        }
        FontVariationAxis[] axes = font3.getAxes();
        if (axes == null) {
            axes = EMPTY_AXES;
        }
        FontVariationAxis[] axes2 = font2.getAxes();
        if (axes2 == null) {
            axes2 = EMPTY_AXES;
        }
        if (axes.length == 0 && axes2.length == 0) {
            return font3;
        }
        FontCacheImpl fontCacheImpl = (FontCacheImpl) this.fontCache;
        InterpKey interpKey = new InterpKey(font3, font2, MathKt__MathJVMKt.roundToInt(fontCacheImpl.animationFrameCount * f3));
        Font font4 = (Font) fontCacheImpl.interpCache.get(interpKey);
        boolean z = DEBUG;
        if (font4 != null) {
            if (z) {
                StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("[", f, ", ", f3, "] Interp. cache hit for ");
                sbM.append(interpKey);
                Log.d("FontInterpolator", sbM.toString());
            }
            return font4;
        }
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
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 >= axes.length && i3 >= axes2.length) {
                VarFontKey varFontKey = new VarFontKey(font3, arrayList);
                Font font5 = (Font) fontCacheImpl.verFontCache.get(varFontKey);
                if (font5 != null) {
                    fontCacheImpl.interpCache.put(interpKey, font5);
                    if (z) {
                        StringBuilder sbM2 = CubicBezierEasing$$ExternalSyntheticOutline0.m("[", f, ", ", f3, "] Axis cache hit for ");
                        sbM2.append(varFontKey);
                        Log.d("FontInterpolator", sbM2.toString());
                    }
                    return font5;
                }
                Font fontBuild = new Font.Builder(font3).setFontVariationSettings((FontVariationAxis[]) arrayList.toArray(new FontVariationAxis[i2])).build();
                fontCacheImpl.interpCache.put(interpKey, fontBuild);
                fontCacheImpl.verFontCache.put(varFontKey, fontBuild);
                Log.e("FontInterpolator", "[" + f + ", " + f3 + "] Cache MISS for " + interpKey + " / " + varFontKey);
                return fontBuild;
            }
            String tag = i4 < axes.length ? axes[i4].getTag() : null;
            String tag2 = i3 < axes2.length ? axes2[i3].getTag() : null;
            int iCompareTo = tag == null ? 1 : tag2 == null ? -1 : tag.compareTo(tag2);
            if (iCompareTo != 0 && iCompareTo >= 0) {
                tag2.getClass();
                tag = tag2;
            } else {
                tag.getClass();
            }
            GSFAxes.INSTANCE.getClass();
            AxisDefinition axisDefinition = (AxisDefinition) GSFAxes.AXIS_MAP.get(tag.toLowerCase(Locale.ROOT));
            if (iCompareTo != 0 && axisDefinition == null) {
                throw new IllegalArgumentException("Unable to interpolate due to unknown default axes value: ".concat(tag).toString());
            }
            if (iCompareTo == 0) {
                i = i3 + 1;
                fLerp = MathUtils.lerp(axes[i4].getStyleValue(), axes2[i3].getStyleValue(), f);
                i4++;
            } else if (iCompareTo < 0) {
                int i5 = i4 + 1;
                float styleValue = axes[i4].getStyleValue();
                axisDefinition.getClass();
                float fLerp2 = MathUtils.lerp(styleValue, axisDefinition.defaultValue, f);
                i = i3;
                i4 = i5;
                fLerp = fLerp2;
            } else {
                axisDefinition.getClass();
                int i6 = i3 + 1;
                fLerp = MathUtils.lerp(axisDefinition.defaultValue, axes2[i3].getStyleValue(), f);
                i = i6;
            }
            arrayList.add(new FontVariationAxis(tag, MathKt__MathJVMKt.roundToInt(fLerp / r0) * (axisDefinition != null ? axisDefinition.animationStep : 1.0f)));
            font3 = font;
            f3 = f2;
            i3 = i;
            i2 = 0;
        }
    }

    public FontInterpolator(FontCache fontCache) {
        this.fontCache = fontCache;
    }

    public /* synthetic */ FontInterpolator(FontCache fontCache, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new FontCacheImpl(0, 1, null) : fontCache);
    }
}
