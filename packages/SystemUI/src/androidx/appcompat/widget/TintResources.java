package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class TintResources extends ResourcesWrapper {
    public final WeakReference mContextRef;

    public TintResources(Context context, Resources resources) {
        super(resources);
        this.mContextRef = new WeakReference(context);
    }

    @Override // androidx.appcompat.widget.ResourcesWrapper, android.content.res.Resources
    public final Drawable getDrawable(int i) {
        Drawable drawableCanonical = getDrawableCanonical(i);
        Context context = (Context) this.mContextRef.get();
        if (drawableCanonical != null && context != null) {
            ResourceManagerInternal.get().getClass();
        }
        return drawableCanonical;
    }
}
