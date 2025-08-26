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
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
    public static long m229useMinLinesConstrainereuUD3Qg$default(ParagraphLayoutCache paragraphLayoutCache, long j, LayoutDirection layoutDirection) {
        TextStyle textStyle = paragraphLayoutCache.style;
        paragraphLayoutCache.getClass();
        MinLinesConstrainer.Companion companion = MinLinesConstrainer.Companion;
        MinLinesConstrainer minLinesConstrainer = paragraphLayoutCache.mMinLinesConstrainer;
        IntrinsicMeasureScope intrinsicMeasureScope = paragraphLayoutCache.density;
        intrinsicMeasureScope.getClass();
        FontFamily.Resolver resolver = paragraphLayoutCache.fontFamilyResolver;
        companion.getClass();
        MinLinesConstrainer minLinesConstrainerFrom = MinLinesConstrainer.Companion.from(minLinesConstrainer, layoutDirection, textStyle, intrinsicMeasureScope, resolver);
        paragraphLayoutCache.mMinLinesConstrainer = minLinesConstrainerFrom;
        return minLinesConstrainerFrom.m224coerceMinLinesOh53vG4$foundation_release(paragraphLayoutCache.minLines, j);
    }

    public final int intrinsicHeight(int i, LayoutDirection layoutDirection) {
        int i2 = this.cachedIntrinsicHeightInputWidth;
        int i3 = this.cachedIntrinsicHeight;
        if (i == i2 && i2 != -1) {
            return i3;
        }
        long jConstraints = ConstraintsKt.Constraints(0, i, 0, Integer.MAX_VALUE);
        if (this.minLines > 1) {
            jConstraints = m229useMinLinesConstrainereuUD3Qg$default(this, jConstraints, layoutDirection);
        }
        ParagraphIntrinsics layoutDirection2 = setLayoutDirection(layoutDirection);
        long jM222finalConstraintstfFHcEY = LayoutUtilsKt.m222finalConstraintstfFHcEY(jConstraints, this.softWrap, this.overflow, layoutDirection2.getMaxIntrinsicWidth());
        boolean z = this.softWrap;
        int i4 = this.overflow;
        int i5 = this.maxLines;
        int iCeilToIntPx = TextDelegateKt.ceilToIntPx(new AndroidParagraph((AndroidParagraphIntrinsics) layoutDirection2, ((z || !LayoutUtilsKt.m223isEllipsisMW5ApA(i4)) && i5 >= 1) ? i5 : 1, this.overflow, jM222finalConstraintstfFHcEY, null).getHeight());
        int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(jConstraints);
        if (iCeilToIntPx < iM824getMinHeightimpl) {
            iCeilToIntPx = iM824getMinHeightimpl;
        }
        this.cachedIntrinsicHeightInputWidth = i;
        this.cachedIntrinsicHeight = iCeilToIntPx;
        return iCeilToIntPx;
    }

    public final void markDirty() {
        this.paragraph = null;
        this.paragraphIntrinsics = null;
        this.intrinsicsLayoutDirection = null;
        this.cachedIntrinsicHeightInputWidth = -1;
        this.cachedIntrinsicHeight = -1;
        Constraints.Companion.getClass();
        this.prevConstraints = Constraints.Companion.m829fixedJhjzzOo(0, 0);
        long j = 0;
        IntSize.Companion companion = IntSize.Companion;
        this.layoutSize = (j & 4294967295L) | (j << 32);
        this.didOverflow = false;
    }

    public final void setDensity$foundation_release(IntrinsicMeasureScope intrinsicMeasureScope) {
        long jM220constructorimpl;
        IntrinsicMeasureScope intrinsicMeasureScope2 = this.density;
        InlineDensity.Companion companion = InlineDensity.Companion;
        if (intrinsicMeasureScope != null) {
            jM220constructorimpl = InlineDensity.m220constructorimpl(intrinsicMeasureScope.getDensity(), intrinsicMeasureScope.getFontScale());
        } else {
            companion.getClass();
            jM220constructorimpl = InlineDensity.Unspecified;
        }
        if (intrinsicMeasureScope2 == null) {
            this.density = intrinsicMeasureScope;
            this.lastDensity = jM220constructorimpl;
        } else if (intrinsicMeasureScope == null || this.lastDensity != jM220constructorimpl) {
            this.density = intrinsicMeasureScope;
            this.lastDensity = jM220constructorimpl;
            markDirty();
        }
    }

    public final ParagraphIntrinsics setLayoutDirection(LayoutDirection layoutDirection) {
        ParagraphIntrinsics androidParagraphIntrinsics = this.paragraphIntrinsics;
        if (androidParagraphIntrinsics == null || layoutDirection != this.intrinsicsLayoutDirection || androidParagraphIntrinsics.getHasStaleResolvedFonts()) {
            this.intrinsicsLayoutDirection = layoutDirection;
            String str = this.text;
            TextStyle textStyleResolveDefaults = TextStyleKt.resolveDefaults(this.style, layoutDirection);
            EmptyList emptyList = EmptyList.INSTANCE;
            IntrinsicMeasureScope intrinsicMeasureScope = this.density;
            intrinsicMeasureScope.getClass();
            androidParagraphIntrinsics = new AndroidParagraphIntrinsics(str, textStyleResolveDefaults, emptyList, emptyList, this.fontFamilyResolver, intrinsicMeasureScope);
        }
        this.paragraphIntrinsics = androidParagraphIntrinsics;
        return androidParagraphIntrinsics;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ParagraphLayoutCache(paragraph=");
        sb.append(this.paragraph != null ? "<paragraph>" : "null");
        sb.append(", lastDensity=");
        sb.append((Object) InlineDensity.m221toStringimpl(this.lastDensity));
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
        this.prevConstraints = Constraints.Companion.m829fixedJhjzzOo(0, 0);
        this.cachedIntrinsicHeightInputWidth = -1;
        this.cachedIntrinsicHeight = -1;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ParagraphLayoutCache(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i4 & 8) != 0) {
            TextOverflow.Companion.getClass();
            i = TextOverflow.Clip;
        }
        this(str, textStyle, resolver, i, (i4 & 16) != 0 ? true : z, (i4 & 32) != 0 ? Integer.MAX_VALUE : i2, (i4 & 64) != 0 ? 1 : i3, null);
    }
}
