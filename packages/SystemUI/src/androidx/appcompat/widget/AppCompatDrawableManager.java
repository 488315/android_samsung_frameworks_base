package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AppCompatDrawableManager {
    public static AppCompatDrawableManager INSTANCE;
    public ResourceManagerInternal mResourceManager;

    static {
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
    }

    public static synchronized AppCompatDrawableManager get() {
        AppCompatDrawableManager appCompatDrawableManager;
        synchronized (AppCompatDrawableManager.class) {
            try {
                if (INSTANCE == null) {
                    preload();
                }
                appCompatDrawableManager = INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return appCompatDrawableManager;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.appcompat.widget.AppCompatDrawableManager$1] */
    public static synchronized void preload() {
        synchronized (AppCompatDrawableManager.class) {
            if (INSTANCE == null) {
                AppCompatDrawableManager appCompatDrawableManager = new AppCompatDrawableManager();
                INSTANCE = appCompatDrawableManager;
                appCompatDrawableManager.mResourceManager = ResourceManagerInternal.get();
                ResourceManagerInternal resourceManagerInternal = INSTANCE.mResourceManager;
                ?? r2 = new Object() { // from class: androidx.appcompat.widget.AppCompatDrawableManager.1
                };
                synchronized (resourceManagerInternal) {
                    resourceManagerInternal.mHooks = r2;
                }
            }
        }
    }

    public static void tintDrawable(Drawable drawable, TintInfo tintInfo, int[] iArr) {
        PorterDuff.Mode mode = ResourceManagerInternal.DEFAULT_MODE;
        int[] state = drawable.getState();
        if (drawable.mutate() != drawable) {
            Log.d("ResourceManagerInternal", "Mutated drawable is not the same instance as the input.");
            return;
        }
        if ((drawable instanceof LayerDrawable) && drawable.isStateful()) {
            drawable.setState(new int[0]);
            drawable.setState(state);
        }
        boolean z = tintInfo.mHasTintList;
        if (!z && !tintInfo.mHasTintMode) {
            drawable.clearColorFilter();
            return;
        }
        PorterDuffColorFilter porterDuffColorFilter = null;
        ColorStateList colorStateList = z ? tintInfo.mTintList : null;
        PorterDuff.Mode mode2 = tintInfo.mHasTintMode ? tintInfo.mTintMode : ResourceManagerInternal.DEFAULT_MODE;
        if (colorStateList != null && mode2 != null) {
            porterDuffColorFilter = ResourceManagerInternal.getPorterDuffColorFilter(colorStateList.getColorForState(iArr, 0), mode2);
        }
        drawable.setColorFilter(porterDuffColorFilter);
    }

    public final synchronized Drawable getDrawable(int i, Context context) {
        return this.mResourceManager.getDrawable(i, context);
    }
}
