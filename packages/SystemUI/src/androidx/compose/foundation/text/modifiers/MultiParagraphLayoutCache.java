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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public final TextLayoutResult mo226performLayout5ZSfY2I(long j, long j2) {
            long j3;
            MultiParagraphLayoutCache multiParagraphLayoutCache = MultiParagraphLayoutCache.this;
            TextStyle textStyle = multiParagraphLayoutCache.style;
            long m227access$timesNB67dxo = TextUnit.m869isEmimpl(j2) ? MultiParagraphLayoutCacheKt.m227access$timesNB67dxo(multiParagraphLayoutCache.style.spanStyle.fontSize, j2) : j2;
            if (!TextUnit.m866equalsimpl0(m227access$timesNB67dxo, multiParagraphLayoutCache.style.spanStyle.fontSize)) {
                multiParagraphLayoutCache.setStyle(TextStyle.m754copyp1EtxEg$default(multiParagraphLayoutCache.style, 0L, m227access$timesNB67dxo, null, null, 0L, 0, 0L, null, null, 0, 16777213));
            }
            if (multiParagraphLayoutCache.minLines > 1) {
                LayoutDirection layoutDirection = multiParagraphLayoutCache.intrinsicsLayoutDirection;
                layoutDirection.getClass();
                j3 = multiParagraphLayoutCache.m225useMinLinesConstrainerOh53vG4(j, layoutDirection);
            } else {
                j3 = j;
            }
            LayoutDirection layoutDirection2 = multiParagraphLayoutCache.intrinsicsLayoutDirection;
            layoutDirection2.getClass();
            TextOverflow.Companion.getClass();
            int i = TextOverflow.Clip;
            MultiParagraphIntrinsics layoutDirection3 = multiParagraphLayoutCache.setLayoutDirection(layoutDirection2);
            long m221finalConstraintstfFHcEY = LayoutUtilsKt.m221finalConstraintstfFHcEY(j3, multiParagraphLayoutCache.softWrap, i, layoutDirection3.getMaxIntrinsicWidth());
            boolean z = multiParagraphLayoutCache.softWrap;
            int i2 = multiParagraphLayoutCache.maxLines;
            MultiParagraph multiParagraph = new MultiParagraph(layoutDirection3, m221finalConstraintstfFHcEY, ((z || !LayoutUtilsKt.m222isEllipsisMW5ApA(i)) && i2 >= 1) ? i2 : 1, i, (DefaultConstructorMarker) null);
            LayoutDirection layoutDirection4 = multiParagraphLayoutCache.intrinsicsLayoutDirection;
            layoutDirection4.getClass();
            TextLayoutResult m224textLayoutResultVKLhPVY = multiParagraphLayoutCache.m224textLayoutResultVKLhPVY(layoutDirection4, j3, multiParagraph);
            this.lastLayoutResult = m224textLayoutResultVKLhPVY;
            multiParagraphLayoutCache.setStyle(textStyle);
            return m224textLayoutResultVKLhPVY;
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toPx--R2X_6o */
        public final float mo56toPxR2X_6o(long j) {
            if (!TextUnit.m869isEmimpl(j)) {
                return getDensity() * mo52toDpGaN1DYA(j);
            }
            MultiParagraphLayoutCache multiParagraphLayoutCache = MultiParagraphLayoutCache.this;
            if (TextUnit.m869isEmimpl(multiParagraphLayoutCache.style.spanStyle.fontSize)) {
                throw new IllegalStateException("InternalAutoSize -> toPx(): Cannot convert Em to Px when style.fontSize is Em\nDeclare the composable's style.fontSize with Sp units instead.");
            }
            long j2 = multiParagraphLayoutCache.style.spanStyle.fontSize;
            TextUnit.Companion.getClass();
            if (TextUnit.m866equalsimpl0(j2, TextUnit.Unspecified)) {
                throw new IllegalStateException("InternalAutoSize -> toPx(): Cannot convert Em to Px when style.fontSize is not set. Please specify a font size.");
            }
            return TextUnit.m868getValueimpl(j) * mo56toPxR2X_6o(multiParagraphLayoutCache.style.spanStyle.fontSize);
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
        long Constraints = ConstraintsKt.Constraints(0, i, 0, Integer.MAX_VALUE);
        if (this.minLines > 1) {
            Constraints = m225useMinLinesConstrainerOh53vG4(Constraints, layoutDirection);
        }
        int i4 = this.overflow;
        MultiParagraphIntrinsics layoutDirection2 = setLayoutDirection(layoutDirection);
        long m221finalConstraintstfFHcEY = LayoutUtilsKt.m221finalConstraintstfFHcEY(Constraints, this.softWrap, i4, layoutDirection2.getMaxIntrinsicWidth());
        boolean z = this.softWrap;
        int i5 = this.maxLines;
        int ceilToIntPx = TextDelegateKt.ceilToIntPx(new MultiParagraph(layoutDirection2, m221finalConstraintstfFHcEY, ((z || !LayoutUtilsKt.m222isEllipsisMW5ApA(i4)) && i5 >= 1) ? i5 : 1, i4, (DefaultConstructorMarker) null).height);
        int m822getMinHeightimpl = Constraints.m822getMinHeightimpl(Constraints);
        if (ceilToIntPx < m822getMinHeightimpl) {
            ceilToIntPx = m822getMinHeightimpl;
        }
        this.cachedIntrinsicHeightInputWidth = i;
        this.cachedIntrinsicHeight = ceilToIntPx;
        return ceilToIntPx;
    }

    public final void setDensity$foundation_release(Density density) {
        long j;
        Density density2 = this.density;
        InlineDensity.Companion companion = InlineDensity.Companion;
        if (density != null) {
            j = InlineDensity.m219constructorimpl(density.getDensity(), density.getFontScale());
        } else {
            companion.getClass();
            j = InlineDensity.Unspecified;
        }
        if (density2 == null) {
            this.density = density;
            this.lastDensity = j;
        } else if (density == null || this.lastDensity != j) {
            this.density = density;
            this.lastDensity = j;
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
            TextStyle resolveDefaults = TextStyleKt.resolveDefaults(this.style, layoutDirection);
            Density density = this.density;
            density.getClass();
            FontFamily.Resolver resolver = this.fontFamilyResolver;
            List list = this.placeholders;
            if (list == null) {
                list = EmptyList.INSTANCE;
            }
            multiParagraphIntrinsics = new MultiParagraphIntrinsics(annotatedString, resolveDefaults, (List<AnnotatedString.Range<Placeholder>>) list, density, resolver);
        }
        this.paragraphIntrinsics = multiParagraphIntrinsics;
        return multiParagraphIntrinsics;
    }

    public final void setStyle(TextStyle textStyle) {
        boolean hasSameLayoutAffectingAttributes = textStyle.hasSameLayoutAffectingAttributes(this.style);
        this.style = textStyle;
        if (hasSameLayoutAffectingAttributes) {
            return;
        }
        this.paragraphIntrinsics = null;
        this.layoutCache = null;
        this.cachedIntrinsicHeight = -1;
        this.cachedIntrinsicHeightInputWidth = -1;
    }

    /* renamed from: textLayoutResult-VKLhPVY, reason: not valid java name */
    public final TextLayoutResult m224textLayoutResultVKLhPVY(LayoutDirection layoutDirection, long j, MultiParagraph multiParagraph) {
        float min = Math.min(multiParagraph.intrinsics.getMaxIntrinsicWidth(), multiParagraph.width);
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
        long ceilToIntPx = (TextDelegateKt.ceilToIntPx(multiParagraph.height) & 4294967295L) | (TextDelegateKt.ceilToIntPx(min) << 32);
        IntSize.Companion companion = IntSize.Companion;
        return new TextLayoutResult(textLayoutInput, multiParagraph, ConstraintsKt.m829constrain4WqzIAM(j, ceilToIntPx), null);
    }

    /* renamed from: useMinLinesConstrainer-Oh53vG4, reason: not valid java name */
    public final long m225useMinLinesConstrainerOh53vG4(long j, LayoutDirection layoutDirection) {
        MinLinesConstrainer.Companion companion = MinLinesConstrainer.Companion;
        MinLinesConstrainer minLinesConstrainer = this.mMinLinesConstrainer;
        TextStyle textStyle = this.style;
        Density density = this.density;
        density.getClass();
        FontFamily.Resolver resolver = this.fontFamilyResolver;
        companion.getClass();
        MinLinesConstrainer from = MinLinesConstrainer.Companion.from(minLinesConstrainer, layoutDirection, textStyle, density, resolver);
        this.mMinLinesConstrainer = from;
        return from.m223coerceMinLinesOh53vG4$foundation_release(this.minLines, j);
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MultiParagraphLayoutCache(androidx.compose.ui.text.AnnotatedString r14, androidx.compose.ui.text.TextStyle r15, androidx.compose.ui.text.font.FontFamily.Resolver r16, int r17, boolean r18, int r19, int r20, java.util.List r21, androidx.compose.foundation.text.TextAutoSize r22, int r23, kotlin.jvm.internal.DefaultConstructorMarker r24) {
        /*
            r13 = this;
            r0 = r23
            r1 = r0 & 8
            if (r1 == 0) goto Lf
            androidx.compose.ui.text.style.TextOverflow$Companion r1 = androidx.compose.ui.text.style.TextOverflow.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextOverflow.Clip
            r6 = r1
            goto L11
        Lf:
            r6 = r17
        L11:
            r1 = r0 & 16
            r2 = 1
            if (r1 == 0) goto L18
            r7 = r2
            goto L1a
        L18:
            r7 = r18
        L1a:
            r1 = r0 & 32
            if (r1 == 0) goto L23
            r1 = 2147483647(0x7fffffff, float:NaN)
            r8 = r1
            goto L25
        L23:
            r8 = r19
        L25:
            r1 = r0 & 64
            if (r1 == 0) goto L2b
            r9 = r2
            goto L2d
        L2b:
            r9 = r20
        L2d:
            r1 = r0 & 128(0x80, float:1.8E-43)
            r2 = 0
            if (r1 == 0) goto L34
            r10 = r2
            goto L36
        L34:
            r10 = r21
        L36:
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L3c
            r11 = r2
            goto L3e
        L3c:
            r11 = r22
        L3e:
            r12 = 0
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.MultiParagraphLayoutCache.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.font.FontFamily$Resolver, int, boolean, int, int, java.util.List, androidx.compose.foundation.text.TextAutoSize, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
