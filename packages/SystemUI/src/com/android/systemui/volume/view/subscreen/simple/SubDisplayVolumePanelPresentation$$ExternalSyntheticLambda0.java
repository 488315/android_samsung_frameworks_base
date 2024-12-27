package com.android.systemui.volume.view.subscreen.simple;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.ContextUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SubDisplayVolumePanelPresentation f$0;

    public /* synthetic */ SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0(SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation, int i) {
        this.$r8$classId = i;
        this.f$0 = subDisplayVolumePanelPresentation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = this.f$0;
        switch (i) {
            case 0:
                BlurEffect blurEffect = subDisplayVolumePanelPresentation.mBlurEffect;
                View view = subDisplayVolumePanelPresentation.mBlurView;
                blurEffect.getClass();
                BlurEffect.hideBlur(view);
                break;
            case 1:
                subDisplayVolumePanelPresentation.dismiss();
                break;
            case 2:
                BlurEffect blurEffect2 = subDisplayVolumePanelPresentation.mBlurEffect;
                View view2 = subDisplayVolumePanelPresentation.mBlurView;
                int color = subDisplayVolumePanelPresentation.getContext().getColor(R.color.sub_large_display_volume_expand_panel_bg_color_blur);
                float dimension = subDisplayVolumePanelPresentation.getContext().getResources().getDimension(R.dimen.volume_sub_large_display_dual_panel_radius);
                blurEffect2.getClass();
                BlurEffect.setRealTimeBlur(view2, color, dimension, 107);
                break;
            default:
                BlurEffect blurEffect3 = subDisplayVolumePanelPresentation.mBlurEffect;
                View view3 = subDisplayVolumePanelPresentation.mBlurView;
                int color2 = subDisplayVolumePanelPresentation.getContext().getColor(R.color.sub_large_display_volume_seekbar_background_color_blur);
                float dimension2 = subDisplayVolumePanelPresentation.getContext().getResources().getDimension(R.dimen.volume_sub_large_display_seek_bar_radius);
                BlurEffect blurEffect4 = subDisplayVolumePanelPresentation.mBlurEffect;
                boolean isNightMode = ContextUtils.isNightMode(subDisplayVolumePanelPresentation.getContext());
                blurEffect4.getClass();
                int i2 = isNightMode ? 106 : 121;
                blurEffect3.getClass();
                BlurEffect.setRealTimeBlur(view3, color2, dimension2, i2);
                break;
        }
    }
}
