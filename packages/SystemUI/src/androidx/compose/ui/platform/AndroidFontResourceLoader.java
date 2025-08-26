package androidx.compose.ui.platform;

import android.content.Context;
import androidx.compose.ui.text.font.Font;
import androidx.compose.ui.text.font.ResourceFont;

/* loaded from: classes.dex */
public final class AndroidFontResourceLoader implements Font.ResourceLoader {
    public final Context context;

    public AndroidFontResourceLoader(Context context) {
        this.context = context;
    }

    public final Object load(Font font) {
        if (!(font instanceof ResourceFont)) {
            throw new IllegalArgumentException("Unknown font type: " + font);
        }
        AndroidFontResourceLoaderHelper androidFontResourceLoaderHelper = AndroidFontResourceLoaderHelper.INSTANCE;
        Context context = this.context;
        int i = ((ResourceFont) font).resId;
        androidFontResourceLoaderHelper.getClass();
        return context.getResources().getFont(i);
    }
}
