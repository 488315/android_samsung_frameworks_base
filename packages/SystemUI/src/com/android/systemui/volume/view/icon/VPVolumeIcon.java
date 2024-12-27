package com.android.systemui.volume.view.icon;

import android.content.Context;
import android.util.AttributeSet;

public final class VPVolumeIcon extends VolumeIcon {
    public VPVolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.volume.view.icon.VolumeIcon, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return "android.widget.Button";
    }
}
