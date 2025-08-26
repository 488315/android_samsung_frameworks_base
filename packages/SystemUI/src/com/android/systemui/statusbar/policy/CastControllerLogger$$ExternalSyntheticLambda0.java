package com.android.systemui.statusbar.policy;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.CastControllerLogger;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class CastControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                CastControllerLogger.Companion companion = CastControllerLogger.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("stopCasting failed because projection is no longer active: ", logMessage.getStr1());
            case 1:
                CastControllerLogger.Companion companion2 = CastControllerLogger.Companion;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("stopCasting. isProjection=", logMessage.getBool1());
            case 2:
                CastControllerLogger.Companion companion3 = CastControllerLogger.Companion;
                return "stopCasting is selecting fallback route in MediaRouter";
            case 3:
                CastControllerLogger.Companion companion4 = CastControllerLogger.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onRouteChanged: ", logMessage.getStr1());
            case 4:
                CastControllerLogger.Companion companion5 = CastControllerLogger.Companion;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onRouteUnselected(", "): ", logMessage.getStr1());
            case 5:
                CastControllerLogger.Companion companion6 = CastControllerLogger.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onRouteRemoved: ", logMessage.getStr1());
            case 6:
                CastControllerLogger.Companion companion7 = CastControllerLogger.Companion;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onRouteSelected(", "): ", logMessage.getStr1());
            case 7:
                CastControllerLogger.Companion companion8 = CastControllerLogger.Companion;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("setProjection: ", logMessage.getStr1(), " -> ", logMessage.getStr2());
            default:
                CastControllerLogger.Companion companion9 = CastControllerLogger.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onRouteAdded: ", logMessage.getStr1());
        }
    }
}
