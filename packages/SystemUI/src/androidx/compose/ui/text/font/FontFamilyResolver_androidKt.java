package androidx.compose.ui.text.font;

import android.content.Context;

/* loaded from: classes.dex */
public abstract class FontFamilyResolver_androidKt {
    public static final FontFamilyResolverImpl createFontFamilyResolver(Context context) {
        AndroidFontLoader androidFontLoader = new AndroidFontLoader(context);
        FontWeightAdjustmentHelper.INSTANCE.getClass();
        return new FontFamilyResolverImpl(androidFontLoader, new AndroidFontResolveInterceptor(FontWeightAdjustmentHelperApi31.INSTANCE.fontWeightAdjustment(context)), null, null, null, 28, null);
    }
}
