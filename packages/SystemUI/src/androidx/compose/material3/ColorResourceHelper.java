package androidx.compose.material3;

import android.content.Context;
import androidx.compose.ui.graphics.ColorKt;

/* loaded from: classes.dex */
final class ColorResourceHelper {
    public static final ColorResourceHelper INSTANCE = new ColorResourceHelper();

    private ColorResourceHelper() {
    }

    /* renamed from: getColor-WaAFU9c, reason: not valid java name */
    public static long m256getColorWaAFU9c(int i, Context context) {
        return ColorKt.Color(context.getResources().getColor(i, context.getTheme()));
    }
}
