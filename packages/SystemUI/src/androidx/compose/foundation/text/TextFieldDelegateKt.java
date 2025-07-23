package androidx.compose.foundation.text;

import androidx.compose.ui.text.AndroidParagraph;
import androidx.compose.ui.text.ParagraphKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import com.sec.ims.settings.ImsProfile;
import kotlin.collections.EmptyList;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextFieldDelegateKt {
    public static final String EmptyTextReplacement = StringsKt__StringsJVMKt.repeat(10, ImsProfile.TIMER_NAME_H);

    public static final long computeSizeForDefaultText(TextStyle textStyle, Density density, FontFamily.Resolver resolver, String str, int i) {
        EmptyList emptyList = EmptyList.INSTANCE;
        TextOverflow.Companion.getClass();
        AndroidParagraph m738ParagraphUl8oQg4$default = ParagraphKt.m738ParagraphUl8oQg4$default(str, textStyle, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), density, resolver, emptyList, i, TextOverflow.Clip, 64);
        long ceilToIntPx = (TextDelegateKt.ceilToIntPx(m738ParagraphUl8oQg4$default.paragraphIntrinsics.getMinIntrinsicWidth()) << 32) | (TextDelegateKt.ceilToIntPx(m738ParagraphUl8oQg4$default.getHeight()) & 4294967295L);
        IntSize.Companion companion = IntSize.Companion;
        return ceilToIntPx;
    }
}
