package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
final class AppCompatCheckedTextViewHelper {
    private ColorStateList mCheckMarkTintList = null;
    private PorterDuff.Mode mCheckMarkTintMode = null;
    private boolean mHasCheckMarkTint = false;
    private boolean mHasCheckMarkTintMode = false;
    private boolean mSkipNextApply;
    private final CheckedTextView mView;

    AppCompatCheckedTextViewHelper(CheckedTextView checkedTextView) {
        this.mView = checkedTextView;
    }

    final void applyCheckMarkTint() {
        CheckedTextView checkedTextView = this.mView;
        Drawable checkMarkDrawable = checkedTextView.getCheckMarkDrawable();
        if (checkMarkDrawable != null) {
            if (this.mHasCheckMarkTint || this.mHasCheckMarkTintMode) {
                Drawable mutate = checkMarkDrawable.mutate();
                if (this.mHasCheckMarkTint) {
                    mutate.setTintList(this.mCheckMarkTintList);
                }
                if (this.mHasCheckMarkTintMode) {
                    mutate.setTintMode(this.mCheckMarkTintMode);
                }
                if (mutate.isStateful()) {
                    mutate.setState(checkedTextView.getDrawableState());
                }
                checkedTextView.setCheckMarkDrawable(mutate);
            }
        }
    }

    final ColorStateList getSupportCheckMarkTintList() {
        return this.mCheckMarkTintList;
    }

    final PorterDuff.Mode getSupportCheckMarkTintMode() {
        return this.mCheckMarkTintMode;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0057 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:3:0x001e, B:5:0x0024, B:8:0x002a, B:10:0x0039, B:12:0x003f, B:14:0x0045, B:15:0x0050, B:17:0x0057, B:18:0x005e, B:20:0x0065), top: B:2:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0065 A[Catch: all -> 0x0076, TRY_LEAVE, TryCatch #1 {all -> 0x0076, blocks: (B:3:0x001e, B:5:0x0024, B:8:0x002a, B:10:0x0039, B:12:0x003f, B:14:0x0045, B:15:0x0050, B:17:0x0057, B:18:0x005e, B:20:0x0065), top: B:2:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        int resourceId2;
        CheckedTextView checkedTextView = this.mView;
        Context context = checkedTextView.getContext();
        int[] iArr = R$styleable.CheckedTextView;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        CheckedTextView checkedTextView2 = this.mView;
        ViewCompat.saveAttributeDataForStyleable(checkedTextView2, checkedTextView2.getContext(), iArr, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        boolean z = true;
        try {
            if (obtainStyledAttributes.hasValue(1) && (resourceId2 = obtainStyledAttributes.getResourceId(1, 0)) != 0) {
                try {
                    checkedTextView.setCheckMarkDrawable(AppCompatResources.getDrawable(checkedTextView.getContext(), resourceId2));
                } catch (Resources.NotFoundException unused) {
                }
                if (!z && obtainStyledAttributes.hasValue(0) && (resourceId = obtainStyledAttributes.getResourceId(0, 0)) != 0) {
                    checkedTextView.setCheckMarkDrawable(AppCompatResources.getDrawable(checkedTextView.getContext(), resourceId));
                }
                if (obtainStyledAttributes.hasValue(2)) {
                    checkedTextView.setCheckMarkTintList(obtainStyledAttributes.getColorStateList(2));
                }
                if (obtainStyledAttributes.hasValue(3)) {
                    checkedTextView.setCheckMarkTintMode(DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(3, -1), null));
                }
            }
            z = false;
            if (!z) {
                checkedTextView.setCheckMarkDrawable(AppCompatResources.getDrawable(checkedTextView.getContext(), resourceId));
            }
            if (obtainStyledAttributes.hasValue(2)) {
            }
            if (obtainStyledAttributes.hasValue(3)) {
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    final void onSetCheckMarkDrawable() {
        if (this.mSkipNextApply) {
            this.mSkipNextApply = false;
        } else {
            this.mSkipNextApply = true;
            applyCheckMarkTint();
        }
    }

    final void setSupportCheckMarkTintList(ColorStateList colorStateList) {
        this.mCheckMarkTintList = colorStateList;
        this.mHasCheckMarkTint = true;
        applyCheckMarkTint();
    }

    final void setSupportCheckMarkTintMode(PorterDuff.Mode mode) {
        this.mCheckMarkTintMode = mode;
        this.mHasCheckMarkTintMode = true;
        applyCheckMarkTint();
    }
}
