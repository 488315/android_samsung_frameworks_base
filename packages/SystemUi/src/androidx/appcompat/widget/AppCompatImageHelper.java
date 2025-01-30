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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatImageHelper {
    public int mLevel = 0;
    public final ImageView mView;

    public AppCompatImageHelper(ImageView imageView) {
        this.mView = imageView;
    }

    public final void applySupportImageTint() {
        if (this.mView.getDrawable() != null) {
            Rect rect = DrawableUtils.INSETS_NONE;
        }
    }

    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        ImageView imageView = this.mView;
        Context context = imageView.getContext();
        int[] iArr = R$styleable.AppCompatImageView;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        ImageView imageView2 = this.mView;
        Context context2 = imageView2.getContext();
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(imageView2, context2, iArr, attributeSet, typedArray, i, 0);
        try {
            Drawable drawable = imageView.getDrawable();
            if (drawable == null && (resourceId = obtainStyledAttributes.getResourceId(1, -1)) != -1 && (drawable = AppCompatResources.getDrawable(resourceId, imageView.getContext())) != null) {
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

    public final void setImageResource(int i) {
        ImageView imageView = this.mView;
        if (i != 0) {
            Drawable drawable = AppCompatResources.getDrawable(i, imageView.getContext());
            if (drawable != null) {
                Rect rect = DrawableUtils.INSETS_NONE;
            }
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setImageDrawable(null);
        }
        applySupportImageTint();
    }
}
