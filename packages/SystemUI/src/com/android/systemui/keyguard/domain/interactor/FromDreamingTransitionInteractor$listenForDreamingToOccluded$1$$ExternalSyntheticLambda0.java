package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class FromDreamingTransitionInteractor$listenForDreamingToOccluded$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Pair pair = (Pair) obj;
                return Boolean.valueOf(((Boolean) pair.component1()).booleanValue() && !((Boolean) pair.component2()).booleanValue());
            case 1:
                return Boolean.valueOf(((BiometricUnlockModel) obj).mode == BiometricUnlockMode.WAKE_AND_UNLOCK_FROM_DREAM);
            default:
                return Boolean.valueOf(!((Boolean) obj).booleanValue());
        }
    }
}
