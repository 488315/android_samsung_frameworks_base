package com.android.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.Objects;
import java.util.function.Consumer;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class CachingIconView extends ImageView {
    private int mBackgroundColor;
    private int mDesiredVisibility;
    private boolean mForceHidden;
    private int mIconColor;
    private boolean mInternalSetDrawable;
    private String mLastPackage;
    private int mLastResId;
    private int mMaxDrawableHeight;
    private int mMaxDrawableWidth;
    private Consumer<Boolean> mOnForceHiddenChangedListener;
    private Consumer<Integer> mOnVisibilityChangedListener;
    private boolean mWillBeForceHidden;

    public CachingIconView(Context context) {
        this(context, null, 0, 0);
    }

    public CachingIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public CachingIconView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CachingIconView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxDrawableWidth = -1;
        this.mMaxDrawableHeight = -1;
        init(context, attributeSet, i, i2);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CachingIconView, i, i2);
        this.mMaxDrawableWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1);
        this.mMaxDrawableHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.ImageView
    @RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(Icon icon) {
        if (testAndSetCache(icon)) {
            return;
        }
        this.mInternalSetDrawable = true;
        Drawable drawableLoadSizeRestrictedIcon = loadSizeRestrictedIcon(icon);
        if (drawableLoadSizeRestrictedIcon == null) {
            super.setImageIcon(icon);
        } else {
            super.lambda$setImageURIAsync$2(drawableLoadSizeRestrictedIcon);
        }
        this.mInternalSetDrawable = false;
    }

    Drawable loadSizeRestrictedIcon(Icon icon) {
        return LocalImageResolver.resolveImage(icon, getContext(), this.mMaxDrawableWidth, this.mMaxDrawableHeight);
    }

    @Override // android.widget.ImageView
    public Runnable setImageIconAsync(Icon icon) {
        resetCache();
        final Drawable drawableLoadSizeRestrictedIcon = loadSizeRestrictedIcon(icon);
        if (drawableLoadSizeRestrictedIcon != null) {
            return new Runnable() { // from class: com.android.internal.widget.CachingIconView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setImageIconAsync$0(drawableLoadSizeRestrictedIcon);
                }
            };
        }
        return super.setImageIconAsync(icon);
    }

    @Override // android.widget.ImageView
    @RemotableViewMethod(asyncImpl = "setImageResourceAsync")
    public void setImageResource(int i) {
        if (testAndSetCache(i)) {
            return;
        }
        this.mInternalSetDrawable = true;
        Drawable drawableLoadSizeRestrictedDrawable = loadSizeRestrictedDrawable(i);
        if (drawableLoadSizeRestrictedDrawable == null) {
            super.setImageResource(i);
        } else {
            super.lambda$setImageURIAsync$2(drawableLoadSizeRestrictedDrawable);
        }
        this.mInternalSetDrawable = false;
    }

    private Drawable loadSizeRestrictedDrawable(int i) {
        return LocalImageResolver.resolveImage(i, getContext(), this.mMaxDrawableWidth, this.mMaxDrawableHeight);
    }

    @Override // android.widget.ImageView
    public Runnable setImageResourceAsync(int i) {
        resetCache();
        final Drawable drawableLoadSizeRestrictedDrawable = loadSizeRestrictedDrawable(i);
        if (drawableLoadSizeRestrictedDrawable != null) {
            return new Runnable() { // from class: com.android.internal.widget.CachingIconView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setImageResourceAsync$1(drawableLoadSizeRestrictedDrawable);
                }
            };
        }
        return super.setImageResourceAsync(i);
    }

    @Override // android.widget.ImageView
    @RemotableViewMethod(asyncImpl = "setImageURIAsync")
    public void setImageURI(Uri uri) {
        resetCache();
        Drawable drawableLoadSizeRestrictedUri = loadSizeRestrictedUri(uri);
        if (drawableLoadSizeRestrictedUri == null) {
            super.setImageURI(uri);
            return;
        }
        this.mInternalSetDrawable = true;
        super.lambda$setImageURIAsync$2(drawableLoadSizeRestrictedUri);
        this.mInternalSetDrawable = false;
    }

    private Drawable loadSizeRestrictedUri(Uri uri) {
        return LocalImageResolver.resolveImage(uri, getContext(), this.mMaxDrawableWidth, this.mMaxDrawableHeight);
    }

    @Override // android.widget.ImageView
    public Runnable setImageURIAsync(Uri uri) {
        resetCache();
        final Drawable drawableLoadSizeRestrictedUri = loadSizeRestrictedUri(uri);
        if (drawableLoadSizeRestrictedUri == null) {
            return super.setImageURIAsync(uri);
        }
        return new Runnable() { // from class: com.android.internal.widget.CachingIconView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setImageURIAsync$2(drawableLoadSizeRestrictedUri);
            }
        };
    }

    @Override // android.widget.ImageView, android.inputmethodservice.navigationbar.ButtonInterface
    /* renamed from: setImageDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setImageURIAsync$2(Drawable drawable) {
        if (!this.mInternalSetDrawable) {
            resetCache();
        }
        super.lambda$setImageURIAsync$2(drawable);
    }

    @Override // android.widget.ImageView
    @RemotableViewMethod
    public void setImageBitmap(Bitmap bitmap) {
        resetCache();
        super.setImageBitmap(bitmap);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        resetCache();
    }

    private synchronized boolean testAndSetCache(Icon icon) {
        boolean z = false;
        if (icon != null) {
            if (icon.getType() == 2) {
                String strNormalizeIconPackage = normalizeIconPackage(icon);
                if (this.mLastResId != 0 && icon.getResId() == this.mLastResId && Objects.equals(strNormalizeIconPackage, this.mLastPackage)) {
                    z = true;
                }
                this.mLastPackage = strNormalizeIconPackage;
                this.mLastResId = icon.getResId();
                return z;
            }
        }
        resetCache();
        return false;
    }

    private synchronized boolean testAndSetCache(int i) {
        boolean z;
        z = false;
        if (i != 0) {
            try {
                int i2 = this.mLastResId;
                if (i2 != 0 && i == i2 && this.mLastPackage == null) {
                    z = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mLastPackage = null;
        this.mLastResId = i;
        return z;
    }

    private String normalizeIconPackage(Icon icon) {
        if (icon == null) {
            return null;
        }
        String resPackage = icon.getResPackage();
        if (TextUtils.isEmpty(resPackage) || resPackage.equals(this.mContext.getPackageName())) {
            return null;
        }
        return resPackage;
    }

    private synchronized void resetCache() {
        this.mLastResId = 0;
        this.mLastPackage = null;
    }

    public void setForceHidden(boolean z) {
        if (z != this.mForceHidden) {
            this.mForceHidden = z;
            this.mWillBeForceHidden = false;
            updateVisibility();
            Consumer<Boolean> consumer = this.mOnForceHiddenChangedListener;
            if (consumer != null) {
                consumer.accept(Boolean.valueOf(z));
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    @RemotableViewMethod
    public void setVisibility(int i) {
        this.mDesiredVisibility = i;
        updateVisibility();
    }

    private void updateVisibility() {
        int i = this.mDesiredVisibility;
        if (i == 0 && this.mForceHidden) {
            i = 4;
        }
        Consumer<Integer> consumer = this.mOnVisibilityChangedListener;
        if (consumer != null) {
            consumer.accept(Integer.valueOf(i));
        }
        super.setVisibility(i);
    }

    public void setOnVisibilityChangedListener(Consumer<Integer> consumer) {
        this.mOnVisibilityChangedListener = consumer;
    }

    public void setOnForceHiddenChangedListener(Consumer<Boolean> consumer) {
        this.mOnForceHiddenChangedListener = consumer;
    }

    public boolean isForceHidden() {
        return this.mForceHidden;
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    @RemotableViewMethod
    public void setOriginalIconColor(int i) {
        this.mIconColor = i;
        Drawable background = getBackground();
        Drawable drawable = getDrawable();
        boolean z = i != 1;
        if (background == null && z && drawable != null) {
            drawable.mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @RemotableViewMethod
    public void updateColorizedIconTint(boolean z) {
        if (z) {
            Drawable background = getBackground();
            Drawable drawable = getDrawable();
            if (background != null) {
                background.mutate().setColorFilter(this.mIconColor, PorterDuff.Mode.SRC_ATOP);
            }
            if (drawable != null) {
                drawable.mutate().setColorFilter(this.mBackgroundColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    public void setGrayedOut(boolean z) {
        Drawable background = getBackground();
        if (background == null) {
            background = getDrawable();
        }
        ColoredIconHelper.applyGrayTint(this.mContext, background, z, this.mIconColor);
    }

    public int getOriginalIconColor() {
        return this.mIconColor;
    }

    public boolean willBeForceHidden() {
        return this.mWillBeForceHidden;
    }

    public void setWillBeForceHidden(boolean z) {
        this.mWillBeForceHidden = z;
    }

    public int getMaxDrawableWidth() {
        return this.mMaxDrawableWidth;
    }

    public int getMaxDrawableHeight() {
        return this.mMaxDrawableHeight;
    }

    @RemotableViewMethod
    public void setMaxDrawableWidth(int i) {
        this.mMaxDrawableWidth = i;
    }

    @RemotableViewMethod
    public void setMaxDrawableHeight(int i) {
        this.mMaxDrawableHeight = i;
    }
}
