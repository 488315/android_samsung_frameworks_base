package androidx.compose.foundation.text;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraphIntrinsics;
import androidx.compose.ui.text.Placeholder;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.TextStyleKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextDelegate {
    public final Density density;
    public final FontFamily.Resolver fontFamilyResolver;
    public LayoutDirection intrinsicsLayoutDirection;
    public final int maxLines;
    public final int minLines;
    public final int overflow;
    public MultiParagraphIntrinsics paragraphIntrinsics;
    public final List placeholders;
    public final boolean softWrap;
    public final TextStyle style;
    public final AnnotatedString text;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ TextDelegate(AnnotatedString annotatedString, TextStyle textStyle, int i, int i2, boolean z, int i3, Density density, FontFamily.Resolver resolver, List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, i, i2, z, i3, density, resolver, list);
    }

    public final void layoutIntrinsics(LayoutDirection layoutDirection) {
        MultiParagraphIntrinsics multiParagraphIntrinsics = this.paragraphIntrinsics;
        if (multiParagraphIntrinsics == null || layoutDirection != this.intrinsicsLayoutDirection || multiParagraphIntrinsics.getHasStaleResolvedFonts()) {
            this.intrinsicsLayoutDirection = layoutDirection;
            multiParagraphIntrinsics = new MultiParagraphIntrinsics(this.text, TextStyleKt.resolveDefaults(this.style, layoutDirection), (List<AnnotatedString.Range<Placeholder>>) this.placeholders, this.density, this.fontFamilyResolver);
        }
        this.paragraphIntrinsics = multiParagraphIntrinsics;
    }

    private TextDelegate(AnnotatedString annotatedString, TextStyle textStyle, int i, int i2, boolean z, int i3, Density density, FontFamily.Resolver resolver, List<AnnotatedString.Range<Placeholder>> list) {
        this.text = annotatedString;
        this.style = textStyle;
        this.maxLines = i;
        this.minLines = i2;
        this.softWrap = z;
        this.overflow = i3;
        this.density = density;
        this.fontFamilyResolver = resolver;
        this.placeholders = list;
        if (!(i > 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("no maxLines");
        }
        if (!(i2 > 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("no minLines");
        }
        if (i2 <= i) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("minLines greater than maxLines");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextDelegate(androidx.compose.ui.text.AnnotatedString r14, androidx.compose.ui.text.TextStyle r15, int r16, int r17, boolean r18, int r19, androidx.compose.ui.unit.Density r20, androidx.compose.ui.text.font.FontFamily.Resolver r21, java.util.List r22, int r23, kotlin.jvm.internal.DefaultConstructorMarker r24) {
        /*
            r13 = this;
            r0 = r23
            r1 = r0 & 4
            if (r1 == 0) goto Lb
            r1 = 2147483647(0x7fffffff, float:NaN)
            r5 = r1
            goto Ld
        Lb:
            r5 = r16
        Ld:
            r1 = r0 & 8
            r2 = 1
            if (r1 == 0) goto L14
            r6 = r2
            goto L16
        L14:
            r6 = r17
        L16:
            r1 = r0 & 16
            if (r1 == 0) goto L1c
            r7 = r2
            goto L1e
        L1c:
            r7 = r18
        L1e:
            r1 = r0 & 32
            if (r1 == 0) goto L2b
            androidx.compose.ui.text.style.TextOverflow$Companion r1 = androidx.compose.ui.text.style.TextOverflow.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextOverflow.Clip
            r8 = r1
            goto L2d
        L2b:
            r8 = r19
        L2d:
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L35
            kotlin.collections.EmptyList r0 = kotlin.collections.EmptyList.INSTANCE
            r11 = r0
            goto L37
        L35:
            r11 = r22
        L37:
            r12 = 0
            r2 = r13
            r3 = r14
            r4 = r15
            r9 = r20
            r10 = r21
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.TextDelegate.<init>(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.text.TextStyle, int, int, boolean, int, androidx.compose.ui.unit.Density, androidx.compose.ui.text.font.FontFamily$Resolver, java.util.List, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
