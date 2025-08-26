package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.TextUnit;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ParagraphStyle implements AnnotatedString.Annotation {
    public final int hyphens;
    public final int lineBreak;
    public final long lineHeight;
    public final LineHeightStyle lineHeightStyle;
    public final PlatformParagraphStyle platformStyle;
    public final int textAlign;
    public final int textDirection;
    public final TextIndent textIndent;
    public final TextMotion textMotion;

    public /* synthetic */ ParagraphStyle(int i, int i2, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, j, textIndent, platformParagraphStyle, lineHeightStyle, i3, i4, textMotion);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ParagraphStyle)) {
            return false;
        }
        ParagraphStyle paragraphStyle = (ParagraphStyle) obj;
        int i = paragraphStyle.textAlign;
        TextAlign.Companion companion = TextAlign.Companion;
        if (this.textAlign == i) {
            int i2 = paragraphStyle.textDirection;
            TextDirection.Companion companion2 = TextDirection.Companion;
            if (this.textDirection != i2 || !TextUnit.m868equalsimpl0(this.lineHeight, paragraphStyle.lineHeight) || !Intrinsics.areEqual(this.textIndent, paragraphStyle.textIndent) || !Intrinsics.areEqual(this.platformStyle, paragraphStyle.platformStyle) || !Intrinsics.areEqual(this.lineHeightStyle, paragraphStyle.lineHeightStyle)) {
                return false;
            }
            int i3 = paragraphStyle.lineBreak;
            LineBreak.Companion companion3 = LineBreak.Companion;
            if (this.lineBreak == i3) {
                int i4 = paragraphStyle.hyphens;
                Hyphens.Companion companion4 = Hyphens.Companion;
                return this.hyphens == i4 && Intrinsics.areEqual(this.textMotion, paragraphStyle.textMotion);
            }
        }
        return false;
    }

    public final int hashCode() {
        TextAlign.Companion companion = TextAlign.Companion;
        int iHashCode = Integer.hashCode(this.textAlign) * 31;
        TextDirection.Companion companion2 = TextDirection.Companion;
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.textDirection, iHashCode, 31);
        TextUnit.Companion companion3 = TextUnit.Companion;
        int iM2 = MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.lineHeight);
        TextIndent textIndent = this.textIndent;
        int iHashCode2 = (iM2 + (textIndent != null ? textIndent.hashCode() : 0)) * 31;
        PlatformParagraphStyle platformParagraphStyle = this.platformStyle;
        int iHashCode3 = (iHashCode2 + (platformParagraphStyle != null ? platformParagraphStyle.hashCode() : 0)) * 31;
        LineHeightStyle lineHeightStyle = this.lineHeightStyle;
        int iHashCode4 = (iHashCode3 + (lineHeightStyle != null ? lineHeightStyle.hashCode() : 0)) * 31;
        LineBreak.Companion companion4 = LineBreak.Companion;
        int iM3 = ReorderTile$$ExternalSyntheticOutline0.m(this.lineBreak, iHashCode4, 31);
        Hyphens.Companion companion5 = Hyphens.Companion;
        int iM4 = ReorderTile$$ExternalSyntheticOutline0.m(this.hyphens, iM3, 31);
        TextMotion textMotion = this.textMotion;
        return iM4 + (textMotion != null ? textMotion.hashCode() : 0);
    }

    public final ParagraphStyle merge(ParagraphStyle paragraphStyle) {
        if (paragraphStyle == null) {
            return this;
        }
        return ParagraphStyleKt.m741fastMergej5T8yCg(this, paragraphStyle.textAlign, paragraphStyle.textDirection, paragraphStyle.lineHeight, paragraphStyle.textIndent, paragraphStyle.platformStyle, paragraphStyle.lineHeightStyle, paragraphStyle.lineBreak, paragraphStyle.hyphens, paragraphStyle.textMotion);
    }

    public final String toString() {
        return "ParagraphStyle(textAlign=" + ((Object) TextAlign.m808toStringimpl(this.textAlign)) + ", textDirection=" + ((Object) TextDirection.m810toStringimpl(this.textDirection)) + ", lineHeight=" + ((Object) TextUnit.m872toStringimpl(this.lineHeight)) + ", textIndent=" + this.textIndent + ", platformStyle=" + this.platformStyle + ", lineHeightStyle=" + this.lineHeightStyle + ", lineBreak=" + ((Object) LineBreak.m798toStringimpl(this.lineBreak)) + ", hyphens=" + ((Object) Hyphens.m796toStringimpl(this.hyphens)) + ", textMotion=" + this.textMotion + ')';
    }

    public /* synthetic */ ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion, DefaultConstructorMarker defaultConstructorMarker) {
        this(textAlign, textDirection, j, textIndent, platformParagraphStyle, lineHeightStyle, lineBreak, hyphens, textMotion);
    }

    public /* synthetic */ ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, DefaultConstructorMarker defaultConstructorMarker) {
        this(textAlign, textDirection, j, textIndent, platformParagraphStyle, lineHeightStyle, lineBreak, hyphens);
    }

    public /* synthetic */ ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, DefaultConstructorMarker defaultConstructorMarker) {
        this(textAlign, textDirection, j, textIndent, platformParagraphStyle, lineHeightStyle);
    }

    public /* synthetic */ ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, DefaultConstructorMarker defaultConstructorMarker) {
        this(textAlign, textDirection, j, textIndent);
    }

    private ParagraphStyle(int i, int i2, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion) {
        this.textAlign = i;
        this.textDirection = i2;
        this.lineHeight = j;
        this.textIndent = textIndent;
        this.platformStyle = platformParagraphStyle;
        this.lineHeightStyle = lineHeightStyle;
        this.lineBreak = i3;
        this.hyphens = i4;
        this.textMotion = textMotion;
        TextUnit.Companion.getClass();
        if (TextUnit.m868equalsimpl0(j, TextUnit.Unspecified)) {
            return;
        }
        if (TextUnit.m870getValueimpl(j) >= 0.0f) {
            return;
        }
        InlineClassHelperKt.throwIllegalStateException("lineHeight can't be negative (" + TextUnit.m870getValueimpl(j) + ')');
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ParagraphStyle(int i, int i2, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        int i6;
        int i7;
        long j2;
        int i8;
        int i9;
        if ((i5 & 1) != 0) {
            TextAlign.Companion.getClass();
            i6 = TextAlign.Unspecified;
        } else {
            i6 = i;
        }
        if ((i5 & 2) != 0) {
            TextDirection.Companion.getClass();
            i7 = TextDirection.Unspecified;
        } else {
            i7 = i2;
        }
        if ((i5 & 4) != 0) {
            TextUnit.Companion.getClass();
            j2 = TextUnit.Unspecified;
        } else {
            j2 = j;
        }
        TextIndent textIndent2 = (i5 & 8) != 0 ? null : textIndent;
        PlatformParagraphStyle platformParagraphStyle2 = (i5 & 16) != 0 ? null : platformParagraphStyle;
        LineHeightStyle lineHeightStyle2 = (i5 & 32) != 0 ? null : lineHeightStyle;
        if ((i5 & 64) != 0) {
            LineBreak.Companion.getClass();
            i8 = 0;
        } else {
            i8 = i3;
        }
        if ((i5 & 128) != 0) {
            Hyphens.Companion.getClass();
            i9 = Hyphens.Unspecified;
        } else {
            i9 = i4;
        }
        this(i6, i7, j2, textIndent2, platformParagraphStyle2, lineHeightStyle2, i8, i9, (i5 & 256) == 0 ? textMotion : null, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion, int i, DefaultConstructorMarker defaultConstructorMarker) {
        textAlign = (i & 1) != 0 ? null : textAlign;
        textDirection = (i & 2) != 0 ? null : textDirection;
        if ((i & 4) != 0) {
            TextUnit.Companion.getClass();
            j = TextUnit.Unspecified;
        }
        this(textAlign, textDirection, j, (i & 8) != 0 ? null : textIndent, (i & 16) != 0 ? null : platformParagraphStyle, (i & 32) != 0 ? null : lineHeightStyle, (i & 64) != 0 ? null : lineBreak, (i & 128) != 0 ? null : hyphens, (i & 256) != 0 ? null : textMotion, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion) {
        int i;
        int i2;
        int i3;
        int i4;
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        int i5 = i2;
        if (lineBreak != null) {
            i3 = lineBreak.mask;
        } else {
            LineBreak.Companion.getClass();
            i3 = 0;
        }
        int i6 = i3;
        if (hyphens != null) {
            i4 = hyphens.value;
        } else {
            Hyphens.Companion.getClass();
            i4 = Hyphens.Unspecified;
        }
        this(i, i5, j, textIndent, platformParagraphStyle, lineHeightStyle, i6, i4, textMotion, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, int i, DefaultConstructorMarker defaultConstructorMarker) {
        textAlign = (i & 1) != 0 ? null : textAlign;
        textDirection = (i & 2) != 0 ? null : textDirection;
        if ((i & 4) != 0) {
            TextUnit.Companion.getClass();
            j = TextUnit.Unspecified;
        }
        this(textAlign, textDirection, j, (i & 8) != 0 ? null : textIndent, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent) {
        int i;
        int i2;
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        int i3 = i;
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        int i4 = i2;
        LineBreak.Companion.getClass();
        Hyphens.Companion.getClass();
        this(i3, i4, j, textIndent, (PlatformParagraphStyle) null, (LineHeightStyle) null, 0, Hyphens.Unspecified, (TextMotion) null, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        textAlign = (i & 1) != 0 ? null : textAlign;
        textDirection = (i & 2) != 0 ? null : textDirection;
        if ((i & 4) != 0) {
            TextUnit.Companion.getClass();
            j = TextUnit.Unspecified;
        }
        this(textAlign, textDirection, j, (i & 8) != 0 ? null : textIndent, (i & 16) != 0 ? null : platformParagraphStyle, (i & 32) != 0 ? null : lineHeightStyle, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle) {
        int i;
        int i2;
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        int i3 = i;
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        int i4 = i2;
        LineBreak.Companion.getClass();
        Hyphens.Companion.getClass();
        this(i3, i4, j, textIndent, platformParagraphStyle, lineHeightStyle, 0, Hyphens.Unspecified, (TextMotion) null, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, int i, DefaultConstructorMarker defaultConstructorMarker) {
        textAlign = (i & 1) != 0 ? null : textAlign;
        textDirection = (i & 2) != 0 ? null : textDirection;
        if ((i & 4) != 0) {
            TextUnit.Companion.getClass();
            j = TextUnit.Unspecified;
        }
        this(textAlign, textDirection, j, (i & 8) != 0 ? null : textIndent, (i & 16) != 0 ? null : platformParagraphStyle, (i & 32) != 0 ? null : lineHeightStyle, (i & 64) != 0 ? null : lineBreak, (i & 128) != 0 ? null : hyphens, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens) {
        int i;
        int i2;
        int i3;
        int i4;
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        int i5 = i2;
        if (lineBreak != null) {
            i3 = lineBreak.mask;
        } else {
            LineBreak.Companion.getClass();
            i3 = 0;
        }
        int i6 = i3;
        if (hyphens != null) {
            i4 = hyphens.value;
        } else {
            Hyphens.Companion.getClass();
            i4 = Hyphens.Unspecified;
        }
        this(i, i5, j, textIndent, platformParagraphStyle, lineHeightStyle, i6, i4, (TextMotion) null, (DefaultConstructorMarker) null);
    }
}
