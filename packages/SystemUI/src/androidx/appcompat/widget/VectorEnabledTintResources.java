package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class VectorEnabledTintResources extends ResourcesWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final WeakReference mContextRef;

    public VectorEnabledTintResources(Context context, Resources resources) {
        super(resources);
        this.mContextRef = new WeakReference(context);
    }

    @Override // androidx.appcompat.widget.ResourcesWrapper, android.content.res.Resources
    public final Drawable getDrawable(int i) {
        if (((Context) this.mContextRef.get()) == null) {
            return getDrawableCanonical(i);
        }
        ResourceManagerInternal resourceManagerInternal = ResourceManagerInternal.get();
        synchronized (resourceManagerInternal) {
            Drawable drawableCanonical = getDrawableCanonical(i);
            if (drawableCanonical == null) {
                return null;
            }
            synchronized (resourceManagerInternal) {
            }
            return drawableCanonical;
        }
    }
}
