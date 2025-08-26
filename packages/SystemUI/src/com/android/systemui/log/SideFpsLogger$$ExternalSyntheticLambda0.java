package com.android.systemui.log;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class SideFpsLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return ValueAnimator$$ExternalSyntheticOutline0.m("SideFpsSensor auth duration changed: ", logMessage.getLong1());
            case 1:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("restToUnlockSettingEnabled: ", logMessage.getBool1());
            default:
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str2 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(int1, int2, "SideFpsSensorLocation state changed: pointOnScreen: (", ", ", "), sensorLength: ");
                sbM.append(str2);
                sbM.append(", sensorVerticalInDefaultOrientation: ");
                sbM.append(bool1);
                return sbM.toString();
        }
    }
}
