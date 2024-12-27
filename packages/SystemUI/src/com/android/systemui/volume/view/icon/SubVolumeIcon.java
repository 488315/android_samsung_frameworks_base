package com.android.systemui.volume.view.icon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.jvm.internal.Intrinsics;

public final class SubVolumeIcon extends VolumeIcon {
    public SubVolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.volume.view.icon.VolumeIcon, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return "android.widget.Button";
    }

    @Override // com.android.systemui.volume.view.icon.VolumeIcon
    public final ScreenState getScreenState() {
        return BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? ScreenState.SCREEN_SUB_LARGE_DISPLAY : ScreenState.SCREEN_SUB_DISPLAY;
    }

    @Override // com.android.systemui.volume.view.icon.VolumeIcon
    public final View inflateIconView(boolean z) {
        View inflate;
        if (!z) {
            View inflate2 = LayoutInflater.from(getContext()).inflate(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_default_icon : R.layout.volume_sub_display_default_icon, (ViewGroup) null);
            Intrinsics.checkNotNull(inflate2);
            return inflate2;
        }
        if (VolumeIcons.isForMediaIcon(this.stream)) {
            inflate = LayoutInflater.from(getContext()).inflate(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_media_icon : R.layout.volume_sub_display_media_icon, (ViewGroup) null);
        } else if (VolumePanelValues.isRing(this.stream)) {
            inflate = LayoutInflater.from(getContext()).inflate(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_ringtone_icon : R.layout.volume_sub_display_ringtone_icon, (ViewGroup) null);
        } else {
            inflate = LayoutInflater.from(getContext()).inflate(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_animated_icon : R.layout.volume_sub_display_animated_icon, (ViewGroup) null);
        }
        Intrinsics.checkNotNull(inflate);
        return inflate;
    }

    @Override // com.android.systemui.volume.view.icon.VolumeIcon
    public final void initVolumeIconColor(int i, int i2, int i3) {
        Context context = getContext();
        boolean z = BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG;
        int i4 = R.color.sub_large_display_volume_icon_color;
        int color = context.getColor(z ? R.color.sub_large_display_volume_icon_color : R.color.sub_display_volume_progress_color);
        Context context2 = getContext();
        if (!z) {
            i4 = R.color.sub_display_volume_progress_bg_color;
        }
        super.initVolumeIconColor(color, context2.getColor(i4), getContext().getColor(R.color.sub_display_volume_progress_earshock_color));
    }
}
