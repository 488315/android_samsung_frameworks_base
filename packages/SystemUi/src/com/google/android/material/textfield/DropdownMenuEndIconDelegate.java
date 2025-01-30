package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DropdownMenuEndIconDelegate extends EndIconDelegate {
    public AccessibilityManager accessibilityManager;
    public AutoCompleteTextView autoCompleteTextView;
    public long dropdownPopupActivatedAt;
    public boolean dropdownPopupDirty;
    public boolean editTextHasFocus;
    public ValueAnimator fadeInAnim;
    public ValueAnimator fadeOutAnim;
    public boolean isEndIconChecked;
    public final DropdownMenuEndIconDelegate$$ExternalSyntheticLambda1 onEditTextFocusChangeListener;
    public final DropdownMenuEndIconDelegate$$ExternalSyntheticLambda0 onIconClickListener;
    public final DropdownMenuEndIconDelegate$$ExternalSyntheticLambda2 touchExplorationStateChangeListener;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda1] */
    public DropdownMenuEndIconDelegate(EndCompoundLayout endCompoundLayout) {
        super(endCompoundLayout);
        this.onIconClickListener = new View.OnClickListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DropdownMenuEndIconDelegate.this.showHideDropdown();
            }
        };
        this.onEditTextFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda1
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
        this.touchExplorationStateChangeListener = new DropdownMenuEndIconDelegate$$ExternalSyntheticLambda2(this);
        this.dropdownPopupActivatedAt = Long.MAX_VALUE;
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void afterEditTextChanged() {
        if (this.accessibilityManager.isTouchExplorationEnabled()) {
            if ((this.autoCompleteTextView.getInputType() != 0) && !this.endIconView.hasFocus()) {
                this.autoCompleteTextView.dismissDropDown();
            }
        }
        this.autoCompleteTextView.post(new Runnable() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda4
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
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda5
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
        this.autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda6
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
        if (!(editText.getInputType() != 0) && this.accessibilityManager.isTouchExplorationEnabled()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setImportantForAccessibility(this.endIconView, 2);
        }
        textInputLayout.endLayout.setEndIconVisible(true);
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        if (!(this.autoCompleteTextView.getInputType() != 0)) {
            accessibilityNodeInfoCompat.setClassName(Spinner.class.getName());
        }
        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
        if (accessibilityNodeInfo.isShowingHintText()) {
            accessibilityNodeInfo.setHintText(null);
        }
    }

    @Override // com.google.android.material.textfield.EndIconDelegate
    public final void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 1 && this.accessibilityManager.isEnabled()) {
            if (this.autoCompleteTextView.getInputType() != 0) {
                return;
            }
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
        TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.setDuration(67);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = DropdownMenuEndIconDelegate.this;
                dropdownMenuEndIconDelegate.getClass();
                dropdownMenuEndIconDelegate.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.fadeInAnim = ofFloat;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat2.setInterpolator(timeInterpolator);
        ofFloat2.setDuration(50);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda3
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
