package androidx.appcompat.widget;

import android.content.Context;
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

/* loaded from: classes.dex */
public class AppCompatCompoundButtonHelper {
    public PorterDuff.Mode mButtonTintMode = null;
    public boolean mHasButtonTint = false;
    public boolean mHasButtonTintMode = false;
    public boolean mSkipNextApply;
    public final CompoundButton mView;

    public AppCompatCompoundButtonHelper(CompoundButton compoundButton) {
        this.mView = compoundButton;
    }

    public final void applyButtonTint() {
        Drawable buttonDrawable = this.mView.getButtonDrawable();
        if (buttonDrawable != null) {
            if (this.mHasButtonTint || this.mHasButtonTintMode) {
                Drawable drawableMutate = buttonDrawable.mutate();
                if (this.mHasButtonTint) {
                    drawableMutate.setTintList(null);
                }
                if (this.mHasButtonTintMode) {
                    drawableMutate.setTintMode(this.mButtonTintMode);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(this.mView.getDrawableState());
                }
                this.mView.setButtonDrawable(drawableMutate);
            }
        }
    }

    public final void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        int resourceId2;
        Context context = this.mView.getContext();
        int[] iArr = R$styleable.CompoundButton;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        CompoundButton compoundButton = this.mView;
        Context context2 = compoundButton.getContext();
        TypedArray typedArray = tintTypedArrayObtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(compoundButton, context2, iArr, attributeSet, typedArray, i, 0);
        try {
            if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(1) && (resourceId2 = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(1, 0)) != 0) {
                try {
                    CompoundButton compoundButton2 = this.mView;
                    compoundButton2.setButtonDrawable(AppCompatResources.getDrawable(resourceId2, compoundButton2.getContext()));
                } catch (Resources.NotFoundException unused) {
                }
            } else if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(0) && (resourceId = tintTypedArrayObtainStyledAttributes.mWrapped.getResourceId(0, 0)) != 0) {
                CompoundButton compoundButton3 = this.mView;
                compoundButton3.setButtonDrawable(AppCompatResources.getDrawable(resourceId, compoundButton3.getContext()));
            }
            if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(2)) {
                this.mView.setButtonTintList(tintTypedArrayObtainStyledAttributes.getColorStateList(2));
            }
            if (tintTypedArrayObtainStyledAttributes.mWrapped.hasValue(3)) {
                this.mView.setButtonTintMode(DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.mWrapped.getInt(3, -1), null));
            }
            tintTypedArrayObtainStyledAttributes.recycle();
        } catch (Throwable th) {
            tintTypedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }
}
