package com.android.systemui.keyguard.data.repository;

import android.app.admin.DevicePolicyManager;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class BiometricSettingsRepositoryKt {
    public static final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 access$and(Flow flow, Flow flow2) {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, flow2, new BiometricSettingsRepositoryKt$and$1(null));
    }

    public static final boolean isNotActive(DevicePolicyManager devicePolicyManager, int i, int i2) {
        return (devicePolicyManager.getKeyguardDisabledFeatures(null, i) & i2) == 0;
    }
}
