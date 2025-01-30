package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.R$styleable;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatBackgroundHelper {
    public TintInfo mBackgroundTint;
    public TintInfo mInternalBackgroundTint;
    public TintInfo mTmpInfo;
    public final View mView;
    public int mBackgroundResId = -1;
    public final AppCompatDrawableManager mDrawableManager = AppCompatDrawableManager.get();

    public AppCompatBackgroundHelper(View view) {
        this.mView = view;
    }

    public final void applySupportBackgroundTint() {
        View view = this.mView;
        Drawable background = view.getBackground();
        if (background != null) {
            boolean z = false;
            if (this.mInternalBackgroundTint != null) {
                if (this.mTmpInfo == null) {
                    this.mTmpInfo = new TintInfo();
                }
                TintInfo tintInfo = this.mTmpInfo;
                tintInfo.mTintList = null;
                tintInfo.mHasTintList = false;
                tintInfo.mTintMode = null;
                tintInfo.mHasTintMode = false;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ColorStateList backgroundTintList = ViewCompat.Api21Impl.getBackgroundTintList(view);
                if (backgroundTintList != null) {
                    tintInfo.mHasTintList = true;
                    tintInfo.mTintList = backgroundTintList;
                }
                PorterDuff.Mode backgroundTintMode = ViewCompat.Api21Impl.getBackgroundTintMode(view);
                if (backgroundTintMode != null) {
                    tintInfo.mHasTintMode = true;
                    tintInfo.mTintMode = backgroundTintMode;
                }
                if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                    AppCompatDrawableManager.tintDrawable(background, tintInfo, view.getDrawableState());
                    z = true;
                }
                if (z) {
                    return;
                }
            }
            TintInfo tintInfo2 = this.mBackgroundTint;
            if (tintInfo2 != null) {
                AppCompatDrawableManager.tintDrawable(background, tintInfo2, view.getDrawableState());
                return;
            }
            TintInfo tintInfo3 = this.mInternalBackgroundTint;
            if (tintInfo3 != null) {
                AppCompatDrawableManager.tintDrawable(background, tintInfo3, view.getDrawableState());
            }
        }
    }

    public final ColorStateList getSupportBackgroundTintList() {
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    public final PorterDuff.Mode getSupportBackgroundTintMode() {
        TintInfo tintInfo = this.mBackgroundTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        View view = this.mView;
        Context context = view.getContext();
        int[] iArr = R$styleable.ViewBackgroundHelper;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        View view2 = this.mView;
        Context context2 = view2.getContext();
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(view2, context2, iArr, attributeSet, typedArray, i, 0);
        try {
            if (obtainStyledAttributes.hasValue(0)) {
                this.mBackgroundResId = obtainStyledAttributes.getResourceId(0, -1);
                AppCompatDrawableManager appCompatDrawableManager = this.mDrawableManager;
                Context context3 = view.getContext();
                int i2 = this.mBackgroundResId;
                synchronized (appCompatDrawableManager) {
                    appCompatDrawableManager.mResourceManager.getTintList(i2, context3);
                }
            }
            if (obtainStyledAttributes.hasValue(1)) {
                ViewCompat.Api21Impl.setBackgroundTintList(view, obtainStyledAttributes.getColorStateList(1));
            }
            if (obtainStyledAttributes.hasValue(2)) {
                ViewCompat.Api21Impl.setBackgroundTintMode(view, DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(2, -1), null));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public final void onSetBackgroundDrawable() {
        this.mBackgroundResId = -1;
        setInternalBackgroundTint(null);
        applySupportBackgroundTint();
    }

    public final void onSetBackgroundResource(int i) {
        this.mBackgroundResId = i;
        AppCompatDrawableManager appCompatDrawableManager = this.mDrawableManager;
        if (appCompatDrawableManager != null) {
            Context context = this.mView.getContext();
            synchronized (appCompatDrawableManager) {
                appCompatDrawableManager.mResourceManager.getTintList(i, context);
            }
        }
        setInternalBackgroundTint(null);
        applySupportBackgroundTint();
    }

    public final void setInternalBackgroundTint(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            TintInfo tintInfo = this.mInternalBackgroundTint;
            tintInfo.mTintList = colorStateList;
            tintInfo.mHasTintList = true;
        } else {
            this.mInternalBackgroundTint = null;
        }
        applySupportBackgroundTint();
    }

    public final void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        TintInfo tintInfo = this.mBackgroundTint;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = true;
        applySupportBackgroundTint();
    }

    public final void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        TintInfo tintInfo = this.mBackgroundTint;
        tintInfo.mTintMode = mode;
        tintInfo.mHasTintMode = true;
        applySupportBackgroundTint();
    }
}
