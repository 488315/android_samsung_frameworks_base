package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.appcompat.R$styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
final class AppCompatCompoundButtonHelper {
    private ColorStateList mButtonTintList = null;
    private PorterDuff.Mode mButtonTintMode = null;
    private boolean mHasButtonTint = false;
    private boolean mHasButtonTintMode = false;
    private boolean mSkipNextApply;
    private final CompoundButton mView;

    AppCompatCompoundButtonHelper(CompoundButton compoundButton) {
        this.mView = compoundButton;
    }

    final void applyButtonTint() {
        CompoundButton compoundButton = this.mView;
        Drawable buttonDrawable = compoundButton.getButtonDrawable();
        if (buttonDrawable != null) {
            if (this.mHasButtonTint || this.mHasButtonTintMode) {
                Drawable mutate = buttonDrawable.mutate();
                if (this.mHasButtonTint) {
                    mutate.setTintList(this.mButtonTintList);
                }
                if (this.mHasButtonTintMode) {
                    mutate.setTintMode(this.mButtonTintMode);
                }
                if (mutate.isStateful()) {
                    mutate.setState(compoundButton.getDrawableState());
                }
                compoundButton.setButtonDrawable(mutate);
            }
        }
    }

    final ColorStateList getSupportButtonTintList() {
        return this.mButtonTintList;
    }

    final PorterDuff.Mode getSupportButtonTintMode() {
        return this.mButtonTintMode;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0057 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:3:0x001e, B:5:0x0024, B:8:0x002a, B:10:0x0039, B:12:0x003f, B:14:0x0045, B:15:0x0050, B:17:0x0057, B:18:0x005e, B:20:0x0065), top: B:2:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0065 A[Catch: all -> 0x0076, TRY_LEAVE, TryCatch #1 {all -> 0x0076, blocks: (B:3:0x001e, B:5:0x0024, B:8:0x002a, B:10:0x0039, B:12:0x003f, B:14:0x0045, B:15:0x0050, B:17:0x0057, B:18:0x005e, B:20:0x0065), top: B:2:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        int resourceId2;
        CompoundButton compoundButton = this.mView;
        Context context = compoundButton.getContext();
        int[] iArr = R$styleable.CompoundButton;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        CompoundButton compoundButton2 = this.mView;
        ViewCompat.saveAttributeDataForStyleable(compoundButton2, compoundButton2.getContext(), iArr, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        boolean z = true;
        try {
            if (obtainStyledAttributes.hasValue(1) && (resourceId2 = obtainStyledAttributes.getResourceId(1, 0)) != 0) {
                try {
                    compoundButton.setButtonDrawable(AppCompatResources.getDrawable(compoundButton.getContext(), resourceId2));
                } catch (Resources.NotFoundException unused) {
                }
                if (!z && obtainStyledAttributes.hasValue(0) && (resourceId = obtainStyledAttributes.getResourceId(0, 0)) != 0) {
                    compoundButton.setButtonDrawable(AppCompatResources.getDrawable(compoundButton.getContext(), resourceId));
                }
                if (obtainStyledAttributes.hasValue(2)) {
                    compoundButton.setButtonTintList(obtainStyledAttributes.getColorStateList(2));
                }
                if (obtainStyledAttributes.hasValue(3)) {
                    compoundButton.setButtonTintMode(DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(3, -1), null));
                }
            }
            z = false;
            if (!z) {
                compoundButton.setButtonDrawable(AppCompatResources.getDrawable(compoundButton.getContext(), resourceId));
            }
            if (obtainStyledAttributes.hasValue(2)) {
            }
            if (obtainStyledAttributes.hasValue(3)) {
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    final void onSetButtonDrawable() {
        if (this.mSkipNextApply) {
            this.mSkipNextApply = false;
        } else {
            this.mSkipNextApply = true;
            applyButtonTint();
        }
    }

    final void setSupportButtonTintList(ColorStateList colorStateList) {
        this.mButtonTintList = colorStateList;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    final void setSupportButtonTintMode(PorterDuff.Mode mode) {
        this.mButtonTintMode = mode;
        this.mHasButtonTintMode = true;
        applyButtonTint();
    }
}
