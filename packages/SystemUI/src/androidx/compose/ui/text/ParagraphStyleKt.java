package androidx.compose.ui.text;

import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.TextUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class ParagraphStyleKt {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long DefaultLineHeight;

    static {
        TextUnit.Companion.getClass();
        DefaultLineHeight = TextUnit.Unspecified;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00f6  */
    /* renamed from: fastMerge-j5T8yCg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final ParagraphStyle m741fastMergej5T8yCg(ParagraphStyle paragraphStyle, int i, int i2, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion) {
        long j2;
        int i5 = i;
        long j3 = j;
        TextAlign.Companion companion = TextAlign.Companion;
        companion.getClass();
        int i6 = TextAlign.Unspecified;
        if (i5 != i6) {
            j2 = 0;
            if (i5 == paragraphStyle.textAlign) {
            }
            TextUnit.Companion companion2 = TextUnit.Companion;
            if ((j3 & 1095216660480L) == j2) {
                j3 = paragraphStyle.lineHeight;
            }
            long j4 = j3;
            TextIndent textIndent2 = textIndent != null ? paragraphStyle.textIndent : textIndent;
            companion.getClass();
            if (i5 == i6) {
                i5 = paragraphStyle.textAlign;
            }
            int i7 = i5;
            TextDirection.Companion.getClass();
            int i8 = i2 != TextDirection.Unspecified ? paragraphStyle.textDirection : i2;
            PlatformParagraphStyle platformParagraphStyle2 = paragraphStyle.platformStyle;
            PlatformParagraphStyle platformParagraphStyle3 = (platformParagraphStyle2 == null && platformParagraphStyle == null) ? platformParagraphStyle2 : platformParagraphStyle;
            LineHeightStyle lineHeightStyle2 = lineHeightStyle != null ? paragraphStyle.lineHeightStyle : lineHeightStyle;
            LineBreak.Companion.getClass();
            int i9 = i3 != 0 ? paragraphStyle.lineBreak : i3;
            Hyphens.Companion.getClass();
            return new ParagraphStyle(i7, i8, j4, textIndent2, platformParagraphStyle3, lineHeightStyle2, i9, i4 != Hyphens.Unspecified ? paragraphStyle.hyphens : i4, textMotion != null ? paragraphStyle.textMotion : textMotion, (DefaultConstructorMarker) null);
        }
        j2 = 0;
        TextUnit.Companion companion3 = TextUnit.Companion;
        if ((((j3 & 1095216660480L) == j2) || TextUnit.m868equalsimpl0(j3, paragraphStyle.lineHeight)) && (textIndent == null || textIndent.equals(paragraphStyle.textIndent))) {
            TextDirection.Companion.getClass();
            if ((i2 == TextDirection.Unspecified || i2 == paragraphStyle.textDirection) && ((platformParagraphStyle == null || platformParagraphStyle.equals(paragraphStyle.platformStyle)) && (lineHeightStyle == null || lineHeightStyle.equals(paragraphStyle.lineHeightStyle)))) {
                LineBreak.Companion.getClass();
                if (i3 == 0 || i3 == paragraphStyle.lineBreak) {
                    Hyphens.Companion.getClass();
                    if ((i4 == Hyphens.Unspecified || i4 == paragraphStyle.hyphens) && (textMotion == null || textMotion.equals(paragraphStyle.textMotion))) {
                        return paragraphStyle;
                    }
                }
            }
        }
        TextUnit.Companion companion22 = TextUnit.Companion;
        if ((j3 & 1095216660480L) == j2) {
        }
        long j42 = j3;
        if (textIndent != null) {
        }
        companion.getClass();
        if (i5 == i6) {
        }
        int i72 = i5;
        TextDirection.Companion.getClass();
        if (i2 != TextDirection.Unspecified) {
        }
        PlatformParagraphStyle platformParagraphStyle22 = paragraphStyle.platformStyle;
        if (platformParagraphStyle22 == null) {
        }
        if (lineHeightStyle != null) {
        }
        LineBreak.Companion.getClass();
        if (i3 != 0) {
        }
        Hyphens.Companion.getClass();
        return new ParagraphStyle(i72, i8, j42, textIndent2, platformParagraphStyle3, lineHeightStyle2, i9, i4 != Hyphens.Unspecified ? paragraphStyle.hyphens : i4, textMotion != null ? paragraphStyle.textMotion : textMotion, (DefaultConstructorMarker) null);
    }
}
