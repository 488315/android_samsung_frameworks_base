package com.android.systemui.qs.bar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.util.RecoilEffectUtil;
import kotlin.jvm.functions.Function0;

public final class VideoCallMicModeUtil {
    public final Function0 coloredBGHelper;
    public final Context context;
    public final SecQSPanelResourcePicker resourcePicker;

    public VideoCallMicModeUtil(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, Function0 function0) {
        this.context = context;
        this.resourcePicker = secQSPanelResourcePicker;
        this.coloredBGHelper = function0;
    }

    public final VideoCallMicModeResources getResources() {
        Context context = this.context;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        int tileIconStartMargin = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTileIconStartMargin(context);
        int tileIconSize = secQSPanelResourcePicker.getTileIconSize(this.context);
        int pixelSize = toPixelSize(R.dimen.video_call_effects_mic_mode_icon_size);
        return new VideoCallMicModeResources(tileIconStartMargin, pixelSize, AbsActionBarView$$ExternalSyntheticOutline0.m(tileIconSize, pixelSize, 2, tileIconStartMargin), QpRune.QUICK_TABLET ? 0 : toPixelSize(R.dimen.video_call_effects_mic_mode_sub_text_start_padding), toPixelSize(R.dimen.video_call_effects_mic_mode_text_container_start_padding), toPixelSize(R.dimen.video_call_effects_mic_mode_text_container_end_padding), (toPixelSize(R.dimen.large_tile_between_margin) + secQSPanelResourcePicker.getPanelWidth(this.context)) / 4, toPixelSize(R.dimen.large_tile_between_margin), toPixelSize(R.dimen.video_call_effects_mic_mode_divider_padding));
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
        ColoredBGHelper coloredBGHelper = (ColoredBGHelper) this.coloredBGHelper.invoke();
        if (coloredBGHelper != null) {
            coloredBGHelper.addBarBackground(inflate, false);
        }
        inflate.setStateListAnimator(RecoilEffectUtil.getRecoilSmallAnimator(inflate.getContext()));
        return inflate;
    }

    public final int toPixelSize(int i) {
        return this.context.getResources().getDimensionPixelSize(i);
    }
}
