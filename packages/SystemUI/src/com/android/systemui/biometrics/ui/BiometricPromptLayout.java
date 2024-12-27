package com.android.systemui.biometrics.ui;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthDialog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class BiometricPromptLayout extends LinearLayout {
    public final int mCustomBpHeight;
    public final int mCustomBpWidth;
    public final boolean mUseCustomBpSize;
    public final WindowManager mWindowManager;

    public BiometricPromptLayout(Context context) {
        this(context, null);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int min;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (this.mUseCustomBpSize) {
            min = this.mCustomBpWidth;
            size2 = this.mCustomBpHeight;
        } else {
            min = Math.min(size, size2);
        }
        Insets insets = this.mWindowManager.getMaximumWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars());
        int childCount = getChildCount();
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getId() == R.id.space_above_icon || childAt.getId() == R.id.space_above_content || childAt.getId() == R.id.space_below_icon || childAt.getId() == R.id.button_bar) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(min, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == R.id.biometric_icon_frame) {
                View findViewById = findViewById(R.id.biometric_icon);
                childAt.measure(View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().width, 1073741824), View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == R.id.logo) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().width, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == R.id.biometric_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(min, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE));
            } else {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(min, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE));
            }
            if (childAt.getVisibility() != 8) {
                i3 = childAt.getMeasuredHeight() + i3;
            }
        }
        AuthDialog.LayoutParams layoutParams = new AuthDialog.LayoutParams(min, i3);
        setMeasuredDimension(layoutParams.mMediumWidth + insets.left + insets.right, layoutParams.mMediumHeight + insets.bottom);
    }

    public BiometricPromptLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mUseCustomBpSize = getResources().getBoolean(R.bool.use_custom_bp_size);
        this.mCustomBpWidth = getResources().getDimensionPixelSize(R.dimen.biometric_dialog_width);
        this.mCustomBpHeight = getResources().getDimensionPixelSize(R.dimen.biometric_dialog_height);
    }
}
