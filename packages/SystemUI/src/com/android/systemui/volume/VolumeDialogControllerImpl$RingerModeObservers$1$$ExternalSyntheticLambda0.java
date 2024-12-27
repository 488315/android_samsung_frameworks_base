package com.android.systemui.volume;

import android.util.Log;
import androidx.lifecycle.Observer;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.VolumeDialogControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class VolumeDialogControllerImpl$RingerModeObservers$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Observer f$0;
    public final /* synthetic */ Integer f$1;

    public /* synthetic */ VolumeDialogControllerImpl$RingerModeObservers$1$$ExternalSyntheticLambda0(Observer observer, Integer num, int i) {
        this.$r8$classId = i;
        this.f$0 = observer;
        this.f$1 = num;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                VolumeDialogControllerImpl.RingerModeObservers.AnonymousClass1 anonymousClass1 = (VolumeDialogControllerImpl.RingerModeObservers.AnonymousClass1) this.f$0;
                Integer num = this.f$1;
                anonymousClass1.getClass();
                int intValue = num.intValue();
                VolumeDialogControllerImpl.RingerModeObservers ringerModeObservers = VolumeDialogControllerImpl.RingerModeObservers.this;
                boolean initialSticky = ringerModeObservers.mRingerMode.getInitialSticky();
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                if (initialSticky) {
                    volumeDialogControllerImpl.mState.ringerModeExternal = intValue;
                }
                if (D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onChange ringer_mode rm=" + Util.ringerModeToString(intValue));
                }
                String str = VolumeDialogControllerImpl.TAG;
                VolumeDialogController.State state = volumeDialogControllerImpl.mState;
                if (intValue != state.ringerModeExternal) {
                    if (intValue == 1 && !volumeDialogControllerImpl.mIsVibrating) {
                        volumeDialogControllerImpl.mIsVibrating = true;
                        volumeDialogControllerImpl.mWorker.postDelayed(new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(volumeDialogControllerImpl, 0), 800L);
                    }
                    state.ringerModeExternal = intValue;
                    Events.writeEvent(12, num);
                    volumeDialogControllerImpl.updateStreamLevelW(2, volumeDialogControllerImpl.getLastAudibleStreamVolume(2));
                    volumeDialogControllerImpl.updateStreamLevelW(5, volumeDialogControllerImpl.getLastAudibleStreamVolume(5));
                    volumeDialogControllerImpl.updateStreamLevelW(1, volumeDialogControllerImpl.getLastAudibleStreamVolume(1));
                    volumeDialogControllerImpl.mCallbacks.onStateChanged(volumeDialogControllerImpl.mState);
                    break;
                }
                break;
            default:
                VolumeDialogControllerImpl.RingerModeObservers.AnonymousClass2 anonymousClass2 = (VolumeDialogControllerImpl.RingerModeObservers.AnonymousClass2) this.f$0;
                Integer num2 = this.f$1;
                anonymousClass2.getClass();
                int intValue2 = num2.intValue();
                VolumeDialogControllerImpl.RingerModeObservers ringerModeObservers2 = VolumeDialogControllerImpl.RingerModeObservers.this;
                boolean initialSticky2 = ringerModeObservers2.mRingerModeInternal.getInitialSticky();
                VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                if (initialSticky2) {
                    volumeDialogControllerImpl2.mState.ringerModeInternal = intValue2;
                }
                if (D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onChange internal_ringer_mode rm=" + Util.ringerModeToString(intValue2));
                }
                String str2 = VolumeDialogControllerImpl.TAG;
                if (volumeDialogControllerImpl2.updateRingerModeInternalW(intValue2)) {
                    VolumeDialogControllerImpl.m2365$$Nest$mupdateStreamVolume(volumeDialogControllerImpl2, 2);
                    VolumeDialogControllerImpl.m2365$$Nest$mupdateStreamVolume(volumeDialogControllerImpl2, 5);
                    VolumeDialogControllerImpl.m2365$$Nest$mupdateStreamVolume(volumeDialogControllerImpl2, 1);
                    volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
                    break;
                }
                break;
        }
    }
}
