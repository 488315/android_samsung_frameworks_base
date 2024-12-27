package com.android.systemui.power.data.repository;

import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.power.shared.model.WakefulnessState;

public interface PowerRepository {
    static void updateWakefulness$default(PowerRepository powerRepository, WakefulnessState wakefulnessState, WakeSleepReason wakeSleepReason, WakeSleepReason wakeSleepReason2, boolean z, int i) {
        if ((i & 1) != 0) {
            wakefulnessState = ((WakefulnessModel) ((PowerRepositoryImpl) powerRepository).wakefulness.$$delegate_0.getValue()).internalWakefulnessState;
        }
        if ((i & 2) != 0) {
            wakeSleepReason = ((WakefulnessModel) ((PowerRepositoryImpl) powerRepository).wakefulness.$$delegate_0.getValue()).lastWakeReason;
        }
        if ((i & 4) != 0) {
            wakeSleepReason2 = ((WakefulnessModel) ((PowerRepositoryImpl) powerRepository).wakefulness.$$delegate_0.getValue()).lastSleepReason;
        }
        if ((i & 8) != 0) {
            z = ((WakefulnessModel) ((PowerRepositoryImpl) powerRepository).wakefulness.$$delegate_0.getValue()).powerButtonLaunchGestureTriggered;
        }
        ((PowerRepositoryImpl) powerRepository)._wakefulness.updateState(null, new WakefulnessModel(wakefulnessState, wakeSleepReason, wakeSleepReason2, z));
    }
}
