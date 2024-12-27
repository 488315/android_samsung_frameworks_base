package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1 extends FunctionReferenceImpl implements Function1 {
    public DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1(Object obj) {
        super(1, obj, KeyguardState.Companion.class, "deviceIsAwakeInState", "deviceIsAwakeInState(Lcom/android/systemui/keyguard/shared/model/KeyguardState;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((KeyguardState.Companion) this.receiver).getClass();
        return Boolean.valueOf(KeyguardState.Companion.deviceIsAwakeInState((KeyguardState) obj));
    }
}
