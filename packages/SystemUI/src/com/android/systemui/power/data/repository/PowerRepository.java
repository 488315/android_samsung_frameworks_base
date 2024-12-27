package com.android.systemui.power.data.repository;

import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.power.shared.model.WakefulnessState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
