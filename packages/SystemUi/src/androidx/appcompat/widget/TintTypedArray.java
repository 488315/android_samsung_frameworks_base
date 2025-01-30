package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextHelper;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TintTypedArray {
    public final Context mContext;
    public TypedValue mTypedValue;
    public final TypedArray mWrapped;

    private TintTypedArray(Context context, TypedArray typedArray) {
        this.mContext = context;
        this.mWrapped = typedArray;
    }

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr) {
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr));
    }

    public final boolean getBoolean(int i, boolean z) {
        return this.mWrapped.getBoolean(i, z);
    }

    public final ColorStateList getColorStateList(int i) {
        int resourceId;
        ColorStateList colorStateList;
        TypedArray typedArray = this.mWrapped;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0 || (colorStateList = ContextCompat.getColorStateList(resourceId, this.mContext)) == null) ? typedArray.getColorStateList(i) : colorStateList;
    }

    public final int getDimensionPixelOffset(int i, int i2) {
        return this.mWrapped.getDimensionPixelOffset(i, i2);
    }

    public final int getDimensionPixelSize(int i, int i2) {
        return this.mWrapped.getDimensionPixelSize(i, i2);
    }

    public final Drawable getDrawable(int i) {
        int resourceId;
        TypedArray typedArray = this.mWrapped;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0) ? typedArray.getDrawable(i) : AppCompatResources.getDrawable(resourceId, this.mContext);
    }

    public final Drawable getDrawableIfKnown(int i) {
        int resourceId;
        Drawable drawable;
        if (!this.mWrapped.hasValue(i) || (resourceId = this.mWrapped.getResourceId(i, 0)) == 0) {
            return null;
        }
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        Context context = this.mContext;
        synchronized (appCompatDrawableManager) {
            drawable = appCompatDrawableManager.mResourceManager.getDrawable(context, resourceId, true);
        }
        return drawable;
    }

    public final Typeface getFont(int i, int i2, AppCompatTextHelper.C00651 c00651) {
        int resourceId = this.mWrapped.getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        Context context = this.mContext;
        if (context.isRestricted()) {
            return null;
        }
        return ResourcesCompat.loadFont(context, resourceId, typedValue, i2, c00651, true, false);
    }

    public final int getInt(int i, int i2) {
        return this.mWrapped.getInt(i, i2);
    }

    public final int getResourceId(int i, int i2) {
        return this.mWrapped.getResourceId(i, i2);
    }

    public final String getString(int i) {
        return this.mWrapped.getString(i);
    }

    public final CharSequence getText(int i) {
        return this.mWrapped.getText(i);
    }

    public final boolean hasValue(int i) {
        return this.mWrapped.hasValue(i);
    }

    public final void recycle() {
        this.mWrapped.recycle();
    }

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }

    public static TintTypedArray obtainStyledAttributes(Context context, int i, int[] iArr) {
        return new TintTypedArray(context, context.obtainStyledAttributes(i, iArr));
    }
}
