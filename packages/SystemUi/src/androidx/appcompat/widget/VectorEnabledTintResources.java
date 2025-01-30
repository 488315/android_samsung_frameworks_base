package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class VectorEnabledTintResources extends ResourcesWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final WeakReference mContextRef;

    public VectorEnabledTintResources(Context context, Resources resources) {
        super(resources);
        this.mContextRef = new WeakReference(context);
    }

    @Override // androidx.appcompat.widget.ResourcesWrapper, android.content.res.Resources
    public final Drawable getDrawable(int i) {
        Context context = (Context) this.mContextRef.get();
        if (context == null) {
            return getDrawableCanonical(i);
        }
        ResourceManagerInternal resourceManagerInternal = ResourceManagerInternal.get();
        synchronized (resourceManagerInternal) {
            Drawable loadDrawableFromDelegates = resourceManagerInternal.loadDrawableFromDelegates(i, context);
            if (loadDrawableFromDelegates == null) {
                loadDrawableFromDelegates = getDrawableCanonical(i);
            }
            if (loadDrawableFromDelegates == null) {
                return null;
            }
            return resourceManagerInternal.tintDrawable(context, i, false, loadDrawableFromDelegates);
        }
    }
}
