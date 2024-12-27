package com.android.systemui.keyguard.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import kotlin.Unit;

public final class KeyguardIndicationArea extends LinearLayout {
    public KeyguardIndicationArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setId(R.id.keyguard_indication_area);
        setOrientation(1);
        KeyguardIndicationTextView keyguardIndicationTextView = new KeyguardIndicationTextView(getContext(), attributeSet);
        keyguardIndicationTextView.setId(R.id.keyguard_indication_text);
        keyguardIndicationTextView.setGravity(17);
        keyguardIndicationTextView.setAccessibilityLiveRegion(1);
        keyguardIndicationTextView.setTextAppearance(R.style.TextAppearance_Keyguard_BottomArea);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.keyguard_indication_text_padding);
        keyguardIndicationTextView.setPaddingRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
        addView(keyguardIndicationTextView, new LinearLayout.LayoutParams(-1, -2));
        KeyguardIndicationTextView keyguardIndicationTextView2 = new KeyguardIndicationTextView(getContext(), attributeSet);
        keyguardIndicationTextView2.setId(R.id.keyguard_indication_text_bottom);
        keyguardIndicationTextView2.setGravity(17);
        keyguardIndicationTextView2.setAccessibilityLiveRegion(1);
        keyguardIndicationTextView2.setTextAppearance(R.style.TextAppearance_Keyguard_BottomArea);
        keyguardIndicationTextView2.setEllipsize(TextUtils.TruncateAt.END);
        keyguardIndicationTextView2.setAlpha(0.8f);
        keyguardIndicationTextView2.setMinHeight(getContext().getResources().getDimensionPixelSize(R.dimen.keyguard_indication_text_min_height));
        keyguardIndicationTextView2.setMaxLines(2);
        keyguardIndicationTextView2.setVisibility(8);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.keyguard_indication_text_padding);
        keyguardIndicationTextView2.setPaddingRelative(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 1;
        Unit unit = Unit.INSTANCE;
        addView(keyguardIndicationTextView2, layoutParams);
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        super.setAlpha(f);
        if (f == 0.0f) {
            setImportantForAccessibility(4);
        } else {
            setImportantForAccessibility(0);
        }
    }
}
