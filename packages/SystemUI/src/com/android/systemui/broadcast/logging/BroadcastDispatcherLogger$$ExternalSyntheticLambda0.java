package com.android.systemui.broadcast.logging;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt__IndentKt;

/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastDispatcherLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BroadcastDispatcherLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                BroadcastDispatcherLogger.Companion companion = BroadcastDispatcherLogger.Companion;
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Receiver ", logMessage.getStr1(), " tagged for removal from user ");
            case 1:
                BroadcastDispatcherLogger.Companion companion2 = BroadcastDispatcherLogger.Companion;
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Receiver ", logMessage.getStr1(), " has been completely removed for user ");
            case 2:
                BroadcastDispatcherLogger.Companion companion3 = BroadcastDispatcherLogger.Companion;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Receiver ", str1, " (", str2, ") registered for user ");
                sbM.append(int1);
                return sbM.toString();
            case 3:
                BroadcastDispatcherLogger.Companion companion4 = BroadcastDispatcherLogger.Companion;
                int int12 = logMessage.getInt1();
                String str12 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                StringBuilder sbM2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int12, "Broadcast ", " (", str12, ") dispatched to ");
                sbM2.append(str22);
                return sbM2.toString();
            case 4:
                BroadcastDispatcherLogger.Companion companion5 = BroadcastDispatcherLogger.Companion;
                int int13 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str13 = logMessage.getStr1();
                StringBuilder sbM3 = MutableObjectList$$ExternalSyntheticOutline0.m(int13, int2, "[", "] Broadcast received for user ", ": ");
                sbM3.append(str13);
                return sbM3.toString();
            case 5:
                BroadcastDispatcherLogger.Companion companion6 = BroadcastDispatcherLogger.Companion;
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Receiver ", logMessage.getStr1(), " unregistered for user ");
            case 6:
                BroadcastDispatcherLogger.Companion companion7 = BroadcastDispatcherLogger.Companion;
                int int14 = logMessage.getInt1();
                String str23 = logMessage.getStr2();
                String str14 = logMessage.getStr1();
                StringBuilder sbM4 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int14, "\n                Receiver registered with Context for user ", ". Flags=", str23, "\n                ");
                sbM4.append(str14);
                sbM4.append("\n            ");
                return StringsKt__IndentKt.trimIndent(sbM4.toString());
            default:
                BroadcastDispatcherLogger.Companion companion8 = BroadcastDispatcherLogger.Companion;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Receiver unregistered with Context for user ", ", action ", logMessage.getStr1());
        }
    }
}
