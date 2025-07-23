package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppCompatImageHelper {
    public TintInfo mImageTint;
    public int mLevel = 0;
    public final ImageView mView;

    public AppCompatImageHelper(ImageView imageView) {
        this.mView = imageView;
    }

    public final void applySupportImageTint() {
        TintInfo tintInfo;
        Drawable drawable = this.mView.getDrawable();
        if (drawable != null) {
            Rect rect = DrawableUtils.INSETS_NONE;
        }
        if (drawable == null || (tintInfo = this.mImageTint) == null) {
            return;
        }
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
    }

    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        Context context = this.mView.getContext();
        int[] iArr = R$styleable.AppCompatImageView;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        ImageView imageView = this.mView;
        Context context2 = imageView.getContext();
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(imageView, context2, iArr, attributeSet, typedArray, i, 0);
        try {
            Drawable drawable = this.mView.getDrawable();
            if (drawable == null && (resourceId = obtainStyledAttributes.mWrapped.getResourceId(1, -1)) != -1 && (drawable = AppCompatResources.getDrawable(resourceId, this.mView.getContext())) != null) {
                this.mView.setImageDrawable(drawable);
            }
            if (drawable != null) {
                Rect rect = DrawableUtils.INSETS_NONE;
            }
            if (obtainStyledAttributes.mWrapped.hasValue(2)) {
                this.mView.setImageTintList(obtainStyledAttributes.getColorStateList(2));
            }
            if (obtainStyledAttributes.mWrapped.hasValue(3)) {
                this.mView.setImageTintMode(DrawableUtils.parseTintMode(obtainStyledAttributes.mWrapped.getInt(3, -1), null));
            }
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final void setImageResource(int i) {
        if (i != 0) {
            Drawable drawable = AppCompatResources.getDrawable(i, this.mView.getContext());
            if (drawable != null) {
                Rect rect = DrawableUtils.INSETS_NONE;
            }
            this.mView.setImageDrawable(drawable);
        } else {
            this.mView.setImageDrawable(null);
        }
        applySupportImageTint();
    }
}
