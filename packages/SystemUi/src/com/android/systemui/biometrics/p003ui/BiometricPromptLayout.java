package com.android.systemui.biometrics.p003ui;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthDialog;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            if (childAt.getId() == R.id.space_above_icon || childAt.getId() == R.id.space_below_icon || childAt.getId() == R.id.button_bar) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(min, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            } else if (childAt.getId() == R.id.biometric_icon_frame) {
                View findViewById = findViewById(R.id.biometric_icon);
                childAt.measure(View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().width, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().height, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            } else if (childAt.getId() == R.id.biometric_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(min, VideoPlayer.MEDIA_ERROR_SYSTEM), View.MeasureSpec.makeMeasureSpec(size2, VideoPlayer.MEDIA_ERROR_SYSTEM));
            } else {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(min, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(size2, VideoPlayer.MEDIA_ERROR_SYSTEM));
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
