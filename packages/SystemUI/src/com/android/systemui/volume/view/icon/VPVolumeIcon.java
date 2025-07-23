package com.android.systemui.volume.view.icon;

import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VPVolumeIcon extends VolumeIcon {
    public VPVolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.volume.view.icon.VolumeIcon, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return "android.widget.Button";
    }
}
