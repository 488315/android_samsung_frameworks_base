package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$3 extends FunctionReferenceImpl implements Function1 {
    public DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$3(Object obj) {
        super(1, obj, KeyguardState.Companion.class, "deviceIsAsleepInState", "deviceIsAsleepInState(Lcom/android/systemui/keyguard/shared/model/KeyguardState;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((KeyguardState.Companion) this.receiver).getClass();
        return Boolean.valueOf(KeyguardState.Companion.deviceIsAsleepInState((KeyguardState) obj));
    }
}
