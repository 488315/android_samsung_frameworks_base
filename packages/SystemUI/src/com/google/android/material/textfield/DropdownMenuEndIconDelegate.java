package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DropdownMenuEndIconDelegate extends EndIconDelegate {
    public AccessibilityManager accessibilityManager;
    public final int animationFadeInDuration;
    public final TimeInterpolator animationFadeInterpolator;
    public final int animationFadeOutDuration;
    public AutoCompleteTextView autoCompleteTextView;
    public long dropdownPopupActivatedAt;
    public boolean dropdownPopupDirty;
    public boolean editTextHasFocus;
    public ValueAnimator fadeInAnim;
    public ValueAnimator fadeOutAnim;
    public boolean isEndIconChecked;
    public final DropdownMenuEndIconDelegate$$ExternalSyntheticLambda4 onEditTextFocusChangeListener;
    public final DropdownMenuEndIconDelegate$$ExternalSyntheticLambda3 onIconClickListener;
    public final DropdownMenuEndIconDelegate$$ExternalSyntheticLambda5 touchExplorationStateChangeListener;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda5] */
    public DropdownMenuEndIconDelegate(EndCompoundLayout endCompoundLayout) {
        super(endCompoundLayout);
        this.onIconClickListener = new View.OnClickListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DropdownMenuEndIconDelegate.this.showHideDropdown();
            }
        };
        this.onEditTextFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda4
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = DropdownMenuEndIconDelegate.this;
                dropdownMenuEndIconDelegate.editTextHasFocus = z;
                dropdownMenuEndIconDelegate.refreshIconState();
                if (z) {
                    return;
                }
                dropdownMenuEndIconDelegate.setEndIconChecked(false);
                dropdownMenuEndIconDelegate.dropdownPopupDirty = false;
            }
        };
        this.touchExplorationStateChangeListener = new AccessibilityManagerCompat$TouchExplorationStateChangeListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda5
            @Override // androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = DropdownMenuEndIconDelegate.this;
                AutoCompleteTextView autoCompleteTextView = dropdownMenuEndIconDelegate.autoCompleteTextView;
                if (autoCompleteTextView == null || EditTextUtils.isEditable(autoCompleteTextView)) {
                    return;
                }
                int i = z ? 2 : 1;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                dropdownMenuEndIconDelegate.endIconView.setImportantForAccessibility(i);
            }
        };
        this.dropdownPopupActivatedAt = Long.MAX_VALUE;
        this.animationFadeInDuration = MotionUtils.resolveThemeDuration(endCompoundLayout.getContext(), R.attr.motionDurationShort3, 67);
        this.animationFadeOutDuration = MotionUtils.resolveThemeDuration(endCompoundLayout.getContext(), R.attr.motionDurationShort3, 50);
        this.animationFadeInterpolator = MotionUtils.resolveThemeInterpolator(endCompoundLayout.getContext(), R.attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void afterEditTextChanged() {
        if (this.accessibilityManager.isTouchExplorationEnabled() && EditTextUtils.isEditable(this.autoCompleteTextView) && !this.endIconView.hasFocus()) {
            this.autoCompleteTextView.dismissDropDown();
        }
        this.autoCompleteTextView.post(new Runnable() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = DropdownMenuEndIconDelegate.this;
                boolean isPopupShowing = dropdownMenuEndIconDelegate.autoCompleteTextView.isPopupShowing();
                dropdownMenuEndIconDelegate.setEndIconChecked(isPopupShowing);
                dropdownMenuEndIconDelegate.dropdownPopupDirty = isPopupShowing;
            }
        });
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final int getIconContentDescriptionResId() {
        return R.string.exposed_dropdown_menu_content_description;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final int getIconDrawableResId() {
        return R.drawable.mtrl_dropdown_arrow;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final View.OnFocusChangeListener getOnEditTextFocusChangeListener() {
        return this.onEditTextFocusChangeListener;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final View.OnClickListener getOnIconClickListener() {
        return this.onIconClickListener;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final AccessibilityManagerCompat$TouchExplorationStateChangeListener getTouchExplorationStateChangeListener() {
        return this.touchExplorationStateChangeListener;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final boolean isBoxBackgroundModeSupported(int i) {
        return i != 0;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final boolean isIconActivated() {
        return this.editTextHasFocus;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final boolean isIconChecked() {
        return this.isEndIconChecked;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void onEditTextAttached(EditText editText) {
        if (!(editText instanceof AutoCompleteTextView)) {
            throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
        }
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText;
        this.autoCompleteTextView = autoCompleteTextView;
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = DropdownMenuEndIconDelegate.this;
                dropdownMenuEndIconDelegate.getClass();
                if (motionEvent.getAction() == 1) {
                    long currentTimeMillis = System.currentTimeMillis() - dropdownMenuEndIconDelegate.dropdownPopupActivatedAt;
                    if (currentTimeMillis < 0 || currentTimeMillis > 300) {
                        dropdownMenuEndIconDelegate.dropdownPopupDirty = false;
                    }
                    dropdownMenuEndIconDelegate.showHideDropdown();
                    dropdownMenuEndIconDelegate.dropdownPopupDirty = true;
                    dropdownMenuEndIconDelegate.dropdownPopupActivatedAt = System.currentTimeMillis();
                }
                return false;
            }
        });
        this.autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda2
            @Override // android.widget.AutoCompleteTextView.OnDismissListener
            public final void onDismiss() {
                DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = DropdownMenuEndIconDelegate.this;
                dropdownMenuEndIconDelegate.dropdownPopupDirty = true;
                dropdownMenuEndIconDelegate.dropdownPopupActivatedAt = System.currentTimeMillis();
                dropdownMenuEndIconDelegate.setEndIconChecked(false);
            }
        });
        this.autoCompleteTextView.setThreshold(0);
        TextInputLayout textInputLayout = this.textInputLayout;
        EndCompoundLayout endCompoundLayout = textInputLayout.endLayout;
        endCompoundLayout.errorIconView.setImageDrawable(null);
        endCompoundLayout.updateErrorIconVisibility();
        IconHelper.applyIconTint(endCompoundLayout.textInputLayout, endCompoundLayout.errorIconView, endCompoundLayout.errorIconTintList, endCompoundLayout.errorIconTintMode);
        if (!EditTextUtils.isEditable(editText) && this.accessibilityManager.isTouchExplorationEnabled()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.endIconView.setImportantForAccessibility(2);
        }
        textInputLayout.endLayout.setEndIconVisible(true);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        if (!EditTextUtils.isEditable(this.autoCompleteTextView)) {
            accessibilityNodeInfoCompat.setClassName(Spinner.class.getName());
        }
        if (accessibilityNodeInfoCompat.mInfo.isShowingHintText()) {
            accessibilityNodeInfoCompat.mInfo.setHintText(null);
        }
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (!this.accessibilityManager.isEnabled() || EditTextUtils.isEditable(this.autoCompleteTextView)) {
            return;
        }
        boolean z = (accessibilityEvent.getEventType() == 32768 || accessibilityEvent.getEventType() == 8) && this.isEndIconChecked && !this.autoCompleteTextView.isPopupShowing();
        if (accessibilityEvent.getEventType() == 1 || z) {
            showHideDropdown();
            this.dropdownPopupDirty = true;
            this.dropdownPopupActivatedAt = System.currentTimeMillis();
        }
    }

    public final void setEndIconChecked(boolean z) {
        if (this.isEndIconChecked != z) {
            this.isEndIconChecked = z;
            this.fadeInAnim.cancel();
            this.fadeOutAnim.start();
        }
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void setUp() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(this.animationFadeInterpolator);
        ofFloat.setDuration(this.animationFadeInDuration);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = DropdownMenuEndIconDelegate.this;
                dropdownMenuEndIconDelegate.getClass();
                dropdownMenuEndIconDelegate.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.fadeInAnim = ofFloat;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat2.setInterpolator(this.animationFadeInterpolator);
        ofFloat2.setDuration(this.animationFadeOutDuration);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = DropdownMenuEndIconDelegate.this;
                dropdownMenuEndIconDelegate.getClass();
                dropdownMenuEndIconDelegate.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.fadeOutAnim = ofFloat2;
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DropdownMenuEndIconDelegate.this.refreshIconState();
                DropdownMenuEndIconDelegate.this.fadeInAnim.start();
            }
        });
        this.accessibilityManager = (AccessibilityManager) this.context.getSystemService("accessibility");
    }

    public final void showHideDropdown() {
        if (this.autoCompleteTextView == null) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis() - this.dropdownPopupActivatedAt;
        if (currentTimeMillis < 0 || currentTimeMillis > 300) {
            this.dropdownPopupDirty = false;
        }
        if (this.dropdownPopupDirty) {
            this.dropdownPopupDirty = false;
            return;
        }
        setEndIconChecked(!this.isEndIconChecked);
        if (!this.isEndIconChecked) {
            this.autoCompleteTextView.dismissDropDown();
        } else {
            this.autoCompleteTextView.requestFocus();
            this.autoCompleteTextView.showDropDown();
        }
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void tearDown() {
        AutoCompleteTextView autoCompleteTextView = this.autoCompleteTextView;
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setOnTouchListener(null);
            this.autoCompleteTextView.setOnDismissListener(null);
        }
    }
}
