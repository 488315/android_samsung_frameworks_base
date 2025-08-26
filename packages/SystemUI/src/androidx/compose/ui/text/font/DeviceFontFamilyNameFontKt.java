package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.FontVariation;

/* loaded from: classes.dex */
public abstract class DeviceFontFamilyNameFontKt {
    /* renamed from: Font-vxs03AY$default, reason: not valid java name */
    public static Font m763Fontvxs03AY$default(String str, FontWeight fontWeight, int i) {
        if ((i & 2) != 0) {
            FontWeight.Companion.getClass();
            fontWeight = FontWeight.Normal;
        }
        FontWeight fontWeight2 = fontWeight;
        FontStyle.Companion.getClass();
        return new DeviceFontFamilyNameFont(str, fontWeight2, 0, new FontVariation.Settings(new FontVariation.Setting[0]), null);
    }
}
