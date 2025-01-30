package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.collection.LongSparseArray;
import androidx.collection.LruCache;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ResourceManagerInternal {
    public static ResourceManagerInternal INSTANCE;
    public final WeakHashMap mDrawableCaches = new WeakHashMap(0);
    public boolean mHasCheckedVectorDrawableSetup;
    public AppCompatDrawableManager.C00581 mHooks;
    public TypedValue mTypedValue;
    public static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    public static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ColorFilterLruCache extends LruCache {
        public ColorFilterLruCache(int i) {
            super(i);
        }
    }

    public static synchronized ResourceManagerInternal get() {
        ResourceManagerInternal resourceManagerInternal;
        synchronized (ResourceManagerInternal.class) {
            if (INSTANCE == null) {
                INSTANCE = new ResourceManagerInternal();
            }
            resourceManagerInternal = INSTANCE;
        }
        return resourceManagerInternal;
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        synchronized (ResourceManagerInternal.class) {
            ColorFilterLruCache colorFilterLruCache = COLOR_FILTER_CACHE;
            colorFilterLruCache.getClass();
            int i2 = (i + 31) * 31;
            porterDuffColorFilter = (PorterDuffColorFilter) colorFilterLruCache.get(Integer.valueOf(mode.hashCode() + i2));
            if (porterDuffColorFilter == null) {
                porterDuffColorFilter = new PorterDuffColorFilter(i, mode);
                colorFilterLruCache.getClass();
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

    public final synchronized ColorStateList getTintList(int i, Context context) {
        AppCompatDrawableManager.C00581 c00581 = this.mHooks;
        return null;
    }

    public final Drawable loadDrawableFromDelegates(int i, Context context) {
        return null;
    }

    public final Drawable tintDrawable(Context context, int i, boolean z, Drawable drawable) {
        getTintList(i, context);
        if (z) {
            return null;
        }
        return drawable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x002b, code lost:
    
        if (r0 == false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized Drawable getDrawable(Context context, int i, boolean z) {
        boolean z2;
        Drawable cachedDrawable;
        if (!this.mHasCheckedVectorDrawableSetup) {
            this.mHasCheckedVectorDrawableSetup = true;
            Drawable drawable = getDrawable(R.drawable.abc_vector_test, context);
            if (drawable != null) {
                if (!(drawable instanceof VectorDrawableCompat) && !"android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName())) {
                    z2 = false;
                }
                z2 = true;
            }
            this.mHasCheckedVectorDrawableSetup = false;
            throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        context.getResources().getValue(i, this.mTypedValue, true);
        cachedDrawable = getCachedDrawable(context, (r0.assetCookie << 32) | r0.data);
        if (cachedDrawable == null) {
            cachedDrawable = null;
        }
        if (cachedDrawable == null) {
            Object obj = ContextCompat.sLock;
            cachedDrawable = context.getDrawable(i);
        }
        if (cachedDrawable != null) {
            cachedDrawable = tintDrawable(context, i, z, cachedDrawable);
        }
        if (cachedDrawable != null) {
            Rect rect = DrawableUtils.INSETS_NONE;
        }
        return cachedDrawable;
    }
}
