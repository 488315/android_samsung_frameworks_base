package androidx.compose.ui.text.font;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FontFamilyResolver_androidKt {
    public static final FontFamilyResolverImpl createFontFamilyResolver(Context context) {
        AndroidFontLoader androidFontLoader = new AndroidFontLoader(context);
        FontWeightAdjustmentHelper.INSTANCE.getClass();
        return new FontFamilyResolverImpl(androidFontLoader, new AndroidFontResolveInterceptor(FontWeightAdjustmentHelperApi31.INSTANCE.fontWeightAdjustment(context)), null, null, null, 28, null);
    }
}
