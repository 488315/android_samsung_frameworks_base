package androidx.compose.ui.text.font;

import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class FontFamilyKt {
    public static final FontListFontFamily FontFamily(Font... fontArr) {
        return new FontListFontFamily(Arrays.asList(fontArr));
    }
}
