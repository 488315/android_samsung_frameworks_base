package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;

/* loaded from: classes.dex */
public final class TintContextWrapper extends ContextWrapper {
    private static final Object CACHE_LOCK = new Object();

    public static void wrap(Context context) {
        if ((context instanceof TintContextWrapper) || (context.getResources() instanceof TintResources)) {
            return;
        }
        context.getResources();
        int i = VectorEnabledTintResources.$r8$clinit;
    }
}
