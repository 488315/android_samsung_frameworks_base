package com.android.systemui.statusbar.policy;

import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "saveNewRotationLockSetting: isRotationLocked=" + logMessage.getBool1() + ", state=" + logMessage.getInt1();
            default:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("setListening: ", logMessage.getBool1());
        }
    }
}
