package com.android.wm.shell.controlpanel.activity;

import android.provider.Settings;
import com.android.wm.shell.controlpanel.utils.CheckControlWindowState;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FlexPanelActivity$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FlexPanelActivity f$0;

    public /* synthetic */ FlexPanelActivity$$ExternalSyntheticLambda2(FlexPanelActivity flexPanelActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = flexPanelActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FlexPanelActivity flexPanelActivity = this.f$0;
                int i = FlexPanelActivity.mEditPanelItemSize;
                if (flexPanelActivity.findViewById(R.id.edit_panel_view) != null) {
                    flexPanelActivity.findViewById(R.id.edit_panel_view).setVisibility(8);
                    break;
                }
                break;
            case 1:
                FlexPanelActivity flexPanelActivity2 = this.f$0;
                int i2 = FlexPanelActivity.mEditPanelItemSize;
                if (Settings.System.getInt(flexPanelActivity2.getContentResolver(), "media_floating_only", 0) != 1 && !CheckControlWindowState.isMediaPanelRequestedState(flexPanelActivity2, flexPanelActivity2.mMediaController)) {
                    flexPanelActivity2.setupBasicPanel();
                    break;
                } else if (!flexPanelActivity2.getPreferences$1("MEDIA_TOUCH_PAD_ENABLED")) {
                    flexPanelActivity2.setupMediaPanel();
                    break;
                } else {
                    flexPanelActivity2.setupTouchPadMediaPanel();
                    break;
                }
                break;
            case 2:
                FlexPanelActivity flexPanelActivity3 = this.f$0;
                int i3 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity3.onDragEnded();
                break;
            case 3:
                FlexPanelActivity flexPanelActivity4 = this.f$0;
                flexPanelActivity4.mBrightnessVolumeView.setVisibility(8);
                flexPanelActivity4.mUpperArea.setVisibility(0);
                flexPanelActivity4.mUpperArea.startAnimation(flexPanelActivity4.mSliderIn);
                flexPanelActivity4.mBrightnessVolumeType = -1;
                break;
            case 4:
                FlexPanelActivity flexPanelActivity5 = this.f$0;
                int i4 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity5.setupTouchPadMediaPanel();
                break;
            case 5:
                FlexPanelActivity flexPanelActivity6 = this.f$0;
                int i5 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity6.setupMediaPanel();
                break;
            case 6:
                FlexPanelActivity flexPanelActivity7 = this.f$0;
                int i6 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity7.closeOperation();
                break;
            default:
                FlexPanelActivity flexPanelActivity8 = this.f$0;
                int i7 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity8.closeOperation();
                break;
        }
    }
}
