package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.shared.model.WakefulnessModel;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(((WakefulnessModel) obj).isAwake());
            case 1:
                KeyguardState.Companion.getClass();
                return Boolean.valueOf(KeyguardState.Companion.deviceIsAsleepInState((KeyguardState) obj));
            default:
                KeyguardState.Companion.getClass();
                return Boolean.valueOf(KeyguardState.Companion.deviceIsAwakeInState((KeyguardState) obj));
        }
    }
}
