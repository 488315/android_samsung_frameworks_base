package androidx.compose.material3;

import android.content.Context;
import androidx.compose.ui.graphics.ColorKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ColorResourceHelper {
    public static final ColorResourceHelper INSTANCE = new ColorResourceHelper();

    private ColorResourceHelper() {
    }

    /* renamed from: getColor-WaAFU9c, reason: not valid java name */
    public static long m255getColorWaAFU9c(int i, Context context) {
        return ColorKt.Color(context.getResources().getColor(i, context.getTheme()));
    }
}
