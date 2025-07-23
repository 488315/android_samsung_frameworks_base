package androidx.compose.foundation.text.modifiers;

import androidx.compose.ui.text.ParagraphKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.TextStyleKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MinLinesConstrainer {
    public static final Companion Companion = new Companion(null);
    public static MinLinesConstrainer last;
    public final Density density;
    public final FontFamily.Resolver fontFamilyResolver;
    public final TextStyle inputTextStyle;
    public final LayoutDirection layoutDirection;
    public float lineHeightCache = Float.NaN;
    public float oneLineHeightCache = Float.NaN;
    public final TextStyle resolvedStyle;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static MinLinesConstrainer from(MinLinesConstrainer minLinesConstrainer, LayoutDirection layoutDirection, TextStyle textStyle, Density density, FontFamily.Resolver resolver) {
            if (minLinesConstrainer != null && layoutDirection == minLinesConstrainer.layoutDirection && TextStyleKt.resolveDefaults(textStyle, layoutDirection).equals(minLinesConstrainer.inputTextStyle) && density.getDensity() == minLinesConstrainer.density.getDensity() && resolver == minLinesConstrainer.fontFamilyResolver) {
                return minLinesConstrainer;
            }
            MinLinesConstrainer minLinesConstrainer2 = MinLinesConstrainer.last;
            if (minLinesConstrainer2 != null && layoutDirection == minLinesConstrainer2.layoutDirection && TextStyleKt.resolveDefaults(textStyle, layoutDirection).equals(minLinesConstrainer2.inputTextStyle) && density.getDensity() == minLinesConstrainer2.density.getDensity() && resolver == minLinesConstrainer2.fontFamilyResolver) {
                return minLinesConstrainer2;
            }
            MinLinesConstrainer minLinesConstrainer3 = new MinLinesConstrainer(layoutDirection, TextStyleKt.resolveDefaults(textStyle, layoutDirection), DensityKt.Density(density.getDensity(), density.getFontScale()), resolver);
            MinLinesConstrainer.last = minLinesConstrainer3;
            return minLinesConstrainer3;
        }

        private Companion() {
        }
    }

    public MinLinesConstrainer(LayoutDirection layoutDirection, TextStyle textStyle, Density density, FontFamily.Resolver resolver) {
        this.layoutDirection = layoutDirection;
        this.inputTextStyle = textStyle;
        this.density = density;
        this.fontFamilyResolver = resolver;
        this.resolvedStyle = TextStyleKt.resolveDefaults(textStyle, layoutDirection);
    }

    /* renamed from: coerceMinLines-Oh53vG4$foundation_release, reason: not valid java name */
    public final long m223coerceMinLinesOh53vG4$foundation_release(int i, long j) {
        int m822getMinHeightimpl;
        float f = this.oneLineHeightCache;
        float f2 = this.lineHeightCache;
        if (Float.isNaN(f) || Float.isNaN(f2)) {
            String str = MinLinesConstrainerKt.EmptyTextReplacement;
            long Constraints$default = ConstraintsKt.Constraints$default(0, 0, 0, 0, 15);
            TextOverflow.Companion.getClass();
            int i2 = TextOverflow.Clip;
            float height = ParagraphKt.m738ParagraphUl8oQg4$default(str, this.resolvedStyle, Constraints$default, this.density, this.fontFamilyResolver, null, 1, i2, 96).getHeight();
            float height2 = ParagraphKt.m738ParagraphUl8oQg4$default(MinLinesConstrainerKt.TwoLineTextReplacement, this.resolvedStyle, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), this.density, this.fontFamilyResolver, null, 2, i2, 96).getHeight() - height;
            this.oneLineHeightCache = height;
            this.lineHeightCache = height2;
            f2 = height2;
            f = height;
        }
        if (i != 1) {
            int round = Math.round((f2 * (i - 1)) + f);
            m822getMinHeightimpl = round >= 0 ? round : 0;
            int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
            if (m822getMinHeightimpl > m820getMaxHeightimpl) {
                m822getMinHeightimpl = m820getMaxHeightimpl;
            }
        } else {
            m822getMinHeightimpl = Constraints.m822getMinHeightimpl(j);
        }
        return ConstraintsKt.Constraints(Constraints.m823getMinWidthimpl(j), Constraints.m821getMaxWidthimpl(j), m822getMinHeightimpl, Constraints.m820getMaxHeightimpl(j));
    }
}
