package com.android.systemui.volume;

import android.view.View;
import com.android.systemui.Flags;
import com.android.systemui.Prefs;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.domain.model.VolumePanelRoute;
import com.android.systemui.volume.ui.navigation.VolumeNavigator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda11 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda11(VolumeDialogImpl volumeDialogImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = volumeDialogImpl;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = 0;
        int i2 = this.$r8$classId;
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        switch (i2) {
            case 0:
                volumeDialogImpl.getClass();
                Events.writeEvent(8, new Object[0]);
                volumeDialogImpl.dismissH(5);
                volumeDialogImpl.mMediaOutputDialogManager.getClass();
                int i3 = MediaOutputDialogManager.$r8$clinit;
                VolumeNavigator volumeNavigator = volumeDialogImpl.mVolumeNavigator;
                volumeDialogImpl.mVolumePanelNavigationInteractor.volumePanelFlag.getClass();
                Flags.FEATURE_FLAGS.getClass();
                volumeNavigator.openVolumePanel(VolumePanelRoute.COMPOSE_VOLUME_PANEL);
                break;
            case 1:
                Prefs.putBoolean(volumeDialogImpl.mContext, "TouchedRingerToggle", true);
                VolumeDialogController.StreamState streamState = volumeDialogImpl.mState.states.get(2);
                if (streamState != null) {
                    boolean hasVibrator = volumeDialogImpl.mController.hasVibrator();
                    int i4 = volumeDialogImpl.mState.ringerModeInternal;
                    if (i4 == 2) {
                        if (hasVibrator) {
                            i = 1;
                        }
                    } else if (i4 != 1) {
                        if (streamState.level == 0) {
                            volumeDialogImpl.mController.setStreamVolume(2, 1);
                        }
                        i = 2;
                    }
                    volumeDialogImpl.setRingerMode(i);
                    break;
                }
                break;
            default:
                if (!volumeDialogImpl.mIsRingerDrawerOpen) {
                    volumeDialogImpl.showRingerDrawer();
                    break;
                } else {
                    volumeDialogImpl.hideRingerDrawer();
                    break;
                }
        }
    }
}
