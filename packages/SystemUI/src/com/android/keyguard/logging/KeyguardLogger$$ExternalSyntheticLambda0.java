package com.android.keyguard.logging;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("droppingFaceMessage message=", logMessage.getStr1(), " followUpMessage:", logMessage.getStr2());
            case 1:
                return MoveResult$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Delay showing trustAgentError:", logMessage.getStr1(), ". fpEngaged:", " faceRunning:", logMessage.getBool1()), logMessage.getBool2(), " ");
            case 2:
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(int1, int2, "Showing unlock ripple with center (x, y): (", ", ", "), context: ");
                m.append(str1);
                return m.toString();
            case 3:
                return "Not showing unlock ripple: keyguardNotShowing: " + logMessage.getBool1() + ", unlockNotAllowed: " + logMessage.getBool2();
            case 4:
                return logMessage.getStr1() + " msgId: " + logMessage.getStr2() + " msg: " + logMessage.getStr3();
            case 5:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), ": ", logMessage.getStr2());
            default:
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                int int12 = logMessage.getInt1();
                StringBuilder m2 = EmergencyButtonController$$ExternalSyntheticOutline0.m("refreshBatteryInfo isChargingOrFull:", " powerPluggedIn:", " batteryOverheated:", bool1, bool2);
                m2.append(bool3);
                m2.append(" batteryLevel:");
                m2.append(int12);
                return m2.toString();
        }
    }
}
