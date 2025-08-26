package androidx.compose.ui.text.font;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class PlatformTypefacesApi28 implements PlatformTypefaces {
    /* renamed from: createAndroidTypefaceApi28-RetOiIg, reason: not valid java name */
    public static android.graphics.Typeface m771createAndroidTypefaceApi28RetOiIg(String str, FontWeight fontWeight, int i) {
        FontStyle.Companion.getClass();
        if (i == 0) {
            FontWeight.Companion.getClass();
            if (Intrinsics.areEqual(fontWeight, FontWeight.Normal) && (str == null || str.length() == 0)) {
                return android.graphics.Typeface.DEFAULT;
            }
        }
        return android.graphics.Typeface.create(str == null ? android.graphics.Typeface.DEFAULT : android.graphics.Typeface.create(str, 0), fontWeight.weight, i == FontStyle.Italic);
    }
}
