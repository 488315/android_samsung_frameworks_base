package androidx.compose.ui.platform;

import android.content.Context;
import androidx.compose.ui.text.font.Font;
import androidx.compose.ui.text.font.ResourceFont;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
