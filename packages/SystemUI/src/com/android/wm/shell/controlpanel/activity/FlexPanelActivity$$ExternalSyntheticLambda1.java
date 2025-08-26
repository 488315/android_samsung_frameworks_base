package com.android.wm.shell.controlpanel.activity;

import android.provider.Settings;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.utils.CheckControlWindowState;
import org.json.JSONException;

/* loaded from: classes3.dex */
public final /* synthetic */ class FlexPanelActivity$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FlexPanelActivity f$0;

    public /* synthetic */ FlexPanelActivity$$ExternalSyntheticLambda1(FlexPanelActivity flexPanelActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = flexPanelActivity;
    }

    @Override // java.lang.Runnable
    public final void run() throws JSONException {
        int i = this.$r8$classId;
        FlexPanelActivity flexPanelActivity = this.f$0;
        switch (i) {
            case 0:
                int i2 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity.onDragEnded();
                break;
            case 1:
                int i3 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity.setupMediaPanel();
                break;
            case 2:
                int i4 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity.closeOperation();
                break;
            case 3:
                flexPanelActivity.mGridAdapter.notifyDataSetChanged();
                break;
            case 4:
                int i5 = FlexPanelActivity.mEditPanelItemSize;
                if (flexPanelActivity.findViewById(R.id.edit_panel_view) != null) {
                    flexPanelActivity.findViewById(R.id.edit_panel_view).setVisibility(8);
                    break;
                }
                break;
            case 5:
                int i6 = FlexPanelActivity.mEditPanelItemSize;
                if (Settings.System.getInt(flexPanelActivity.getContentResolver(), "media_floating_only", 0) != 1 && !CheckControlWindowState.isMediaPanelRequestedState(flexPanelActivity, flexPanelActivity.mMediaController)) {
                    flexPanelActivity.setupBasicPanel();
                    break;
                } else if (!flexPanelActivity.getPreferences("MEDIA_TOUCH_PAD_ENABLED")) {
                    flexPanelActivity.setupMediaPanel();
                    break;
                } else {
                    flexPanelActivity.setupTouchPadMediaPanel();
                    break;
                }
                break;
            case 6:
                flexPanelActivity.mBrightnessVolumeView.setVisibility(8);
                flexPanelActivity.mUpperArea.setVisibility(0);
                flexPanelActivity.mUpperArea.startAnimation(flexPanelActivity.mSliderIn);
                flexPanelActivity.mBrightnessVolumeType = -1;
                break;
            default:
                int i7 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity.setupTouchPadMediaPanel();
                break;
        }
    }
}
