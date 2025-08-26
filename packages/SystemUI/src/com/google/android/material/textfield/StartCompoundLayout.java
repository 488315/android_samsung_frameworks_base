package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

/* loaded from: classes4.dex */
public class StartCompoundLayout extends LinearLayout {
    public boolean hintExpanded;
    public final CharSequence prefixText;
    public final AppCompatTextView prefixTextView;
    public final int startIconMinSize;
    public final ColorStateList startIconTintList;
    public final PorterDuff.Mode startIconTintMode;
    public final CheckableImageButton startIconView;
    public final TextInputLayout textInputLayout;

    public StartCompoundLayout(TextInputLayout textInputLayout, TintTypedArray tintTypedArray) throws Resources.NotFoundException {
        CharSequence text;
        super(textInputLayout.getContext());
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
        checkableImageButton.setOnClickListener(null);
        IconHelper.setIconClickable(checkableImageButton);
        checkableImageButton.setOnLongClickListener(null);
        IconHelper.setIconClickable(checkableImageButton);
        if (tintTypedArray.mWrapped.hasValue(69)) {
            this.startIconTintList = MaterialResources.getColorStateList(getContext(), tintTypedArray, 69);
        }
        if (tintTypedArray.mWrapped.hasValue(70)) {
            this.startIconTintMode = ViewUtils.parseTintMode(tintTypedArray.mWrapped.getInt(70, -1), null);
        }
        if (tintTypedArray.mWrapped.hasValue(66)) {
            Drawable drawable = tintTypedArray.getDrawable(66);
            checkableImageButton.setImageDrawable(drawable);
            if (drawable != null) {
                IconHelper.applyIconTint(textInputLayout, checkableImageButton, this.startIconTintList, this.startIconTintMode);
                if (checkableImageButton.getVisibility() != 0) {
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
                checkableImageButton.setOnClickListener(null);
                IconHelper.setIconClickable(checkableImageButton);
                checkableImageButton.setOnLongClickListener(null);
                IconHelper.setIconClickable(checkableImageButton);
                if (checkableImageButton.getContentDescription() != null) {
                    checkableImageButton.setContentDescription(null);
                }
            }
            if (tintTypedArray.mWrapped.hasValue(65) && checkableImageButton.getContentDescription() != (text = tintTypedArray.mWrapped.getText(65))) {
                checkableImageButton.setContentDescription(text);
            }
            boolean z = tintTypedArray.mWrapped.getBoolean(64, true);
            if (checkableImageButton.checkable != z) {
                checkableImageButton.checkable = z;
                checkableImageButton.sendAccessibilityEvent(0);
            }
        }
        int dimensionPixelSize = tintTypedArray.mWrapped.getDimensionPixelSize(67, getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size));
        if (dimensionPixelSize < 0) {
            throw new IllegalArgumentException("startIconSize cannot be less than 0");
        }
        if (dimensionPixelSize != this.startIconMinSize) {
            this.startIconMinSize = dimensionPixelSize;
            checkableImageButton.setMinimumWidth(dimensionPixelSize);
            checkableImageButton.setMinimumHeight(dimensionPixelSize);
        }
        if (tintTypedArray.mWrapped.hasValue(68)) {
            checkableImageButton.setScaleType(IconHelper.convertScaleType(tintTypedArray.mWrapped.getInt(68, -1)));
        }
        appCompatTextView.setVisibility(8);
        appCompatTextView.setId(R.id.textinput_prefix_text);
        appCompatTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        appCompatTextView.setAccessibilityLiveRegion(1);
        appCompatTextView.setTextAppearance(tintTypedArray.mWrapped.getResourceId(60, 0));
        if (tintTypedArray.mWrapped.hasValue(61)) {
            appCompatTextView.setTextColor(tintTypedArray.getColorStateList(61));
        }
        CharSequence text2 = tintTypedArray.mWrapped.getText(59);
        this.prefixText = TextUtils.isEmpty(text2) ? null : text2;
        appCompatTextView.setText(text2);
        updateVisibility();
        addView(checkableImageButton);
        addView(appCompatTextView);
    }

    public final int getPrefixTextStartOffset() {
        int marginEnd;
        if (this.startIconView.getVisibility() == 0) {
            marginEnd = ((ViewGroup.MarginLayoutParams) this.startIconView.getLayoutParams()).getMarginEnd() + this.startIconView.getMeasuredWidth();
        } else {
            marginEnd = 0;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return this.prefixTextView.getPaddingStart() + getPaddingStart() + marginEnd;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) throws Resources.NotFoundException {
        super.onMeasure(i, i2);
        updatePrefixTextViewPadding();
    }

    public final void updatePrefixTextViewPadding() throws Resources.NotFoundException {
        int paddingStart;
        EditText editText = this.textInputLayout.editText;
        if (editText == null) {
            return;
        }
        if (this.startIconView.getVisibility() == 0) {
            paddingStart = 0;
        } else {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            paddingStart = editText.getPaddingStart();
        }
        AppCompatTextView appCompatTextView = this.prefixTextView;
        int compoundPaddingTop = editText.getCompoundPaddingTop();
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding);
        int compoundPaddingBottom = editText.getCompoundPaddingBottom();
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        appCompatTextView.setPaddingRelative(paddingStart, compoundPaddingTop, dimensionPixelSize, compoundPaddingBottom);
    }

    public final void updateVisibility() {
        int i = (this.prefixText == null || this.hintExpanded) ? 8 : 0;
        setVisibility((this.startIconView.getVisibility() == 0 || i == 0) ? 0 : 8);
        this.prefixTextView.setVisibility(i);
        this.textInputLayout.updateDummyDrawables();
    }
}
