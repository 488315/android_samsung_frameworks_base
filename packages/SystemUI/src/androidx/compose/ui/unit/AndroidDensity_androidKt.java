package androidx.compose.ui.unit;

import android.content.Context;
import androidx.compose.ui.unit.fontscaling.FontScaleConverter;
import androidx.compose.ui.unit.fontscaling.FontScaleConverterFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidDensity_androidKt {
    public static final Density Density(Context context) {
        float f = context.getResources().getConfiguration().fontScale;
        float f2 = context.getResources().getDisplayMetrics().density;
        FontScaleConverterFactory.INSTANCE.getClass();
        FontScaleConverter forScale = FontScaleConverterFactory.forScale(f);
        if (forScale == null) {
            forScale = new LinearFontScaleConverter(f);
        }
        return new DensityWithConverter(f2, f, forScale);
    }
}
