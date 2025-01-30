package com.google.android.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCompoundButtonHelper;
import androidx.appcompat.widget.TintTypedArray;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MaterialCheckBox extends AppCompatCheckBox {
    public boolean broadcasting;
    public Drawable buttonDrawable;
    public Drawable buttonIconDrawable;
    public final ColorStateList buttonIconTintList;
    public final PorterDuff.Mode buttonIconTintMode;
    public ColorStateList buttonTintList;
    public final boolean centerIfNoTextEnabled;
    public int checkedState;
    public int[] currentStateChecked;
    public CharSequence customStateDescription;
    public final CharSequence errorAccessibilityLabel;
    public final boolean errorShown;
    public ColorStateList materialThemeColorsTintList;
    public CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    public final LinkedHashSet onCheckedStateChangedListeners;
    public final AnimatedVectorDrawableCompat transitionToUnchecked;
    public final C42441 transitionToUncheckedCallback;
    public boolean useMaterialThemeColors;
    public boolean usingMaterialButtonDrawable;
    public static final int[] INDETERMINATE_STATE_SET = {R.attr.state_indeterminate};
    public static final int[] ERROR_STATE_SET = {R.attr.state_error};
    public static final int[][] CHECKBOX_STATES = {new int[]{android.R.attr.state_enabled, R.attr.state_error}, new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}, new int[]{android.R.attr.state_enabled, -16842912}, new int[]{-16842910, android.R.attr.state_checked}, new int[]{-16842910, -16842912}};
    public static final int FRAMEWORK_BUTTON_DRAWABLE_RES_ID = Resources.getSystem().getIdentifier("btn_check_material_anim", "drawable", "android");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: com.google.android.material.checkbox.MaterialCheckBox.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int checkedState;

        public final String toString() {
            StringBuilder sb = new StringBuilder("MaterialCheckBox.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" CheckedState=");
            int i = this.checkedState;
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, i != 1 ? i != 2 ? "unchecked" : "indeterminate" : "checked", "}");
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Integer.valueOf(this.checkedState));
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.checkedState = ((Integer) parcel.readValue(SavedState.class.getClassLoader())).intValue();
        }
    }

    public MaterialCheckBox(Context context) {
        this(context, null);
    }

    private void setDefaultStateDescription() {
        if (this.customStateDescription == null) {
            int i = this.checkedState;
            super.setStateDescription(i == 1 ? getResources().getString(R.string.mtrl_checkbox_state_description_checked) : i == 0 ? getResources().getString(R.string.mtrl_checkbox_state_description_unchecked) : getResources().getString(R.string.mtrl_checkbox_state_description_indeterminate));
        }
    }

    @Override // android.widget.CompoundButton
    public final Drawable getButtonDrawable() {
        return this.buttonDrawable;
    }

    @Override // android.widget.CompoundButton
    public final ColorStateList getButtonTintList() {
        return this.buttonTintList;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final boolean isChecked() {
        return this.checkedState == 1;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && this.buttonTintList == null && this.buttonIconTintList == null) {
            this.useMaterialThemeColors = true;
            if (this.materialThemeColorsTintList == null) {
                int color = MaterialColors.getColor(this, R.attr.colorControlActivated);
                int color2 = MaterialColors.getColor(this, R.attr.colorError);
                int color3 = MaterialColors.getColor(this, R.attr.colorSurface);
                int color4 = MaterialColors.getColor(this, R.attr.colorOnSurface);
                this.materialThemeColorsTintList = new ColorStateList(CHECKBOX_STATES, new int[]{MaterialColors.layer(1.0f, color3, color2), MaterialColors.layer(1.0f, color3, color), MaterialColors.layer(0.54f, color3, color4), MaterialColors.layer(0.38f, color3, color4), MaterialColors.layer(0.38f, color3, color4)});
            }
            setButtonTintList(this.materialThemeColorsTintList);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (this.checkedState == 2) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, INDETERMINATE_STATE_SET);
        }
        if (this.errorShown) {
            CheckBox.mergeDrawableStates(onCreateDrawableState, ERROR_STATE_SET);
        }
        this.currentStateChecked = DrawableUtils.getCheckedState(onCreateDrawableState);
        return onCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        Drawable buttonDrawable;
        if (!this.centerIfNoTextEnabled || !TextUtils.isEmpty(getText()) || (buttonDrawable = getButtonDrawable()) == null) {
            super.onDraw(canvas);
            return;
        }
        int width = ((getWidth() - buttonDrawable.getIntrinsicWidth()) / 2) * (ViewUtils.isLayoutRtl(this) ? -1 : 1);
        int save = canvas.save();
        canvas.translate(width, 0.0f);
        super.onDraw(canvas);
        canvas.restoreToCount(save);
        if (getBackground() != null) {
            Rect bounds = buttonDrawable.getBounds();
            getBackground().setHotspotBounds(bounds.left + width, bounds.top, bounds.right + width, bounds.bottom);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (accessibilityNodeInfo != null && this.errorShown) {
            accessibilityNodeInfo.setText(((Object) accessibilityNodeInfo.getText()) + ", " + ((Object) this.errorAccessibilityLabel));
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCheckedState(savedState.checkedState);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checkedState = this.checkedState;
        return savedState;
    }

    public final void refreshButtonDrawable() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat;
        AnimatedVectorDrawableCompat.C05582 c05582;
        this.buttonDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.buttonDrawable, this.buttonTintList, getButtonTintMode());
        this.buttonIconDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.buttonIconDrawable, this.buttonIconTintList, this.buttonIconTintMode);
        if (this.usingMaterialButtonDrawable) {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat2 = this.transitionToUnchecked;
            if (animatedVectorDrawableCompat2 != null) {
                C42441 c42441 = this.transitionToUncheckedCallback;
                Drawable drawable = animatedVectorDrawableCompat2.mDelegateDrawable;
                if (drawable != null) {
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                    if (c42441.mPlatformCallback == null) {
                        c42441.mPlatformCallback = new Animatable2Compat.AnimationCallback.C05561();
                    }
                    animatedVectorDrawable.unregisterAnimationCallback(c42441.mPlatformCallback);
                }
                ArrayList arrayList = animatedVectorDrawableCompat2.mAnimationCallbacks;
                if (arrayList != null && c42441 != null) {
                    arrayList.remove(c42441);
                    if (animatedVectorDrawableCompat2.mAnimationCallbacks.size() == 0 && (c05582 = animatedVectorDrawableCompat2.mAnimatorListener) != null) {
                        animatedVectorDrawableCompat2.mAnimatedVectorState.mAnimatorSet.removeListener(c05582);
                        animatedVectorDrawableCompat2.mAnimatorListener = null;
                    }
                }
                this.transitionToUnchecked.registerAnimationCallback(this.transitionToUncheckedCallback);
            }
            Drawable drawable2 = this.buttonDrawable;
            if ((drawable2 instanceof AnimatedStateListDrawable) && (animatedVectorDrawableCompat = this.transitionToUnchecked) != null) {
                ((AnimatedStateListDrawable) drawable2).addTransition(R.id.checked, R.id.unchecked, animatedVectorDrawableCompat, false);
                ((AnimatedStateListDrawable) this.buttonDrawable).addTransition(R.id.indeterminate, R.id.unchecked, this.transitionToUnchecked, false);
            }
        }
        Drawable drawable3 = this.buttonDrawable;
        if (drawable3 != null && (colorStateList2 = this.buttonTintList) != null) {
            drawable3.setTintList(colorStateList2);
        }
        Drawable drawable4 = this.buttonIconDrawable;
        if (drawable4 != null && (colorStateList = this.buttonIconTintList) != null) {
            drawable4.setTintList(colorStateList);
        }
        super.setButtonDrawable(DrawableUtils.compositeTwoLayeredDrawable(this.buttonDrawable, this.buttonIconDrawable));
        refreshDrawableState();
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton
    public final void setButtonDrawable(int i) {
        setButtonDrawable(AppCompatResources.getDrawable(i, getContext()));
    }

    @Override // android.widget.CompoundButton
    public final void setButtonTintList(ColorStateList colorStateList) {
        if (this.buttonTintList == colorStateList) {
            return;
        }
        this.buttonTintList = colorStateList;
        refreshButtonDrawable();
    }

    @Override // android.widget.CompoundButton
    public final void setButtonTintMode(PorterDuff.Mode mode) {
        AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
        if (appCompatCompoundButtonHelper != null) {
            appCompatCompoundButtonHelper.mButtonTintMode = mode;
            appCompatCompoundButtonHelper.mHasButtonTintMode = true;
            appCompatCompoundButtonHelper.applyButtonTint();
        }
        refreshButtonDrawable();
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void setChecked(boolean z) {
        setCheckedState(z ? 1 : 0);
    }

    public final void setCheckedState(int i) {
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
        if (this.checkedState != i) {
            this.checkedState = i;
            super.setChecked(i == 1);
            refreshDrawableState();
            setDefaultStateDescription();
            if (this.broadcasting) {
                return;
            }
            this.broadcasting = true;
            LinkedHashSet linkedHashSet = this.onCheckedStateChangedListeners;
            if (linkedHashSet != null) {
                Iterator it = linkedHashSet.iterator();
                if (it.hasNext()) {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it.next());
                    throw null;
                }
            }
            if (this.checkedState != 2 && (onCheckedChangeListener = this.onCheckedChangeListener) != null) {
                onCheckedChangeListener.onCheckedChanged(this, isChecked());
            }
            AutofillManager autofillManager = (AutofillManager) getContext().getSystemService(AutofillManager.class);
            if (autofillManager != null) {
                autofillManager.notifyValueChanged(this);
            }
            this.broadcasting = false;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
    }

    @Override // android.widget.CompoundButton
    public final void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // android.widget.CompoundButton, android.view.View
    public final void setStateDescription(CharSequence charSequence) {
        this.customStateDescription = charSequence;
        if (charSequence == null) {
            setDefaultStateDescription();
        } else {
            super.setStateDescription(charSequence);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void toggle() {
        setChecked(!isChecked());
    }

    public MaterialCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkboxStyle);
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton
    public final void setButtonDrawable(Drawable drawable) {
        this.buttonDrawable = drawable;
        this.usingMaterialButtonDrawable = false;
        refreshButtonDrawable();
    }

    /* JADX WARN: Type inference failed for: r10v6, types: [com.google.android.material.checkbox.MaterialCheckBox$1] */
    public MaterialCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019108), attributeSet, i);
        new LinkedHashSet();
        this.onCheckedStateChangedListeners = new LinkedHashSet();
        this.transitionToUnchecked = AnimatedVectorDrawableCompat.create(getContext());
        this.transitionToUncheckedCallback = new Animatable2Compat.AnimationCallback() { // from class: com.google.android.material.checkbox.MaterialCheckBox.1
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                ColorStateList colorStateList = MaterialCheckBox.this.buttonTintList;
                if (colorStateList != null) {
                    drawable.setTintList(colorStateList);
                }
            }

            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationStart(Drawable drawable) {
                MaterialCheckBox materialCheckBox = MaterialCheckBox.this;
                ColorStateList colorStateList = materialCheckBox.buttonTintList;
                if (colorStateList != null) {
                    drawable.setTint(colorStateList.getColorForState(materialCheckBox.currentStateChecked, colorStateList.getDefaultColor()));
                }
            }
        };
        Context context2 = getContext();
        this.buttonDrawable = getButtonDrawable();
        ColorStateList colorStateList = this.buttonTintList;
        if (colorStateList == null) {
            if (super.getButtonTintList() != null) {
                colorStateList = super.getButtonTintList();
            } else {
                AppCompatCompoundButtonHelper appCompatCompoundButtonHelper = this.mCompoundButtonHelper;
                colorStateList = appCompatCompoundButtonHelper != null ? appCompatCompoundButtonHelper.mButtonTintList : null;
            }
        }
        this.buttonTintList = colorStateList;
        AppCompatCompoundButtonHelper appCompatCompoundButtonHelper2 = this.mCompoundButtonHelper;
        if (appCompatCompoundButtonHelper2 != null) {
            appCompatCompoundButtonHelper2.mButtonTintList = null;
            appCompatCompoundButtonHelper2.mHasButtonTint = true;
            appCompatCompoundButtonHelper2.applyButtonTint();
        }
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R$styleable.MaterialCheckBox, i, 2132019108, new int[0]);
        this.buttonIconDrawable = obtainTintedStyledAttributes.getDrawable(2);
        if (this.buttonDrawable != null && MaterialAttributes.resolveBoolean(context2, R.attr.isMaterial3Theme, false)) {
            if (obtainTintedStyledAttributes.getResourceId(0, 0) == FRAMEWORK_BUTTON_DRAWABLE_RES_ID && obtainTintedStyledAttributes.getResourceId(1, 0) == 0) {
                super.setButtonDrawable((Drawable) null);
                this.buttonDrawable = AppCompatResources.getDrawable(R.drawable.mtrl_checkbox_button, context2);
                this.usingMaterialButtonDrawable = true;
                if (this.buttonIconDrawable == null) {
                    this.buttonIconDrawable = AppCompatResources.getDrawable(R.drawable.mtrl_checkbox_button_icon, context2);
                }
            }
        }
        this.buttonIconTintList = MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 3);
        this.buttonIconTintMode = ViewUtils.parseTintMode(obtainTintedStyledAttributes.getInt(4, -1), PorterDuff.Mode.SRC_IN);
        this.useMaterialThemeColors = obtainTintedStyledAttributes.getBoolean(10, false);
        this.centerIfNoTextEnabled = obtainTintedStyledAttributes.getBoolean(6, true);
        this.errorShown = obtainTintedStyledAttributes.getBoolean(9, false);
        this.errorAccessibilityLabel = obtainTintedStyledAttributes.getText(8);
        if (obtainTintedStyledAttributes.hasValue(7)) {
            setCheckedState(obtainTintedStyledAttributes.getInt(7, 0));
        }
        obtainTintedStyledAttributes.recycle();
        refreshButtonDrawable();
    }
}
