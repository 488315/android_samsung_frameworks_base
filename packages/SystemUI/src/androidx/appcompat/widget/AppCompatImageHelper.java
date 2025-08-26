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
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        ImageView imageView = this.mView;
        Context context2 = imageView.getContext();
        TypedArray typedArray = tintTypedArrayObtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(imageView, context2, iArr, attributeSet, typedArray, i, 0);
        try {
            Drawable drawable = this.mView.getDrawable();
            if (drawable == null && (resourceId = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(1, -1)) != -1 && (drawable = AppCompatResources.getDrawable(resourceId, this.mView.getContext())) != null) {
                this.mView.setImageDrawable(drawable);
            }
            if (drawable != null) {
                Rect rect = DrawableUtils.INSETS_NONE;
            }
            if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(2)) {
                this.mView.setImageTintList(tintTypedArrayObtainStyledAttributes.getColorStateList(2));
            }
            if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(3)) {
                this.mView.setImageTintMode(DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.mWrapped.getInt(3, -1), null));
            }
            tintTypedArrayObtainStyledAttributes.recycle();
        } catch (Throwable th) {
            tintTypedArrayObtainStyledAttributes.recycle();
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
