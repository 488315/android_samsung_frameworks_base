package com.android.systemui.volume;

import com.android.systemui.plugins.VolumeDialogController;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogControllerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ VolumeDialogControllerImpl$$ExternalSyntheticLambda1(Object obj, int i) {
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
                ((VolumeDialogControllerImpl) obj).updateStreamNameMusicShare();
                break;
            default:
                ((VolumeDialogController.Callbacks) ((Map.Entry) obj).getKey()).onShowVolumeLimiterToast();
                break;
        }
    }
}
