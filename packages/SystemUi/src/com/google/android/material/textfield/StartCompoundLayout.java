package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StartCompoundLayout extends LinearLayout {
    public boolean hintExpanded;
    public CharSequence prefixText;
    public final AppCompatTextView prefixTextView;
    public View.OnLongClickListener startIconOnLongClickListener;
    public ColorStateList startIconTintList;
    public PorterDuff.Mode startIconTintMode;
    public final CheckableImageButton startIconView;
    public final TextInputLayout textInputLayout;

    public StartCompoundLayout(TextInputLayout textInputLayout, TintTypedArray tintTypedArray) {
        super(textInputLayout.getContext());
        CharSequence text;
        this.textInputLayout = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388611));
        CheckableImageButton checkableImageButton = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_start_icon, (ViewGroup) this, false);
        this.startIconView = checkableImageButton;
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.prefixTextView = appCompatTextView;
        if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).setMarginEnd(0);
        }
        View.OnLongClickListener onLongClickListener = this.startIconOnLongClickListener;
        checkableImageButton.setOnClickListener(null);
        IconHelper.setIconClickable(checkableImageButton, onLongClickListener);
        this.startIconOnLongClickListener = null;
        checkableImageButton.setOnLongClickListener(null);
        IconHelper.setIconClickable(checkableImageButton, null);
        if (tintTypedArray.hasValue(62)) {
            this.startIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, 62);
        }
        if (tintTypedArray.hasValue(63)) {
            this.startIconTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(63, -1), null);
        }
        if (tintTypedArray.hasValue(61)) {
            Drawable drawable = tintTypedArray.getDrawable(61);
            checkableImageButton.setImageDrawable(drawable);
            if (drawable != null) {
                IconHelper.applyIconTint(textInputLayout, checkableImageButton, this.startIconTintList, this.startIconTintMode);
                if (!(checkableImageButton.getVisibility() == 0)) {
                    checkableImageButton.setVisibility(0);
                    updatePrefixTextViewPadding();
                    updateVisibility();
                }
                IconHelper.refreshIconDrawableState(textInputLayout, checkableImageButton, this.startIconTintList);
            } else {
                if (checkableImageButton.getVisibility() == 0) {
                    checkableImageButton.setVisibility(8);
                    updatePrefixTextViewPadding();
                    updateVisibility();
                }
                View.OnLongClickListener onLongClickListener2 = this.startIconOnLongClickListener;
                checkableImageButton.setOnClickListener(null);
                IconHelper.setIconClickable(checkableImageButton, onLongClickListener2);
                this.startIconOnLongClickListener = null;
                checkableImageButton.setOnLongClickListener(null);
                IconHelper.setIconClickable(checkableImageButton, null);
                if (checkableImageButton.getContentDescription() != null) {
                    checkableImageButton.setContentDescription(null);
                }
            }
            if (tintTypedArray.hasValue(60) && checkableImageButton.getContentDescription() != (text = tintTypedArray.getText(60))) {
                checkableImageButton.setContentDescription(text);
            }
            boolean z = tintTypedArray.getBoolean(59, true);
            if (checkableImageButton.checkable != z) {
                checkableImageButton.checkable = z;
                checkableImageButton.sendAccessibilityEvent(0);
            }
        }
        appCompatTextView.setVisibility(8);
        appCompatTextView.setId(R.id.textinput_prefix_text);
        appCompatTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api19Impl.setAccessibilityLiveRegion(appCompatTextView, 1);
        appCompatTextView.setTextAppearance(tintTypedArray.getResourceId(55, 0));
        if (tintTypedArray.hasValue(56)) {
            appCompatTextView.setTextColor(tintTypedArray.getColorStateList(56));
        }
        CharSequence text2 = tintTypedArray.getText(54);
        this.prefixText = TextUtils.isEmpty(text2) ? null : text2;
        appCompatTextView.setText(text2);
        updateVisibility();
        addView(checkableImageButton);
        addView(appCompatTextView);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        updatePrefixTextViewPadding();
    }

    public final void updatePrefixTextViewPadding() {
        EditText editText = this.textInputLayout.editText;
        if (editText == null) {
            return;
        }
        int i = 0;
        if (!(this.startIconView.getVisibility() == 0)) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            i = ViewCompat.Api17Impl.getPaddingStart(editText);
        }
        AppCompatTextView appCompatTextView = this.prefixTextView;
        int compoundPaddingTop = editText.getCompoundPaddingTop();
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding);
        int compoundPaddingBottom = editText.getCompoundPaddingBottom();
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api17Impl.setPaddingRelative(appCompatTextView, i, compoundPaddingTop, dimensionPixelSize, compoundPaddingBottom);
    }

    public final void updateVisibility() {
        int i = (this.prefixText == null || this.hintExpanded) ? 8 : 0;
        setVisibility(this.startIconView.getVisibility() == 0 || i == 0 ? 0 : 8);
        this.prefixTextView.setVisibility(i);
        this.textInputLayout.updateDummyDrawables();
    }
}
