package com.android.systemui.p016qs.bar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VideoCallMicModeUtil {
    public final Context context;
    public final SecQSPanelResourcePicker resourcePicker;

    public VideoCallMicModeUtil(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        this.context = context;
        this.resourcePicker = secQSPanelResourcePicker;
    }

    public final int getPixelSize(int i) {
        return this.context.getResources().getDimensionPixelSize(i);
    }

    public final VideoCallMicModeResources getResources() {
        this.resourcePicker.getClass();
        Context context = this.context;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_icon_margin_start);
        int tileIconSize = SecQSPanelResourcePicker.getTileIconSize(context);
        int pixelSize = getPixelSize(R.dimen.video_call_effects_mic_mode_icon_size);
        return new VideoCallMicModeResources(dimensionPixelSize, pixelSize, AbsActionBarView$$ExternalSyntheticOutline0.m8m(tileIconSize, pixelSize, 2, dimensionPixelSize), QpRune.QUICK_TABLET ? 0 : getPixelSize(R.dimen.video_call_effects_mic_mode_sub_text_start_padding), getPixelSize(R.dimen.video_call_effects_mic_mode_text_container_start_padding), getPixelSize(R.dimen.video_call_effects_mic_mode_text_container_end_padding), (getPixelSize(R.dimen.large_tile_temp_between_margin) + SecQSPanelResourcePicker.getPanelWidth(context)) / 4, getPixelSize(R.dimen.large_tile_temp_between_margin), getPixelSize(R.dimen.video_call_effects_mic_mode_divider_padding));
    }

    public final View inflate(int i, ViewGroup viewGroup, boolean z) {
        View inflate = LayoutInflater.from(this.context).inflate(i, viewGroup, false);
        if (inflate == null) {
            return null;
        }
        if (!z) {
            return inflate;
        }
        inflate.setBackground(inflate.getContext().getDrawable(R.drawable.sec_large_button_ripple_background));
        return inflate;
    }
}
