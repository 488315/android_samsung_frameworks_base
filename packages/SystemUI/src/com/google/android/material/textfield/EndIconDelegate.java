package com.google.android.material.textfield;

import android.content.Context;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.internal.CheckableImageButton;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class EndIconDelegate {
    public final Context context;
    public final CheckableImageButton endIconView;
    public final EndCompoundLayout endLayout;
    public final TextInputLayout textInputLayout;

    public EndIconDelegate(EndCompoundLayout endCompoundLayout) {
        this.textInputLayout = endCompoundLayout.textInputLayout;
        this.endLayout = endCompoundLayout;
        this.context = endCompoundLayout.getContext();
        this.endIconView = endCompoundLayout.endIconView;
    }

    public int getIconContentDescriptionResId() {
        return 0;
    }

    public int getIconDrawableResId() {
        return 0;
    }

    public View.OnFocusChangeListener getOnEditTextFocusChangeListener() {
        return null;
    }

    public View.OnClickListener getOnIconClickListener() {
        return null;
    }

    public View.OnFocusChangeListener getOnIconViewFocusChangeListener() {
        return null;
    }

    public AccessibilityManagerCompat$TouchExplorationStateChangeListener getTouchExplorationStateChangeListener() {
        return null;
    }

    public boolean isBoxBackgroundModeSupported(int i) {
        return true;
    }

    public boolean isIconActivated() {
        return false;
    }

    public boolean isIconCheckable() {
        return this instanceof DropdownMenuEndIconDelegate;
    }

    public boolean isIconChecked() {
        return false;
    }

    public final void refreshIconState() {
        this.endLayout.refreshIconState(false);
    }

    public void afterEditTextChanged() {
    }

    public void beforeEditTextChanged() {
    }

    public void setUp() {
    }

    public void tearDown() {
    }

    public void onEditTextAttached(EditText editText) {
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    public void onSuffixVisibilityChanged(boolean z) {
    }
}
