package com.android.systemui.qp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;

public class SubroomFlashLightSettingsView extends LinearLayout {
    public LinearLayout mFlashLightBackground;
    public ImageView mFlashLightButton;

    public SubroomFlashLightSettingsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        String string;
        super.onAttachedToWindow();
        boolean isEnabled = ((FlashlightControllerImpl) ((FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class))).isEnabled();
        StringBuffer stringBuffer = new StringBuffer();
        if (isEnabled) {
            this.mFlashLightBackground.setBackground(((LinearLayout) this).mContext.getResources().getDrawable(R.drawable.subroom_active_background));
            string = ((LinearLayout) this).mContext.getString(R.string.accessibility_desc_on);
        } else {
            this.mFlashLightBackground.setBackground(((LinearLayout) this).mContext.getResources().getDrawable(R.drawable.subroom_inactive_background));
            string = ((LinearLayout) this).mContext.getString(R.string.accessibility_desc_off);
        }
        stringBuffer.append(((LinearLayout) this).mContext.getString(R.string.quick_settings_flashlight_label));
        stringBuffer.append(",");
        stringBuffer.append(string);
        this.mFlashLightButton.setContentDescription(stringBuffer);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mFlashLightBackground = (LinearLayout) findViewById(R.id.flashlight_background);
        this.mFlashLightButton = (ImageView) findViewById(R.id.flashlight_image_view);
    }
}
