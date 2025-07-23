package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.collection.LongSparseArray;
import androidx.collection.LruCache;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ResourceManagerInternal {
    public static ResourceManagerInternal INSTANCE;
    public final WeakHashMap mDrawableCaches = new WeakHashMap(0);
    public boolean mHasCheckedVectorDrawableSetup;
    public AppCompatDrawableManager.AnonymousClass1 mHooks;
    public TypedValue mTypedValue;
    public static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    public static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ColorFilterLruCache extends LruCache {
        public ColorFilterLruCache(int i) {
            super(i);
        }
    }

    public static synchronized ResourceManagerInternal get() {
        ResourceManagerInternal resourceManagerInternal;
        synchronized (ResourceManagerInternal.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new ResourceManagerInternal();
                }
                resourceManagerInternal = INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return resourceManagerInternal;
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        synchronized (ResourceManagerInternal.class) {
            ColorFilterLruCache colorFilterLruCache = COLOR_FILTER_CACHE;
            colorFilterLruCache.getClass();
            int i2 = (31 + i) * 31;
            porterDuffColorFilter = (PorterDuffColorFilter) colorFilterLruCache.get(Integer.valueOf(mode.hashCode() + i2));
            if (porterDuffColorFilter == null) {
                porterDuffColorFilter = new PorterDuffColorFilter(i, mode);
            }
        }
        return porterDuffColorFilter;
    }

    public final synchronized Drawable getCachedDrawable(Context context, long j) {
        LongSparseArray longSparseArray = (LongSparseArray) this.mDrawableCaches.get(context);
        if (longSparseArray == null) {
            return null;
        }
        WeakReference weakReference = (WeakReference) longSparseArray.get(j);
        if (weakReference != null) {
            Drawable.ConstantState constantState = (Drawable.ConstantState) weakReference.get();
            if (constantState != null) {
                return constantState.newDrawable(context.getResources());
            }
            longSparseArray.remove(j);
        }
        return null;
    }

    public final synchronized Drawable getDrawable(int i, Context context) {
        return getDrawable(context, i, false);
    }

    public final synchronized Drawable getDrawable(Context context, int i, boolean z) {
        Drawable cachedDrawable;
        if (!this.mHasCheckedVectorDrawableSetup) {
            this.mHasCheckedVectorDrawableSetup = true;
            Drawable drawable = getDrawable(R.drawable.abc_vector_test, context);
            if (drawable == null || (!(drawable instanceof VectorDrawableCompat) && !"android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName()))) {
                this.mHasCheckedVectorDrawableSetup = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        context.getResources().getValue(i, this.mTypedValue, true);
        cachedDrawable = getCachedDrawable(context, (r0.assetCookie << 32) | r0.data);
        Drawable drawable2 = null;
        if (cachedDrawable == null) {
            cachedDrawable = null;
        }
        if (cachedDrawable == null) {
            cachedDrawable = context.getDrawable(i);
        }
        if (cachedDrawable != null) {
            synchronized (this) {
                if (!z) {
                    drawable2 = cachedDrawable;
                }
                cachedDrawable = drawable2;
            }
        }
        if (cachedDrawable != null) {
            Rect rect = DrawableUtils.INSETS_NONE;
        }
        return cachedDrawable;
    }
}
