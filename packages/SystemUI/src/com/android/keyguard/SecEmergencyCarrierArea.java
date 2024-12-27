package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.widget.SystemUIButton;

public class SecEmergencyCarrierArea extends EmergencyCarrierArea {
    public LinearLayout mEmergencyButtonArea;
    public SystemUIButton mForgotPatternButton;

    public SecEmergencyCarrierArea(Context context) {
        super(context);
    }

    @Override // com.android.keyguard.EmergencyCarrierArea, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mForgotPatternButton = (SystemUIButton) findViewById(R.id.forgot_password_button);
        this.mEmergencyButton = (EmergencyButton) findViewById(R.id.emergency_call_button);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.keyguard_emergency_button_area);
        this.mEmergencyButtonArea = linearLayout;
        if (linearLayout == null || this.mEmergencyButton == null || this.mForgotPatternButton == null) {
            return;
        }
        View view = null;
        int i = 0;
        for (int i2 = 0; i2 < this.mEmergencyButtonArea.getChildCount(); i2++) {
            if (this.mEmergencyButtonArea.getChildAt(i2) != null && this.mEmergencyButtonArea.getChildAt(i2).getVisibility() == 0) {
                i++;
                view = this.mEmergencyButtonArea.getChildAt(i2);
            }
        }
        if (i > 0 && this.mEmergencyButton.getVisibility() == 4) {
            this.mEmergencyButton.setVisibility(8);
        }
        if (i == 1) {
            if (view != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.width = -2;
                layoutParams.weight = 0.0f;
                view.setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        if (i != 2) {
            return;
        }
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mEmergencyButton.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mForgotPatternButton.getLayoutParams();
        layoutParams2.width = 0;
        layoutParams3.width = 0;
        layoutParams2.weight = 1.0f;
        layoutParams3.weight = 1.0f;
        this.mEmergencyButton.setLayoutParams(layoutParams2);
        this.mForgotPatternButton.setLayoutParams(layoutParams3);
        this.mForgotPatternButton.setPadding(0, 0, 0, 0);
    }

    public SecEmergencyCarrierArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
