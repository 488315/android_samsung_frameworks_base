package com.android.systemui.volume;

import com.android.systemui.plugins.VolumeDialogController;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogControllerImpl$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ VolumeDialogControllerImpl$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((VolumeDialogControllerImpl) obj).mIsVibrating = false;
                break;
            case 1:
                VolumeDialogControllerImpl volumeDialogControllerImpl = (VolumeDialogControllerImpl) obj;
                volumeDialogControllerImpl.mCallbacks.onStateChanged(volumeDialogControllerImpl.mState);
                break;
            case 2:
                VolumeDialogControllerImpl volumeDialogControllerImpl2 = (VolumeDialogControllerImpl) obj;
                if (volumeDialogControllerImpl2.mIsBudsTogetherEnabled) {
                    volumeDialogControllerImpl2.onVolumeChangedW(23, 0);
                }
                volumeDialogControllerImpl2.updateStreamNameMusicShare();
                break;
            case 3:
                String str = VolumeDialogControllerImpl.TAG;
                ((VolumeDialogControllerImpl) obj).updateStreamNameMusicShare();
                break;
            default:
                ((VolumeDialogController.Callbacks) ((Map.Entry) obj).getKey()).onShowVolumeLimiterToast();
                break;
        }
    }
}
