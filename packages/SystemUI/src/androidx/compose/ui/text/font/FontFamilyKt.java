package androidx.compose.ui.text.font;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FontFamilyKt {
    public static final FontListFontFamily FontFamily(Font... fontArr) {
        return new FontListFontFamily(Arrays.asList(fontArr));
    }
}
