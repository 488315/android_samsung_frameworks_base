package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.foundation.text.modifiers.InlineDensity;
import androidx.compose.foundation.text.modifiers.MinLinesConstrainer;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphIntrinsics;
import androidx.compose.ui.text.Placeholder;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.TextStyleKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.TextUnit;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class MultiParagraphLayoutCache {
    public TextAutoSizeLayoutScopeImpl _textAutoSizeLayoutScope;
    public TextAutoSize autoSize;
    public int cachedIntrinsicHeight;
    public int cachedIntrinsicHeightInputWidth;
    public Density density;
    public FontFamily.Resolver fontFamilyResolver;
    public LayoutDirection intrinsicsLayoutDirection;
    public long lastDensity;
    public TextLayoutResult layoutCache;
    public MinLinesConstrainer mMinLinesConstrainer;
    public int maxLines;
    public int minLines;
    public int overflow;
    public MultiParagraphIntrinsics paragraphIntrinsics;
    public List placeholders;
    public boolean softWrap;
    public TextStyle style;
    public AnnotatedString text;

    final class TextAutoSizeLayoutScopeImpl implements TextAutoSizeLayoutScope {
        public TextLayoutResult lastLayoutResult;

        public TextAutoSizeLayoutScopeImpl() {
        }

        @Override // androidx.compose.ui.unit.Density
        public final float getDensity() {
            Density density = MultiParagraphLayoutCache.this.density;
            density.getClass();
            return density.getDensity();
        }

        @Override // androidx.compose.ui.unit.FontScaling
        public final float getFontScale() {
            Density density = MultiParagraphLayoutCache.this.density;
            density.getClass();
            return density.getFontScale();
        }

        @Override // androidx.compose.foundation.text.modifiers.TextAutoSizeLayoutScope
        /* renamed from: performLayout-5ZSfY2I, reason: not valid java name */
        public final TextLayoutResult mo227performLayout5ZSfY2I(long j, long j2) {
            long jM226useMinLinesConstrainerOh53vG4;
            MultiParagraphLayoutCache multiParagraphLayoutCache = MultiParagraphLayoutCache.this;
            TextStyle textStyle = multiParagraphLayoutCache.style;
            long jM228access$timesNB67dxo = TextUnit.m871isEmimpl(j2) ? MultiParagraphLayoutCacheKt.m228access$timesNB67dxo(multiParagraphLayoutCache.style.spanStyle.fontSize, j2) : j2;
            if (!TextUnit.m868equalsimpl0(jM228access$timesNB67dxo, multiParagraphLayoutCache.style.spanStyle.fontSize)) {
                multiParagraphLayoutCache.setStyle(TextStyle.m756copyp1EtxEg$default(multiParagraphLayoutCache.style, 0L, jM228access$timesNB67dxo, null, null, 0L, 0, 0L, null, null, 0, 16777213));
            }
            if (multiParagraphLayoutCache.minLines > 1) {
                LayoutDirection layoutDirection = multiParagraphLayoutCache.intrinsicsLayoutDirection;
                layoutDirection.getClass();
                jM226useMinLinesConstrainerOh53vG4 = multiParagraphLayoutCache.m226useMinLinesConstrainerOh53vG4(j, layoutDirection);
            } else {
                jM226useMinLinesConstrainerOh53vG4 = j;
            }
            LayoutDirection layoutDirection2 = multiParagraphLayoutCache.intrinsicsLayoutDirection;
            layoutDirection2.getClass();
            TextOverflow.Companion.getClass();
            int i = TextOverflow.Clip;
            MultiParagraphIntrinsics layoutDirection3 = multiParagraphLayoutCache.setLayoutDirection(layoutDirection2);
            long jM222finalConstraintstfFHcEY = LayoutUtilsKt.m222finalConstraintstfFHcEY(jM226useMinLinesConstrainerOh53vG4, multiParagraphLayoutCache.softWrap, i, layoutDirection3.getMaxIntrinsicWidth());
            boolean z = multiParagraphLayoutCache.softWrap;
            int i2 = multiParagraphLayoutCache.maxLines;
            MultiParagraph multiParagraph = new MultiParagraph(layoutDirection3, jM222finalConstraintstfFHcEY, ((z || !LayoutUtilsKt.m223isEllipsisMW5ApA(i)) && i2 >= 1) ? i2 : 1, i, (DefaultConstructorMarker) null);
            LayoutDirection layoutDirection4 = multiParagraphLayoutCache.intrinsicsLayoutDirection;
            layoutDirection4.getClass();
            TextLayoutResult textLayoutResultM225textLayoutResultVKLhPVY = multiParagraphLayoutCache.m225textLayoutResultVKLhPVY(layoutDirection4, jM226useMinLinesConstrainerOh53vG4, multiParagraph);
            this.lastLayoutResult = textLayoutResultM225textLayoutResultVKLhPVY;
            multiParagraphLayoutCache.setStyle(textStyle);
            return textLayoutResultM225textLayoutResultVKLhPVY;
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toPx--R2X_6o */
        public final float mo57toPxR2X_6o(long j) {
            if (!TextUnit.m871isEmimpl(j)) {
                return getDensity() * mo53toDpGaN1DYA(j);
            }
            MultiParagraphLayoutCache multiParagraphLayoutCache = MultiParagraphLayoutCache.this;
            if (TextUnit.m871isEmimpl(multiParagraphLayoutCache.style.spanStyle.fontSize)) {
                throw new IllegalStateException("InternalAutoSize -> toPx(): Cannot convert Em to Px when style.fontSize is Em\nDeclare the composable's style.fontSize with Sp units instead.");
            }
            long j2 = multiParagraphLayoutCache.style.spanStyle.fontSize;
            TextUnit.Companion.getClass();
            if (TextUnit.m868equalsimpl0(j2, TextUnit.Unspecified)) {
                throw new IllegalStateException("InternalAutoSize -> toPx(): Cannot convert Em to Px when style.fontSize is not set. Please specify a font size.");
            }
            return TextUnit.m870getValueimpl(j) * mo57toPxR2X_6o(multiParagraphLayoutCache.style.spanStyle.fontSize);
        }
    }

    public /* synthetic */ MultiParagraphLayoutCache(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, List list, TextAutoSize textAutoSize, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, resolver, i, z, i2, i3, list, textAutoSize);
    }

    public final int intrinsicHeight(int i, LayoutDirection layoutDirection) {
        int i2 = this.cachedIntrinsicHeightInputWidth;
        int i3 = this.cachedIntrinsicHeight;
        if (i == i2 && i2 != -1) {
            return i3;
        }
        long jConstraints = ConstraintsKt.Constraints(0, i, 0, Integer.MAX_VALUE);
        if (this.minLines > 1) {
            jConstraints = m226useMinLinesConstrainerOh53vG4(jConstraints, layoutDirection);
        }
        int i4 = this.overflow;
        MultiParagraphIntrinsics layoutDirection2 = setLayoutDirection(layoutDirection);
        long jM222finalConstraintstfFHcEY = LayoutUtilsKt.m222finalConstraintstfFHcEY(jConstraints, this.softWrap, i4, layoutDirection2.getMaxIntrinsicWidth());
        boolean z = this.softWrap;
        int i5 = this.maxLines;
        int iCeilToIntPx = TextDelegateKt.ceilToIntPx(new MultiParagraph(layoutDirection2, jM222finalConstraintstfFHcEY, ((z || !LayoutUtilsKt.m223isEllipsisMW5ApA(i4)) && i5 >= 1) ? i5 : 1, i4, (DefaultConstructorMarker) null).height);
        int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(jConstraints);
        if (iCeilToIntPx < iM824getMinHeightimpl) {
            iCeilToIntPx = iM824getMinHeightimpl;
        }
        this.cachedIntrinsicHeightInputWidth = i;
        this.cachedIntrinsicHeight = iCeilToIntPx;
        return iCeilToIntPx;
    }

    public final void setDensity$foundation_release(Density density) {
        long jM220constructorimpl;
        Density density2 = this.density;
        InlineDensity.Companion companion = InlineDensity.Companion;
        if (density != null) {
            jM220constructorimpl = InlineDensity.m220constructorimpl(density.getDensity(), density.getFontScale());
        } else {
            companion.getClass();
            jM220constructorimpl = InlineDensity.Unspecified;
        }
        if (density2 == null) {
            this.density = density;
            this.lastDensity = jM220constructorimpl;
        } else if (density == null || this.lastDensity != jM220constructorimpl) {
            this.density = density;
            this.lastDensity = jM220constructorimpl;
            this.paragraphIntrinsics = null;
            this.layoutCache = null;
            this.cachedIntrinsicHeight = -1;
            this.cachedIntrinsicHeightInputWidth = -1;
            this._textAutoSizeLayoutScope = null;
        }
    }

    public final MultiParagraphIntrinsics setLayoutDirection(LayoutDirection layoutDirection) {
        MultiParagraphIntrinsics multiParagraphIntrinsics = this.paragraphIntrinsics;
        if (multiParagraphIntrinsics == null || layoutDirection != this.intrinsicsLayoutDirection || multiParagraphIntrinsics.getHasStaleResolvedFonts()) {
            this.intrinsicsLayoutDirection = layoutDirection;
            AnnotatedString annotatedString = this.text;
            TextStyle textStyleResolveDefaults = TextStyleKt.resolveDefaults(this.style, layoutDirection);
            Density density = this.density;
            density.getClass();
            FontFamily.Resolver resolver = this.fontFamilyResolver;
            List list = this.placeholders;
            if (list == null) {
                list = EmptyList.INSTANCE;
            }
            multiParagraphIntrinsics = new MultiParagraphIntrinsics(annotatedString, textStyleResolveDefaults, (List<AnnotatedString.Range<Placeholder>>) list, density, resolver);
        }
        this.paragraphIntrinsics = multiParagraphIntrinsics;
        return multiParagraphIntrinsics;
    }

    public final void setStyle(TextStyle textStyle) {
        boolean zHasSameLayoutAffectingAttributes = textStyle.hasSameLayoutAffectingAttributes(this.style);
        this.style = textStyle;
        if (zHasSameLayoutAffectingAttributes) {
            return;
        }
        this.paragraphIntrinsics = null;
        this.layoutCache = null;
        this.cachedIntrinsicHeight = -1;
        this.cachedIntrinsicHeightInputWidth = -1;
    }

    /* renamed from: textLayoutResult-VKLhPVY, reason: not valid java name */
    public final TextLayoutResult m225textLayoutResultVKLhPVY(LayoutDirection layoutDirection, long j, MultiParagraph multiParagraph) {
        float fMin = Math.min(multiParagraph.intrinsics.getMaxIntrinsicWidth(), multiParagraph.width);
        AnnotatedString annotatedString = this.text;
        TextStyle textStyle = this.style;
        List list = this.placeholders;
        if (list == null) {
            list = EmptyList.INSTANCE;
        }
        int i = this.maxLines;
        boolean z = this.softWrap;
        int i2 = this.overflow;
        Density density = this.density;
        density.getClass();
        TextLayoutInput textLayoutInput = new TextLayoutInput(annotatedString, textStyle, list, i, z, i2, density, layoutDirection, this.fontFamilyResolver, j, (DefaultConstructorMarker) null);
        long jCeilToIntPx = (TextDelegateKt.ceilToIntPx(multiParagraph.height) & 4294967295L) | (TextDelegateKt.ceilToIntPx(fMin) << 32);
        IntSize.Companion companion = IntSize.Companion;
        return new TextLayoutResult(textLayoutInput, multiParagraph, ConstraintsKt.m831constrain4WqzIAM(j, jCeilToIntPx), null);
    }

    /* renamed from: useMinLinesConstrainer-Oh53vG4, reason: not valid java name */
    public final long m226useMinLinesConstrainerOh53vG4(long j, LayoutDirection layoutDirection) {
        MinLinesConstrainer.Companion companion = MinLinesConstrainer.Companion;
        MinLinesConstrainer minLinesConstrainer = this.mMinLinesConstrainer;
        TextStyle textStyle = this.style;
        Density density = this.density;
        density.getClass();
        FontFamily.Resolver resolver = this.fontFamilyResolver;
        companion.getClass();
        MinLinesConstrainer minLinesConstrainerFrom = MinLinesConstrainer.Companion.from(minLinesConstrainer, layoutDirection, textStyle, density, resolver);
        this.mMinLinesConstrainer = minLinesConstrainerFrom;
        return minLinesConstrainerFrom.m224coerceMinLinesOh53vG4$foundation_release(this.minLines, j);
    }

    private MultiParagraphLayoutCache(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, List<AnnotatedString.Range<Placeholder>> list, TextAutoSize textAutoSize) {
        this.text = annotatedString;
        this.fontFamilyResolver = resolver;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        this.placeholders = list;
        this.autoSize = textAutoSize;
        InlineDensity.Companion.getClass();
        this.lastDensity = InlineDensity.Unspecified;
        this.style = textStyle;
        this.cachedIntrinsicHeightInputWidth = -1;
        this.cachedIntrinsicHeight = -1;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MultiParagraphLayoutCache(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, List list, TextAutoSize textAutoSize, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        int i5;
        if ((i4 & 8) != 0) {
            TextOverflow.Companion.getClass();
            i5 = TextOverflow.Clip;
        } else {
            i5 = i;
        }
        this(annotatedString, textStyle, resolver, i5, (i4 & 16) != 0 ? true : z, (i4 & 32) != 0 ? Integer.MAX_VALUE : i2, (i4 & 64) != 0 ? 1 : i3, (i4 & 128) != 0 ? null : list, (i4 & 256) != 0 ? null : textAutoSize, null);
    }
}
