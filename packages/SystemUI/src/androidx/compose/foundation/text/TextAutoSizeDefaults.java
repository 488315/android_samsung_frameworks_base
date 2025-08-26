package androidx.compose.foundation.text;

import androidx.compose.ui.unit.TextUnitKt;

/* loaded from: classes.dex */
public final class TextAutoSizeDefaults {
    public static final TextAutoSizeDefaults INSTANCE = new TextAutoSizeDefaults();
    public static final long MinFontSize = TextUnitKt.getSp(12);

    static {
        TextUnitKt.getSp(112);
    }

    private TextAutoSizeDefaults() {
    }
}
