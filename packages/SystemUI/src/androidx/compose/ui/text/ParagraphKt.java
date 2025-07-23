package androidx.compose.ui.text;

import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.platform.AndroidParagraphIntrinsics;
import androidx.compose.ui.unit.Density;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ParagraphKt {
    /* renamed from: Paragraph-Ul8oQg4$default, reason: not valid java name */
    public static AndroidParagraph m738ParagraphUl8oQg4$default(String str, TextStyle textStyle, long j, Density density, FontFamily.Resolver resolver, EmptyList emptyList, int i, int i2, int i3) {
        return new AndroidParagraph(new AndroidParagraphIntrinsics(str, textStyle, (i3 & 32) != 0 ? EmptyList.INSTANCE : emptyList, EmptyList.INSTANCE, resolver, density), i, i2, j, null);
    }

    public static final int ceilToInt(float f) {
        return (int) Math.ceil(f);
    }
}
