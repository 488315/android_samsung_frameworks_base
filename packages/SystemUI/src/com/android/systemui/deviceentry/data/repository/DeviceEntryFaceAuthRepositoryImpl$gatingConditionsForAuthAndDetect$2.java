package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$2 extends FunctionReferenceImpl implements Function1 {
    public DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$2(Object obj) {
        super(1, obj, KeyguardState.Companion.class, "deviceIsAsleepInState", "deviceIsAsleepInState(Lcom/android/systemui/keyguard/shared/model/KeyguardState;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((KeyguardState.Companion) this.receiver).getClass();
        return Boolean.valueOf(!KeyguardState.Companion.deviceIsAwakeInState((KeyguardState) obj));
    }
}
