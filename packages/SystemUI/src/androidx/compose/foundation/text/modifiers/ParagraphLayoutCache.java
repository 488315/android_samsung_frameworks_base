package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.foundation.text.modifiers.InlineDensity;
import androidx.compose.foundation.text.modifiers.MinLinesConstrainer;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.text.AndroidParagraph;
import androidx.compose.ui.text.ParagraphIntrinsics;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.TextStyleKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ParagraphLayoutCache {
    public int cachedIntrinsicHeight;
    public int cachedIntrinsicHeightInputWidth;
    public IntrinsicMeasureScope density;
    public boolean didOverflow;
    public FontFamily.Resolver fontFamilyResolver;
    public LayoutDirection intrinsicsLayoutDirection;
    public long lastDensity;
    public long layoutSize;
    public MinLinesConstrainer mMinLinesConstrainer;
    public int maxLines;
    public int minLines;
    public int overflow;
    public AndroidParagraph paragraph;
    public ParagraphIntrinsics paragraphIntrinsics;
    public long prevConstraints;
    public boolean softWrap;
    public TextStyle style;
    public String text;

    public /* synthetic */ ParagraphLayoutCache(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, textStyle, resolver, i, z, i2, i3);
    }

    /* renamed from: useMinLinesConstrainer-euUD3Qg$default, reason: not valid java name */
    public static long m228useMinLinesConstrainereuUD3Qg$default(ParagraphLayoutCache paragraphLayoutCache, long j, LayoutDirection layoutDirection) {
        TextStyle textStyle = paragraphLayoutCache.style;
        paragraphLayoutCache.getClass();
        MinLinesConstrainer.Companion companion = MinLinesConstrainer.Companion;
        MinLinesConstrainer minLinesConstrainer = paragraphLayoutCache.mMinLinesConstrainer;
        IntrinsicMeasureScope intrinsicMeasureScope = paragraphLayoutCache.density;
        intrinsicMeasureScope.getClass();
        FontFamily.Resolver resolver = paragraphLayoutCache.fontFamilyResolver;
        companion.getClass();
        MinLinesConstrainer from = MinLinesConstrainer.Companion.from(minLinesConstrainer, layoutDirection, textStyle, intrinsicMeasureScope, resolver);
        paragraphLayoutCache.mMinLinesConstrainer = from;
        return from.m223coerceMinLinesOh53vG4$foundation_release(paragraphLayoutCache.minLines, j);
    }

    public final int intrinsicHeight(int i, LayoutDirection layoutDirection) {
        int i2 = this.cachedIntrinsicHeightInputWidth;
        int i3 = this.cachedIntrinsicHeight;
        if (i == i2 && i2 != -1) {
            return i3;
        }
        long Constraints = ConstraintsKt.Constraints(0, i, 0, Integer.MAX_VALUE);
        if (this.minLines > 1) {
            Constraints = m228useMinLinesConstrainereuUD3Qg$default(this, Constraints, layoutDirection);
        }
        ParagraphIntrinsics layoutDirection2 = setLayoutDirection(layoutDirection);
        long m221finalConstraintstfFHcEY = LayoutUtilsKt.m221finalConstraintstfFHcEY(Constraints, this.softWrap, this.overflow, layoutDirection2.getMaxIntrinsicWidth());
        boolean z = this.softWrap;
        int i4 = this.overflow;
        int i5 = this.maxLines;
        int ceilToIntPx = TextDelegateKt.ceilToIntPx(new AndroidParagraph((AndroidParagraphIntrinsics) layoutDirection2, ((z || !LayoutUtilsKt.m222isEllipsisMW5ApA(i4)) && i5 >= 1) ? i5 : 1, this.overflow, m221finalConstraintstfFHcEY, null).getHeight());
        int m822getMinHeightimpl = Constraints.m822getMinHeightimpl(Constraints);
        if (ceilToIntPx < m822getMinHeightimpl) {
            ceilToIntPx = m822getMinHeightimpl;
        }
        this.cachedIntrinsicHeightInputWidth = i;
        this.cachedIntrinsicHeight = ceilToIntPx;
        return ceilToIntPx;
    }

    public final void markDirty() {
        this.paragraph = null;
        this.paragraphIntrinsics = null;
        this.intrinsicsLayoutDirection = null;
        this.cachedIntrinsicHeightInputWidth = -1;
        this.cachedIntrinsicHeight = -1;
        Constraints.Companion.getClass();
        this.prevConstraints = Constraints.Companion.m827fixedJhjzzOo(0, 0);
        long j = 0;
        IntSize.Companion companion = IntSize.Companion;
        this.layoutSize = (j & 4294967295L) | (j << 32);
        this.didOverflow = false;
    }

    public final void setDensity$foundation_release(IntrinsicMeasureScope intrinsicMeasureScope) {
        long j;
        IntrinsicMeasureScope intrinsicMeasureScope2 = this.density;
        InlineDensity.Companion companion = InlineDensity.Companion;
        if (intrinsicMeasureScope != null) {
            j = InlineDensity.m219constructorimpl(intrinsicMeasureScope.getDensity(), intrinsicMeasureScope.getFontScale());
        } else {
            companion.getClass();
            j = InlineDensity.Unspecified;
        }
        if (intrinsicMeasureScope2 == null) {
            this.density = intrinsicMeasureScope;
            this.lastDensity = j;
        } else if (intrinsicMeasureScope == null || this.lastDensity != j) {
            this.density = intrinsicMeasureScope;
            this.lastDensity = j;
            markDirty();
        }
    }

    public final ParagraphIntrinsics setLayoutDirection(LayoutDirection layoutDirection) {
        ParagraphIntrinsics paragraphIntrinsics = this.paragraphIntrinsics;
        if (paragraphIntrinsics == null || layoutDirection != this.intrinsicsLayoutDirection || paragraphIntrinsics.getHasStaleResolvedFonts()) {
            this.intrinsicsLayoutDirection = layoutDirection;
            String str = this.text;
            TextStyle resolveDefaults = TextStyleKt.resolveDefaults(this.style, layoutDirection);
            EmptyList emptyList = EmptyList.INSTANCE;
            IntrinsicMeasureScope intrinsicMeasureScope = this.density;
            intrinsicMeasureScope.getClass();
            paragraphIntrinsics = new AndroidParagraphIntrinsics(str, resolveDefaults, emptyList, emptyList, this.fontFamilyResolver, intrinsicMeasureScope);
        }
        this.paragraphIntrinsics = paragraphIntrinsics;
        return paragraphIntrinsics;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ParagraphLayoutCache(paragraph=");
        sb.append(this.paragraph != null ? "<paragraph>" : "null");
        sb.append(", lastDensity=");
        sb.append((Object) InlineDensity.m220toStringimpl(this.lastDensity));
        sb.append(')');
        return sb.toString();
    }

    private ParagraphLayoutCache(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3) {
        this.text = str;
        this.style = textStyle;
        this.fontFamilyResolver = resolver;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        InlineDensity.Companion.getClass();
        this.lastDensity = InlineDensity.Unspecified;
        long j = 0;
        IntSize.Companion companion = IntSize.Companion;
        this.layoutSize = (j & 4294967295L) | (j << 32);
        Constraints.Companion.getClass();
        this.prevConstraints = Constraints.Companion.m827fixedJhjzzOo(0, 0);
        this.cachedIntrinsicHeightInputWidth = -1;
        this.cachedIntrinsicHeight = -1;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ParagraphLayoutCache(java.lang.String r10, androidx.compose.ui.text.TextStyle r11, androidx.compose.ui.text.font.FontFamily.Resolver r12, int r13, boolean r14, int r15, int r16, int r17, kotlin.jvm.internal.DefaultConstructorMarker r18) {
        /*
            r9 = this;
            r0 = r17 & 8
            if (r0 == 0) goto Lb
            androidx.compose.ui.text.style.TextOverflow$Companion r13 = androidx.compose.ui.text.style.TextOverflow.Companion
            r13.getClass()
            int r13 = androidx.compose.ui.text.style.TextOverflow.Clip
        Lb:
            r4 = r13
            r13 = r17 & 16
            r0 = 1
            if (r13 == 0) goto L13
            r5 = r0
            goto L14
        L13:
            r5 = r14
        L14:
            r13 = r17 & 32
            if (r13 == 0) goto L1d
            r13 = 2147483647(0x7fffffff, float:NaN)
            r6 = r13
            goto L1e
        L1d:
            r6 = r15
        L1e:
            r13 = r17 & 64
            if (r13 == 0) goto L24
            r7 = r0
            goto L26
        L24:
            r7 = r16
        L26:
            r8 = 0
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.ParagraphLayoutCache.<init>(java.lang.String, androidx.compose.ui.text.TextStyle, androidx.compose.ui.text.font.FontFamily$Resolver, int, boolean, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
