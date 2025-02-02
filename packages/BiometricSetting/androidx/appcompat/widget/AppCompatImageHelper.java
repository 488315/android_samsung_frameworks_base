package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public final class AppCompatImageHelper {
    private TintInfo mImageTint;
    private int mLevel = 0;
    private final ImageView mView;

    public AppCompatImageHelper(ImageView imageView) {
        this.mView = imageView;
    }

    final void applyImageLevel() {
        ImageView imageView = this.mView;
        if (imageView.getDrawable() != null) {
            imageView.getDrawable().setLevel(this.mLevel);
        }
    }

    final void applySupportImageTint() {
        TintInfo tintInfo;
        ImageView imageView = this.mView;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            Rect rect = DrawableUtils.INSETS_NONE;
        }
        if (drawable == null || (tintInfo = this.mImageTint) == null) {
            return;
        }
        int[] drawableState = imageView.getDrawableState();
        int i = AppCompatDrawableManager.$r8$clinit;
        ResourceManagerInternal.tintDrawable(drawable, tintInfo, drawableState);
    }

    final ColorStateList getSupportImageTintList() {
        TintInfo tintInfo = this.mImageTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    final PorterDuff.Mode getSupportImageTintMode() {
        TintInfo tintInfo = this.mImageTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    final boolean hasOverlappingRendering() {
        return !(this.mView.getBackground() instanceof RippleDrawable);
    }

    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        ImageView imageView = this.mView;
        Context context = imageView.getContext();
        int[] iArr = R$styleable.AppCompatImageView;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        ImageView imageView2 = this.mView;
        ViewCompat.saveAttributeDataForStyleable(imageView2, imageView2.getContext(), iArr, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        try {
            Drawable drawable = imageView.getDrawable();
            if (drawable == null && (resourceId = obtainStyledAttributes.getResourceId(1, -1)) != -1 && (drawable = AppCompatResources.getDrawable(imageView.getContext(), resourceId)) != null) {
                imageView.setImageDrawable(drawable);
            }
            if (drawable != null) {
                Rect rect = DrawableUtils.INSETS_NONE;
            }
            if (obtainStyledAttributes.hasValue(2)) {
                imageView.setImageTintList(obtainStyledAttributes.getColorStateList(2));
            }
            if (obtainStyledAttributes.hasValue(3)) {
                imageView.setImageTintMode(DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(3, -1), null));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    final void obtainLevelFromDrawable(Drawable drawable) {
        this.mLevel = drawable.getLevel();
    }

    public final void setImageResource(int i) {
        ImageView imageView = this.mView;
        if (i != 0) {
            Drawable drawable = AppCompatResources.getDrawable(imageView.getContext(), i);
            if (drawable != null) {
                Rect rect = DrawableUtils.INSETS_NONE;
            }
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setImageDrawable(null);
        }
        applySupportImageTint();
    }

    final void setSupportImageTintList(ColorStateList colorStateList) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        TintInfo tintInfo = this.mImageTint;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = true;
        applySupportImageTint();
    }

    final void setSupportImageTintMode(PorterDuff.Mode mode) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        TintInfo tintInfo = this.mImageTint;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = true;
        applySupportImageTint();
    }
}
