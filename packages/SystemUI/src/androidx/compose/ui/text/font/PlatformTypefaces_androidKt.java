package androidx.compose.ui.text.font;

import android.content.Context;
import android.graphics.Paint;
import androidx.compose.ui.text.font.FontVariation;
import androidx.compose.ui.unit.AndroidDensity_androidKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.util.ListUtilsKt;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PlatformTypefaces_androidKt {
    public static final android.graphics.Typeface setFontVariationSettings(android.graphics.Typeface typeface, FontVariation.Settings settings, Context context) {
        TypefaceCompatApi26.INSTANCE.getClass();
        if (typeface == null) {
            return null;
        }
        if (((ArrayList) settings.settings).isEmpty()) {
            return typeface;
        }
        ThreadLocal threadLocal = TypefaceCompatApi26.threadLocalPaint;
        Paint paint = (Paint) threadLocal.get();
        if (paint == null) {
            paint = new Paint();
            threadLocal.set(paint);
        }
        paint.setTypeface(typeface);
        final Density Density = AndroidDensity_androidKt.Density(context);
        paint.setFontVariationSettings(ListUtilsKt.fastJoinToString$default(settings.settings, null, new Function1() { // from class: androidx.compose.ui.text.font.TypefaceCompatApi26$toAndroidString$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                FontVariation.Setting setting = (FontVariation.Setting) obj;
                return "'" + setting.getAxisName() + "' " + setting.toVariationValue();
            }
        }, 31));
        return paint.getTypeface();
    }
}
