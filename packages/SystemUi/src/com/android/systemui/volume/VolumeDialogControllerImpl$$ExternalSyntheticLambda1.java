package com.android.systemui.volume;

import com.android.systemui.plugins.VolumeDialogController;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumeDialogControllerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ VolumeDialogControllerImpl$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((VolumeDialogControllerImpl) this.f$0).mIsVibrating = false;
                break;
            case 1:
                VolumeDialogControllerImpl volumeDialogControllerImpl = (VolumeDialogControllerImpl) this.f$0;
                if (volumeDialogControllerImpl.mIsBudsTogetherEnabled) {
                    volumeDialogControllerImpl.onVolumeChangedW(23, 0);
                }
                volumeDialogControllerImpl.updateStreamNameMusicShare();
                break;
            case 2:
                ((VolumeDialogControllerImpl) this.f$0).updateStreamNameMusicShare();
                break;
            default:
                ((VolumeDialogController.Callbacks) ((Map.Entry) this.f$0).getKey()).onShowVolumeLimiterToast();
                break;
        }
    }
}
