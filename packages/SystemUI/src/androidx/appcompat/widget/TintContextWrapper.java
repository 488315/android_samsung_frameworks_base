package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TintContextWrapper extends ContextWrapper {
    public static final Object CACHE_LOCK = null;
    public final TintResources mResources;

    private TintContextWrapper(Context context) {
        super(context);
        int i = VectorEnabledTintResources.$r8$clinit;
        this.mResources = new TintResources(this, context.getResources());
    }

    public static void wrap(Context context) {
        if ((context instanceof TintContextWrapper) || (context.getResources() instanceof TintResources) || (context.getResources() instanceof VectorEnabledTintResources)) {
            return;
        }
        int i = VectorEnabledTintResources.$r8$clinit;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final AssetManager getAssets() {
        return this.mResources.getAssets();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        return this.mResources;
    }
}
