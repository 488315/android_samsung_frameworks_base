package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BiometricSettingsRepositoryKt {
    public static final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 access$and(Flow flow, Flow flow2) {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, flow2, new BiometricSettingsRepositoryKt$and$1(null));
    }

    public static final boolean isNotActive(DevicePolicyManager devicePolicyManager, int i, int i2) {
        return (devicePolicyManager.getKeyguardDisabledFeatures(null, i) & i2) == 0;
    }
}
