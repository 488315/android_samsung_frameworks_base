package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.appcompat.R$styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatCompoundButtonHelper {
    public ColorStateList mButtonTintList = null;
    public PorterDuff.Mode mButtonTintMode = null;
    public boolean mHasButtonTint = false;
    public boolean mHasButtonTintMode = false;
    public boolean mSkipNextApply;
    public final CompoundButton mView;

    public AppCompatCompoundButtonHelper(CompoundButton compoundButton) {
        this.mView = compoundButton;
    }

    public final void applyButtonTint() {
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0057 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:3:0x001e, B:5:0x0024, B:8:0x002a, B:10:0x0039, B:12:0x003f, B:14:0x0045, B:15:0x0050, B:17:0x0057, B:18:0x005e, B:20:0x0065), top: B:2:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0065 A[Catch: all -> 0x0076, TRY_LEAVE, TryCatch #1 {all -> 0x0076, blocks: (B:3:0x001e, B:5:0x0024, B:8:0x002a, B:10:0x0039, B:12:0x003f, B:14:0x0045, B:15:0x0050, B:17:0x0057, B:18:0x005e, B:20:0x0065), top: B:2:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        int resourceId2;
        CompoundButton compoundButton = this.mView;
        Context context = compoundButton.getContext();
        int[] iArr = R$styleable.CompoundButton;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        CompoundButton compoundButton2 = this.mView;
        Context context2 = compoundButton2.getContext();
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(compoundButton2, context2, iArr, attributeSet, typedArray, i, 0);
        boolean z = true;
        try {
            if (obtainStyledAttributes.hasValue(1) && (resourceId2 = obtainStyledAttributes.getResourceId(1, 0)) != 0) {
                try {
                    compoundButton.setButtonDrawable(AppCompatResources.getDrawable(resourceId2, compoundButton.getContext()));
                } catch (Resources.NotFoundException unused) {
                }
                if (!z && obtainStyledAttributes.hasValue(0) && (resourceId = obtainStyledAttributes.getResourceId(0, 0)) != 0) {
                    compoundButton.setButtonDrawable(AppCompatResources.getDrawable(resourceId, compoundButton.getContext()));
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
                compoundButton.setButtonDrawable(AppCompatResources.getDrawable(resourceId, compoundButton.getContext()));
            }
            if (obtainStyledAttributes.hasValue(2)) {
            }
            if (obtainStyledAttributes.hasValue(3)) {
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
